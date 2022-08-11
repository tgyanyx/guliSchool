package com.atguigu.msmservice.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean sendMsm(HashMap<String, Object> map, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default","LTAI5tJtZMXUwJ4rP2WnUiqi","OwX0SdCjD6uRTOmntc1aBAggv5gyQp");
        IAcsClient client = new DefaultAcsClient(profile);
//        设置相关固定参数
        CommonRequest request = new CommonRequest();
//        request.setProtocol(ProtoType.HTTPS)
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

//        设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName","阿里云短信测试");
        request.putQueryParameter("TemplateCode","SMS_154950909");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        try{
            //        最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
