package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-08-03
 */
@RestController
@RequestMapping("/eduservice/edu-video")
//@CrossOrigin 用网关全局处理跨域问题
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

//    注入vodClient
    @Autowired
    private VodClient vodClient;
    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }


    //删除小节  删除对应的视频
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
//        根据小节id获取视频id  调用方法实现视频删除
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoId = eduVideo.getVideoSourceId();
//        判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoId)){
            // 根据视频id，远程调用实现视频删除

            R result = vodClient.deleteVideo(eduVideo.getVideoSourceId());
            if(result.getCode() == 20001){
                throw  new GUliException(20001,"删除视频失败，熔断器。。。");
            }
        }

//        删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
//        System.out.println(eduVideo.toString());
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }
}

