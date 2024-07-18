package com.sky.task;

import com.sky.constant.MessageConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类 定时处理订单状态
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;


    /**
     * 处理超时订单
     */

    @Scheduled(cron = "0 * * * * ? ") //每分钟触发一次
    public void processTimeOutOrder() {
        log.info("定时处理超时订单:{}", LocalDateTime.now());
        //查询超时订单 订单状态为待支付 且时间超过15分钟
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT,LocalDateTime.now().minusMinutes(15));
        if(list != null && list.size() > 0) {
            for(Orders order : list) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时,自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }

    }

    /**
     * 处理未确认收货订单
     */
    @Scheduled(cron = "0 0 1 * * ? ") //每天凌晨一点执行一次
    public void processDeliveryOrder(){
        log.info("定时处理处于派送中订单: {}",LocalDateTime.now());
        //处理处于派送中且前一天的订单 1点减一小时
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS,LocalDateTime.now().minusHours(1));
        if(list != null && list.size() > 0) {
            for(Orders order : list) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }

    }
}
