package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Guard;
import java.util.List;

@RestController
@RequestMapping("/edu_vod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
//    上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
//        返回视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

//    根据视频id删除阿里云视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);

//            创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
//            向request设置视频id
            request.setVideoIds(id);
//            调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GUliException(20001,"删除失败");
        }


    }

//    删除多个阿里云视频的方法
//    参数多个视频id
    @DeleteMapping("delete-batch")
    public R deleteVideoBatch(@RequestBody List<String> videoIdList){
        System.out.println("==========="+videoIdList);
        vodService.deleteVideoBatch(videoIdList);
        return R.ok();
    }

//    根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try{
//            创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantPropertiesUtils.KEY_ID,ConstantPropertiesUtils.KEY_SECRET);
            //        创建获取视频凭证request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
//        向request设置视频id
            request.setVideoId(id);

//        调用初始化对象的方法得到凭证
            response = client.getAcsResponse(request);
            System.out.println("被调用了"+ response.getPlayAuth());
            return R.ok().data("playAuth",response.getPlayAuth());
        }catch (Exception e){
            throw new GUliException(20001,"视频凭证获取失败");
        }
    }
}
