package com.other.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.ChannelN;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-11 15:15
 * @Version v1.0
 **/
@Component
@Log4j2
public class AckListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info(StrUtil.str(message.getBody(), CharsetUtil.UTF_8));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicAck(deliveryTag, true);
            int a = 1 / 0;
        } catch (Exception e) {
            // 拒签
            channel.basicNack(deliveryTag,true, true);

//            channel.basicReject(deliveryTag,true);
        }

    }
}
