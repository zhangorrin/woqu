package com.woqu.business.a.client;

import org.springframework.stereotype.Component;

/**
 * @author orrin on 2018/7/4
 */
@Component(value = "aMeHystrix")
public class MeHystrix implements Me{
    @Override
    public String callMe() {
        return " a Me.callMe error, please try again later";
    }
}
