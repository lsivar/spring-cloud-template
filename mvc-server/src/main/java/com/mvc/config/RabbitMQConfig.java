package com.mvc.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-11 14:36
 * @Version v1.0
 **/
@Component
@Log4j2
public class RabbitMQConfig {

    @Bean
//    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());

        //3.判断是否发送成功
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                assert correlationData != null;
                log.info("发送消息到交换机成功,messageID:{}", cause);
            } else {
                log.error("消息发送到交换机被退回,ID:{}", cause);
            }
        });

        //回退模式
        template.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey)->{
            log.error("exchange发送消息到queue失败");
            log.error(message);
            log.error(replyCode);
            log.error(replyText);
            log.error(exchange);
            log.error(routingKey);
        });

        return template;
    }
}
