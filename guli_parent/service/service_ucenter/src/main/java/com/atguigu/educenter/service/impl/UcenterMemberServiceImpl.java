package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-08-10
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
// 登录的方法
    @Override
    public String login(UcenterMember member) {
//         获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

//        手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GUliException(20001,"登录失败");
        }
//        判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

//        判断查询对象是否为空
        if(ucenterMember == null){// 没有这个手机号
            throw new GUliException(20001,"手机号未注册");
        }
//        判断密码
//        因为存储到数据库密码肯定加密的
//        把输入的密码进行加密 再和数据库密码进行比较
//        md5 加密
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new GUliException(20001,"账号或密码错误");
        }
//        判断用户是否禁用
        if(ucenterMember.getIsDisabled()){
            throw new GUliException(20001,"登录失败");
        }

//        登录成功
//        生成token字符串 使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
//        获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickName = registerVo.getNickname();
        String password = registerVo.getPassword();

//        非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName)){
            throw new GUliException(20001,"注册失败");
        }

//        判断验证码
//        获取redis里面的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            System.out.println(code + "=======" + redisCode);
            throw new GUliException(20001,"注册失败");
        }
//        判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        baseMapper.selectCount(wrapper);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GUliException(20001,"注册失败");
        }

//        数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

//    根据openid进行查询
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }
}
