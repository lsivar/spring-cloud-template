package com.mvc.controller;

import cn.hutool.core.util.IdUtil;
import com.mvc.feign.OrderFeign;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-10 22:51
 * @Version v1.0
 **/
@RestController
@Log4j2
public class PayController {

    @Autowired
    private OrderFeign orderFeign ;

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    /** 支付成功 */
    @GetMapping(value = "pay/success")
    public String paySuccess(){
        /*
         1.支付成功后调用订单系统更新状态
         2.调用MQ发送公众号推送
         3.调用MQ发送统计报表
         4.调用MQ发送socket通知客户端
         5.调用MQ发送相关广告
         */

        // 1.支付成功后调用订单系统更新状态
        String result = orderFeign.updateOrder();

        //2.调用MQ发送公众号推送
        rabbitTemplate.setReplyTimeout(1000*10);
        rabbitTemplate.convertAndSend("pay.success", "",IdUtil.fastSimpleUUID(), new CorrelationData("uuid-111"));
        /*3.回退模式 : 当消息发送到exchange后,exchange路由到queue失败,触发ReturnCallBack
        *     1.开启回退模式
        *     2.设置ReturnCallBack
        *     3.设置exchange消息处理机制:
        *       1.如果消息没有路由到queue,丢弃消息(默认)
        *       2.如果消息没有路由到queue,返回给发送方ReturnCallBack
        * */

        return result;
    }

    public String ack(){
        /*ACK:消息被消费者获取进行消费, 必须是消费成功, 消费者手动调用返回成功状态, 否则返回失败状态
        * 1.开启ACK手动确认模式: acknowledge-mode: manual
        * 2.消息监听类实现ChannelAwareMessageListener
        * 3.如果消息处理成功,则触发basicAck
        * 4.如果消息处理失败,则触发basicNack
        * */

        return "ack";
    }

    @GetMapping(value = "fanout")
    public String fanout(){
        rabbitTemplate.convertAndSend("logs","",IdUtil.fastSimpleUUID());
        return "fanout success";
    }

}
