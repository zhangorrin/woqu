package com.woqu.business.a.server;

import com.woqu.business.a.client.Addition;
import com.woqu.business.b.client.Multiply;
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
public class AdditionController implements Addition {

    @Autowired
    private Multiply b;

    @GetMapping("/call")
    public String callMe() {
        return "hello , i am a " + System.currentTimeMillis();
    }

    @Value("${extend.info.desc:error}")
    private String desc;

    @GetMapping("/desc")
    public String desc() {
        return desc;
    }

    @Override
    public Integer add(int x, int y) {
        return x + y;
    }
}
