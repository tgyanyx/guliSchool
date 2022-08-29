package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.PayLog;
import com.atguigu.eduorder.mapper.PayLogMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.service.PayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.servicebase.exceptionhandler.GUliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-08-16
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;
    //    生成支付二维码
    @Override
    public Map createNative(String orderNo) {
        try{
//            1 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);

//            2 使用map 设置生成二维码需要参数
            HashMap<String, String> map = new HashMap<>();
            map.put("appid","wx74862e0dfcf69954");//支付id
            map.put("mch_id", "1558950191");//商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());//生成随机的字符串，让每次生成的二维码不一样
            map.put("body", order.getCourseTitle());//生成二维码的名字
            map.put("out_trade_no", orderNo);//二维码唯一的标识
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//支付金额
            map.put("spbill_create_ip", "127.0.0.1");//现在进行支付的ip地址，实际项目使用项目的域名
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");//支付后回调地址
            map.put("trade_type", "NATIVE");//支付类型，NATIVE:根据价格生成二维码
//            3 发送httpclient请求，传递参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //参数1：要转换为xml格式的map
            //参数2：商户的key，用于加密二维码中的信息
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);//上面发送请求的是https。默认支持，需要设置为true支持
            //执行post请求发送
            client.post();
//            得到发送请求返回结果
            //返回的结果是xml格式的
            String content = client.getContent();

            //把xml格式转换为map集合，他里面的数据不全
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            //最终返回数据封装
            HashMap hashMap = new HashMap<>();
            hashMap.put("out_trade_no",orderNo);
            hashMap.put("course_id",order.getCourseId());
            hashMap.put("total_fee",order.getTotalFee());
            hashMap.put("result_code",resultMap.get("result_code")); //二维码操作状态码
            hashMap.put("code_url",resultMap.get("code_url")); //二维码地址

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //Template.opsForValue().set(orderNo, hashMap, 120,TimeUnit.MINUTES);
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            throw new GUliException(20001,"生成二维码错误");
        }


    }

//    查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try{
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、发送httpClient请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setHttps(true);
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));//通过商户key加密
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //5、返回
            return resultMap;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //            添加记录到支付表，更新订单表订单状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
//        从map里面获取订单号
        String orderNo = map.get("out_order_no");
//        根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);

        Order order = orderService.getOne(wrapper);
//        更新订单表订单状态
        if(order.getStatus().intValue() == 1){
            return;
        }
        order.setStatus(1);//1 代表已经支付
        orderService.updateById(order);
//        向支付表添加支付记录
        PayLog tPayLog = new PayLog();
        tPayLog.setOrderNo(orderNo);//支付订单号
        tPayLog.setPayTime(new Date());//支付时间
        tPayLog.setPayType(1);//支付类型
        tPayLog.setTotalFee(order.getTotalFee());//总金额(分)
        tPayLog.setTradeState(map.get("trade_state"));//支付状态
        tPayLog.setTransactionId(map.get("transaction_id"));//订单流水号
        tPayLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(tPayLog);
    }
}
