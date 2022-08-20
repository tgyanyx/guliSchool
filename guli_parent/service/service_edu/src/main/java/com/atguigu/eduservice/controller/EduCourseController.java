package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-08-03
 */
@RestController
@RequestMapping("/eduservice/edu-course")
//@CrossOrigin 用网关全局处理跨域问题
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //    添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //    根据课程查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo", courseInfoVo);
    }

    //    课程列表基本实现
    @GetMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable long current,
                            @PathVariable long limit){
        Page<EduCourse> coursePage = new Page<>(current,limit);
        courseService.page(coursePage,null);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    //    条件查询带分页
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> coursePage = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        wrapper.orderByDesc("gmt_create");

        courseService.page(coursePage,wrapper);
        long total = coursePage.getTotal();
        List<EduCourse> list = coursePage.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //    修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getpublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo publishCourseInfo = courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse", publishCourseInfo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourse.setId(id);
        boolean flag = courseService.updateById(eduCourse);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


//    删除课程
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

