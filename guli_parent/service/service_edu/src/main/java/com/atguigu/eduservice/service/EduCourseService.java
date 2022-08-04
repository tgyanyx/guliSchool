package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yan
 * @since 2022-08-03
 */
public interface EduCourseService extends IService<EduCourse> {
//    添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);
}