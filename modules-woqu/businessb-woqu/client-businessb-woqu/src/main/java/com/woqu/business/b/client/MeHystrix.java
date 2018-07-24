package com.woqu.business.b.client;

import org.springframework.stereotype.Component;

/**
 * @author orrin on 2018/7/4
 */
@Component(value = "bMeHystrix")
public class MeHystrix implements Me{
    @Override
    public String callMe() {
        return " b Me.callMe error, please try again later";
    }
}
