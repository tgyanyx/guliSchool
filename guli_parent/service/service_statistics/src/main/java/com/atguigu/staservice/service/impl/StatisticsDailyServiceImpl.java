package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcneterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-08-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcneterClient ucneterClient;
    @Override
    public void registerCount(String day) {

//        添加记录之前删除表相同日期的数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);

//        远程调用得到某一天注册人数
        R registerR = ucneterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

//        把获取数据添加到数据库中,统计分析表里面
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);//注册人数
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(200,300));//登录数
        sta.setVideoViewNum(RandomUtils.nextInt(200,300));//视频流量数
        sta.setDateCalculated(day);//统计日期
        baseMapper.insert(sta);
    }
    //    图表显示,返回两部分数据,日期json数据,数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
//        根据条件查询对应数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
//        因为返回有两部分数据: 日期 和 日期 对应数量
//        前端要求数组json结构,对应java中 list集合
//        创建两个list集合 一个日期list 一个数量list
        List<String> date_caculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

//        遍历查询所有数据list集合,进行封装
        for(int i = 0; i < staList.size(); i++){
            StatisticsDaily statisticsDaily = staList.get(i);
//            封装日期list集合
            date_caculatedList.add(statisticsDaily.getDateCalculated());
//            封装对应数量
//判断查询的哪个字段
            if ("register_num".equals(type)){
                numDataList.add(statisticsDaily.getRegisterNum());
            }
            if ("login_num".equals(type)){
                numDataList.add(statisticsDaily.getLoginNum());
            }
            if ("video_view_num".equals(type)){
                numDataList.add(statisticsDaily.getVideoViewNum());
            }
            if ("course_num".equals(type)){
                numDataList.add(statisticsDaily.getCourseNum());
            }

        }
        Map<String,Object> map = new HashMap<>();
        map.put("date_caculatedList",date_caculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
