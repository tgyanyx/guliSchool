package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
//        accessKeyId&&accessKeySecret


        try {

//        fileName ：上传文件原始名称
            String fileName = file.getOriginalFilename();
            //        title  :上传之后显示名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));
//        inputStream ：上传文件输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    @Override
    public void deleteVideoBatch(List<String> videoIdList) {
        try{
//            初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.KEY_ID,ConstantPropertiesUtils.KEY_SECRET);
//            创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

//            videoList值转换成1，2，3
            String videoIds = StringUtils.join(videoIdList,",");

//            向request设置视频id
            request.setVideoIds(videoIds);
//            调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GUliException(20001,"删除视频失败");
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");

        String s = StringUtils.join(list.toArray(), ",");
        System.out.println(s);
    }
}
