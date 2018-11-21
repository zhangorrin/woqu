package com.woqu.business.price.server.service;

import com.woqu.business.price.client.Price;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * @author orrin on 2018/11/21
 */
@Service("priceService")
public class PriceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceService.class);

    public Mono<Price> getByUserId(String merchandiseId, String userId) {
        Price price = new Price();

        double finalPrice = RandomUtils.nextDouble() * RandomUtils.nextInt(100);
        double originalPrice = RandomUtils.nextDouble() * RandomUtils.nextInt(50) + finalPrice;

        BigDecimal finalDecimal = new BigDecimal(finalPrice);
        price.setFinalPrice(finalDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());

        BigDecimal originalPriceDecimal = new BigDecimal(originalPrice);
        price.setOriginalPrice(originalPriceDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());

        return Mono.justOrEmpty(price);
    }

    public Mono<Price> get(String merchandiseId) {
        return this.getByUserId(merchandiseId, null);
    }

}
