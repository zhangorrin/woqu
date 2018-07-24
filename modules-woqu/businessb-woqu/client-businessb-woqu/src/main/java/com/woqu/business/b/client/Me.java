package com.woqu.business.b.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author orrin on 2018/7/4
 */
@FeignClient(serviceId = "business-b-woqu", fallback = MeHystrix.class)
public interface Me {

    @GetMapping("/me")
    public String callMe();
}