package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yan
 * @since 2022-08-10
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
//    查询某一天注册人数
//    Integer countRegisterDay(@Param("aa") String day,String name);
//      有两个参数的话通过@Param进行区分
      Integer countRegisterDay(String day);
}
