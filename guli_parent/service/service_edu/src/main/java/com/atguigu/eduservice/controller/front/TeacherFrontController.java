package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin 用网关全局处理跨域问题
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

//    分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,
                                 @PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(teacherPage);
        
//        返回分页所有数据
        
        return R.ok().data("map",map);
    }

//    2 讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
//        根据讲师id查询讲师信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
//        根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = eduCourseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",list);
    }
}
