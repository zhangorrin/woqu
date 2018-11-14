package com.woqu.business.b.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author orrin on 2018/7/4
 */
@Component(value = "multiplyHystrix")
public class MultiplyHystrix implements Multiply {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplyHystrix.class);

    @Override
    public Integer multiply(int x, int y) {
        LOGGER.error(" Multiply is disabled ");
        return 0;
    }
}
