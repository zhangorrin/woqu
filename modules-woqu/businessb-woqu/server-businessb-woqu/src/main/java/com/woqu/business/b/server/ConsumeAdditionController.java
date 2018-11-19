package com.woqu.business.b.server;

import com.woqu.business.a.client.Addition;
import com.woqu.business.b.client.Multiply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author orrin on 2018/7/4
 */
@RestController
public class ConsumeAdditionController implements Multiply {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeAdditionController.class);;

    @Autowired
    private Addition a;

    @Override
    public Integer multiply(int x, int y) {
        return x * y;
    }

    @GetMapping("/area")
    public Integer cuboidArea(@RequestParam("length") int length, @RequestParam("width") int width, @RequestParam("heigh") int heigh) {
        LOGGER.info("length = {}, width = {}, heigh = {}");
        return  this.multiply(2, a.add(a.add(this.multiply(length, width), this.multiply(width, heigh)), this.multiply(length, heigh)));
    }

    @GetMapping("/area/2")
    public Integer cuboidArea2(@RequestParam("length") int length, @RequestParam("width") int width, @RequestParam("heigh") int heigh) {
        LOGGER.info("length = {}, width = {}, heigh = {}");
        return  this.multiply(2, this.add2(this.add2(this.multiply(length, width), this.multiply(width, heigh)), this.multiply(length, heigh)));
    }

    /**
     * 使用 LoadBalancerClient 消费business-a-woqu中的方法
     */
    private Integer add2(int x, int y) {
        ServiceInstance instance = client.choose("business-a-woqu");
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/add?x=" + x + "&y=" + y;
        return restTemplate.getForObject(url, Integer.class);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient client;


    /**
     * 使用 Ribbon 消费business-a-woqu中的方法
     */
    private Integer add3(int x, int y) {
        ServiceInstance instance = client.choose("business-a-woqu");
        String url = "http://business-a-woqu/add?x=" + x + "&y=" + y;
        return restTemplate.getForObject(url, Integer.class);
    }

    @GetMapping("/area/3")
    public Integer cuboidArea3(@RequestParam("length") int length, @RequestParam("width") int width, @RequestParam("heigh") int heigh) {
        LOGGER.info("length = {}, width = {}, heigh = {}");
        return  this.multiply(2, this.add3(this.add3(this.multiply(length, width), this.multiply(width, heigh)), this.multiply(length, heigh)));
    }
}
