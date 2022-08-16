package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller //现在是请求地址不是返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

//    生成微信扫描的二维码

    @GetMapping("login")
    public String getWxCode() throws UnsupportedEncodingException {
        //    请求微信地址
//        固定地址 ，后面拼接参数
//        String url = "https://open.weixin.qq.com/connect/qrconnect？appid="
//                + ConstantUtils.WX_APP_ID+"&redirect_uri="+ConstantUtils.WX_REDIRECT_URL
//                +"&response_type="+;
//
//        重定向到请求微信里面

//        相当于？占位符
        String baseUrl ="https://open.weixin.qq.com/connect/qrconnect"
                +"?appid=%s"
                +"&redirect_uri=%s"
                +"&response_type=code"
                +"&scope=snsapi_login"
                +"&state=%s"
                +"#wechat_redirect";

//        对redirect_url进行URLEncoder编码
        String redirect_url = ConstantUtils.WX_REDIRECT_URL;
        redirect_url = URLEncoder.encode(redirect_url, "utf-8");

//        设置%s 里面值
        String url = String.format(
                baseUrl,
                ConstantUtils.WX_APP_ID,
                redirect_url,
                "yan"
        );
        return "redirect:"+url;
    }

//    获取扫描人信息 添加数据
    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            System.out.println("code:" +code);
            System.out.println("state: " + state);
//        1 获取到code值 临时票据 类似于验证码
//        拿着 code请求 微信固定的地址 得到两个值 access_token 和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"+
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
//        拼接三个参数 id 密钥 和 code 值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantUtils.WX_APP_ID,
                    ConstantUtils.WX_APP_SECRET,
                    code
            );

//        请求这个拼接好的地址 得到返回两个值 access_token 和 openid
//        使用httpclient发送请求 得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo:" + accessTokenInfo);

//            从accessTokenInfo里面拿到 access_token 和 openid
//            使用httpclient 发送请求 得到返回结果
//            使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap hashMapToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) hashMapToken.get("access_token");
            String openid = (String) hashMapToken.get("openid");

//            把扫码人信息添加到数据库
//            判断数据库表里面是否有该用户信息 根据openid进行查询
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if(member == null){ // member 为空将信息添加到数据库
                //            拿着access_token 和openid 再去请求微信固定的地址
//            访问微信的资源服务器 获取用户信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid);
//            发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo: "+userInfo);
//            获取返回userInfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickName = (String) userInfoMap.get("nickname"); //昵称
                String headimgurl = (String) userInfoMap.get("headimgurl"); // 头像

                member = new UcenterMember();
                member.setAvatar(headimgurl);
                member.setOpenid(openid);
                member.setNickname(nickName);
                ucenterMemberService.save(member);
            }
//            使用jwt 根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
//            最后返回首页面
            return "redirect:http://localhost:3000?token="+jwtToken;
        }catch (Exception e){
           throw new GUliException(20001,"登录失败");

        }

    }
}
