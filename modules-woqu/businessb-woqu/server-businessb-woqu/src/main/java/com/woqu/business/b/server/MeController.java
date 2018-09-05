package com.woqu.business.b.server;

import com.woqu.business.a.client.Me;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author orrin on 2018/7/4
 */
@RestController
public class MeController implements com.woqu.business.b.client.Me {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeController.class);;

    @Autowired
    private Me a;

    @GetMapping("/call/a")
    public String call() {
        LOGGER.info("start b call");
        return " i am b , " + System.currentTimeMillis() + " , call a : " + a.callMe();
    }

    @Override
    public String callMe() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return " i am b , " + System.currentTimeMillis();
    }
}
