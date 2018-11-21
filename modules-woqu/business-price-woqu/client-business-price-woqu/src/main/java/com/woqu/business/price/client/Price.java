package com.woqu.business.price.client;

import java.io.Serializable;

/**
 * @author orrin on 2018/11/21
 */
public class Price implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 最终价格
     */
    private Double finalPrice;

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
