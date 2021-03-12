package com.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-10 22:50
 * @Version v1.0
 **/
@SpringBootApplication
@EnableFeignClients
public class MVCApplication {
    public static void main(String[] args) {
        SpringApplication.run(MVCApplication.class, args);
    }
}
