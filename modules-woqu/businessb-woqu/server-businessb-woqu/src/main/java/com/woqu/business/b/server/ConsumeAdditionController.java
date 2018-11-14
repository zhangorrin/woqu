package com.woqu.business.b.server;

import com.woqu.business.a.client.Addition;
import com.woqu.business.b.client.Multiply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public Integer cuboidArea(@RequestParam("length") int length, @RequestParam("width") int width, @RequestParam("heigh") int heigh) {
        LOGGER.info("length = {}, width = {}, heigh = {}");
        return  this.multiply(2, a.add(a.add(this.multiply(length, width), this.multiply(width, heigh)), this.multiply(length, heigh)));
    }
}
