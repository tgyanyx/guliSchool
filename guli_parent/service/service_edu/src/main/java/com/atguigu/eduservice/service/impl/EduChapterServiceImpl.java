package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-08-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
//        根据课程id查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
//        根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
//        遍历查询章节list集合进行封装
        List<ChapterVo> finalList = new ArrayList<>();
//        3 遍历查询章节list集合 进行封装
        for (int i = 0; i < eduChapterList.size(); i++){
//            每个章节
            EduChapter eduChapter = eduChapterList.get(i);
//            eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

//            创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();
            //        遍历查询小节list集合进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
//                得到每个小节
                EduVideo eduVideo = eduVideoList.get(j);
//                判断 小节里面chapterid和章节里面的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
//                    进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);

//                    放到小节封装集合
                    videoList.add(videoVo);
                }

//                把封装之后小节list集合 放到章节对象里面
                chapterVo.setChildren(videoList);
            }


//            把chapterVo放到最终list集合
            finalList.add(chapterVo);
        }



        return finalList;
    }
}
