package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu_cms/bannerAdmin")
//@CrossOrigin 用网关全局处理跨域问题
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

//    1 分页查询bannner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

//    2 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }

    //修改banner
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean flag = bannerService.updateById(crmBanner);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id删除banner
    @DeleteMapping("/deleteBannerById/{id}")
    public R deleteBannerById(@PathVariable String id){
        boolean flag = bannerService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询banner
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("item",crmBanner);
    }
}
