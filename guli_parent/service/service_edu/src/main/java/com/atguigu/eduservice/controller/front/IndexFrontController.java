package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin 用网关全局处理跨域问题
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;
//    查询前8条热门课程 查询前四条名师
    @GetMapping("index")
    public R index(){
//        查询前八条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourseList = eduCourseService.list(wrapper);

//        查询前四个名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(wrapperTeacher);
        return R.ok().data("courseList",eduCourseList).data("teacherList",eduTeacherList);
    }
}
