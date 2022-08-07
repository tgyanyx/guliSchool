package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodClientImpl.class) // 调用的服务名称
@Component
public interface VodClient {
    //    定义调用的方法路径
//    根据视频id删除阿里云视频
    @DeleteMapping("/edu_vod/video/deleteVideo/{id}")
    R deleteVideo(@PathVariable("id") String id);

    //    定义调用删除多个视频的方法
    //    删除多个阿里云视频的方法
//    参数多个视频id
    @DeleteMapping("/edu_vod/video/delete-batch")
    R deleteVideoBatch(@RequestBody List<String> videoIdList);
}
