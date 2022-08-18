package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author yan
 * @since 2022-08-16
 */
public interface PayLogService extends IService<PayLog> {
    //    生成支付二维码
    Map createNative(String orderNo);

//    根据订单号查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);
    //            添加记录到支付表，更新订单表订单状态
    void updateOrderStatus(Map<String, String> map);
}
