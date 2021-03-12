package com.order.controller;

import cn.hutool.core.util.IdUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-10 22:56
 * @Version v1.0
 **/
@RestController
@Log4j2
@RequestMapping(value = "order")
public class OrderController {

    @GetMapping(value = "update")
    public String updateOrder(){
        return "订单状态更新成功";
    }
}
