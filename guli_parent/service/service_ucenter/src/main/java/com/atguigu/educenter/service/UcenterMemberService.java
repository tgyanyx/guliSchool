package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yan
 * @since 2022-08-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

//    注册方法
    void register(RegisterVo registerVo);
}
