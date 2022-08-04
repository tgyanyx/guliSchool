package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-08-02
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;
//    添加课程分类
//    获取上传过来的文件，把文件内容内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
//        上传文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

//    课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject(){
//        list 集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllSubject();
        return R.ok().data("subject",list);
    }


}

