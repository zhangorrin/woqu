package com.woqu.business.a.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author orrin on 2018/7/4
 */
@FeignClient(serviceId = "business-a-woqu", fallback = AdditionHystrix.class)
public interface Addition {

    @GetMapping("/add")
    public Integer add(@RequestParam("x") int x, @RequestParam("y") int y);
}
