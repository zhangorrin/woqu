package com.woqu.business.a.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author orrin on 2018/7/4
 */
@Component(value = "additionHystrix")
public class AdditionHystrix implements Addition {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdditionHystrix.class);

    @Override
    public Integer add(int x, int y) {
        LOGGER.error(" Addition is disabled ");
        return 0;
    }
}
