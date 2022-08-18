package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-08-10
 */
@RestController
@RequestMapping("/edu_center/ucenter-member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

//    登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
//        member封装手机号和密码
//        调用service方法实习登录
//        返回token值
       String token =  ucenterMemberService.login(member);
       return R.ok().data("token",token);
    }
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

//    根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
//        调用jwt工具类的方法 根据request 对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


//    根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberVo getUerInfoOrder(@PathVariable String id){
        UcenterMember member = ucenterMemberService.getById(id);
//        把member对象里面的值复制给UcenterMembervo
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member,ucenterMemberVo);
        return ucenterMemberVo;
    }
}

