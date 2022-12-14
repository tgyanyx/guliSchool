package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcneterClient {
    //    查询某一天注册人数
    @GetMapping("/edu_center/ucenter-member/countRegister/{day}")
    public R countRegister(@PathVariable String day);
}
