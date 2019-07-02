package com.woqu.cloud.core.reactive.error;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author orrin on 2019-07-02
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> info = super.getErrorAttributes(request, includeStackTrace);

        Map<String, Object> globalErrorMap = new HashMap<>();
        globalErrorMap.put("code", info.get("status"));
        globalErrorMap.put("data", info);
        globalErrorMap.put("message", info.get("message"));
        return globalErrorMap;
    }
}
