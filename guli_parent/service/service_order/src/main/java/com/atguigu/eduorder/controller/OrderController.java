package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-08-16
 */
@RestController
@RequestMapping("/edu_order/order")
//@CrossOrigin 用网关全局处理跨域问题
public class OrderController {
    @Autowired
    private OrderService orderService;
//    生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId,
                       HttpServletRequest request){

//        创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

//    根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        System.out.println("-----------------------------------");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
//    根据用户id和课程id查询订单表 是否已经购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId){
//        System.out.println(courseId+"--------------------------"+memberId);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }
}

