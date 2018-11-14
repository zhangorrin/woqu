package com.woqu.business.b.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author orrin on 2018/7/4
 */
@FeignClient(serviceId = "business-b-woqu", fallback = MultiplyHystrix.class)
public interface Multiply {

    @GetMapping("/multiply")
    public Integer multiply(@RequestParam("x") int x, @RequestParam("y") int y);
}