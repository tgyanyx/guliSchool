package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-07-20
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin 用网关全局处理跨域问题
public class EduTeacherController {
//    把service注入
    @Autowired
    private EduTeacherService eduTeacherService;
//    1 查询讲师表所有数据
//    rest风格
    @ApiOperation(value = "查询所有教师信息")
    @GetMapping("findAll")
    public R findAllTeacher(){
//        调用service方法实现查询所有教师信息
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

//    2 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }
        return R.error();
    }

//    3 分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
//        创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
//        调用方法实现分页
//        调用方法的时候，底层封装,把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(teacherPage,null);

//        try{
//            int i = 10 /0;
//        }catch (Exception e){
//            throw new GUliException(200001,"执行了自定义异常");
//        }
//        自定义异常触发测试

        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

//        也可以这样写
//        Map map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

//    多条件组合查询带分页
    @ApiOperation(value = "多条件组合查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
//        创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
//        构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

//        多条件组合查询
//        mybatis  动态sql

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
//        判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
//            构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

//        排序
        wrapper.orderByDesc("gmt_create");

//        调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();// 总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }


    @ApiOperation("添加讲师接口的方法")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation("根据讲师id查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @ApiOperation("根据id修改讲师信息")
    @PutMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }
}

