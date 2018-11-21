package com.woqu.business.merchandise.client;

import com.woqu.business.price.client.Price;

import java.io.Serializable;

/**
 * @author orrin on 2018/11/21
 */
public class Merchandise implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String merchandiseId;

    /**
     * 名称
     */
    private String name;

    /**
     * 货号
     */
    private String itemNum;

    /**
     * 品牌
     */
    private String brand;

    private Price price;

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
