package com.mvc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-10 23:05
 * @Version v1.0
 **/
@FeignClient(value = "order-server")
public interface OrderFeign {
    @GetMapping(value = "order/update")
    String updateOrder();
}
