package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-08-08
 */
@RestController
@RequestMapping("/edu_cms/bannerFront")
//@CrossOrigin 用网关全局处理跨域问题
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

//    查询所有banner
    @Cacheable(key="'selectIndexList'",value="banner")
    @GetMapping("getAllBanner")
    public R selectAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

