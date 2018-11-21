package com.woqu.business.merchandise.server.service;

import com.woqu.business.merchandise.client.Merchandise;
import com.woqu.business.price.client.Price;
import com.woqu.cloud.core.constant.AppWebClientBaseURL;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author orrin on 2018/11/21
 */
@Service("merchandiseService")
public class MerchandiseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchandiseService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<Merchandise> get(String merchandiseId) {
        Merchandise merchandise = new Merchandise();
        merchandise.setMerchandiseId(merchandiseId);
        merchandise.setName(RandomStringUtils.randomAscii(10));
        merchandise.setBrand(RandomStringUtils.randomAscii(10));
        merchandise.setItemNum(RandomStringUtils.randomNumeric(10));

        Mono<Price> monoPrice = this.price(merchandiseId);
        Mono<Merchandise> merchandiseMono = monoPrice.flatMap(t -> {
            merchandise.setPrice(t);
            return Mono.justOrEmpty(merchandise);
        });

        return merchandiseMono;
    }

    public Mono<Price> price(String merchandiseId) {
        Mono<Price> price = webClientBuilder.baseUrl(AppWebClientBaseURL.BUSINESS_PRICE_WOQU).build()
                .get().uri("/price/" + merchandiseId)
                .retrieve()
                .bodyToMono(Price.class);

        return price;
    }

}
