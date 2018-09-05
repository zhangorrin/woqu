package com.woqu.business.a.server;

import com.woqu.business.a.client.Me;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author orrin on 2018/7/4
 */
@RestController
@RefreshScope
public class MeController implements Me {

    @Autowired
    private com.woqu.business.b.client.Me b;

    @Override
    public String callMe() {
        return "hello , i am a " + System.currentTimeMillis() + " and i call b : " + b.callMe();
    }

    @GetMapping("/call/b")
    public String call() {
        return " i am a , " + System.currentTimeMillis() + " , call b : " + b.callMe();
    }


    @Value("${extend.info.desc:error}")
    private String desc;

    @GetMapping("/desc")
    public String desc() {
        return desc;
    }
}
