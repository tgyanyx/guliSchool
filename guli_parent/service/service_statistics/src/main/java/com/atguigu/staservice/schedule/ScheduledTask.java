package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;



//    0/5 * * * * ? 表示每隔5秒执行一次这个方法
    //在每天的凌晨一点，把前一天的数据进行添加
//    0 0 1 * * ?
//    corn 七子表达式 网上有工具生成
    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("*******++++++++*******执行了");
    }


//    在每天的凌晨一点，把前一天的数据进行查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }


}