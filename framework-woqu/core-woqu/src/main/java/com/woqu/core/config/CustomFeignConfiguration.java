package com.woqu.core.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author orrin on 2018/9/4
 */
@Configuration
public class CustomFeignConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFeignConfiguration.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String EXECUTE_TIME = "startTime";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            LOGGER.info("*************");

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String times = request.getHeader(EXECUTE_TIME);

            List<Map<Integer, Long>> timeList = new LinkedList<>();
            Map<Integer, Long> map = new HashMap<>();

            if (StringUtils.isEmpty(times)) {
                map.put(0, System.currentTimeMillis());
                timeList.add(map);
                try {
                    requestTemplate.header(EXECUTE_TIME, objectMapper.writeValueAsString(timeList));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                TypeReference<LinkedList<Map<Integer, Long>>> typeRef = new TypeReference<LinkedList<Map<Integer, Long>>>() {
                };
                try {
                    timeList = objectMapper.readValue(times, typeRef);
                    map.put(timeList.size(), System.currentTimeMillis());
                    timeList.add(map);
                    requestTemplate.header(EXECUTE_TIME, objectMapper.writeValueAsString(timeList));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            LOGGER.info(requestTemplate.headers().toString());
        };
    }


    public static int connectTimeOutMillis = 10000;
    public static int readTimeOutMillis = 60000;
    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

}
