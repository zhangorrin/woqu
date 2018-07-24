package com.woqu.business.b.server;

import com.woqu.business.a.client.Me;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author orrin on 2018/7/4
 */
@RestController
public class MeController implements com.woqu.business.b.client.Me {

    @Autowired
    private Me a;

    @GetMapping("/call/a")
    public String call() {
        return " i am b , " + System.currentTimeMillis() + " , call a : " + a.callMe();
    }

    @Override
    public String callMe() {
        return " i am b , " + System.currentTimeMillis();
    }
}
