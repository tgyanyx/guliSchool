package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient{

//    出错之后会执行
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteVideoBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
