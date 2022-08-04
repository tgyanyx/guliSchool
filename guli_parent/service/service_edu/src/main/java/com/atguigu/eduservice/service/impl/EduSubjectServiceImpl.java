package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-08-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

//    添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try{
//            文件输入流
            InputStream in = file.getInputStream();
//            调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    课程分类列表（树形）
    @Override
    public List<OneSubject> getAllSubject() {
//        1、查询所有一级分类 parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);
//        this.list(wrapperOne);
        
//        查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjects = this.list(wrapperTwo);

//      创建list集合用于存储最终数据
        List<OneSubject> finalSubjects = new ArrayList<>();


//        封装一级分类
//        查询出来的所有一级分类list集合遍历 得到每一个一级分类对象 获取 每个一级分类对象值
//        封装到要求的list集合里面 List<OneSubject> finalSubjects = new ArrayList<>();
        for (int i = 0; i < oneSubjects.size(); i++) {
//            得到oneSubject每个eduSubject对象
            EduSubject eduSubject = oneSubjects.get(i);
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());

            BeanUtils.copyProperties(eduSubject,oneSubject);


            //        封装二级分类
//            创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubject = new ArrayList<>();
//            遍历 二级分类list结合
            for (int i1 = 0; i1 < twoSubjects.size(); i1++) {
//                获取每个二级分类
                EduSubject tSubject = twoSubjects.get(i1);
//                判断二级分类parent_id和一级分类id是否一样
                if(tSubject.getParentId().equals(eduSubject.getId())){
//                    把tSubject值复制到woSubject 里面 放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubject.add(twoSubject);

                }

            }

//            把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubject);

            //            多个OneSubject放到finalSubjects
            finalSubjects.add(oneSubject);


        }

        return finalSubjects;
    }
}
