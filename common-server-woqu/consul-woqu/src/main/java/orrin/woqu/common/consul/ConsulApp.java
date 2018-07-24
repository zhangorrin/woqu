package orrin.woqu.common.consul;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author orrin on 2018/7/2
 */
@Configuration
@EnableAutoConfiguration
@RestController
@EnableConfigurationProperties
@EnableFeignClients
@Slf4j
public class ConsulApp {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Autowired
    private SampleClient sampleClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Registration registration;

    @Value("${spring.application.name:testConsulApp}")
    private String appName;

    @RequestMapping("/me")
    public ServiceInstance me() {
        return this.registration;
    }

    @RequestMapping("/")
    public ServiceInstance lb() {
        return loadBalancer.choose(appName);
    }

    @RequestMapping("/rest")
    public String rest() {
        return this.restTemplate.getForObject("http://"+appName+"/me", String.class);
    }

    @RequestMapping("/choose")
    public String choose() {
        return loadBalancer.choose(appName).getUri().toString();
    }

    @RequestMapping("/myenv")
    public String env(@RequestParam("prop") String prop) {
        return env.getProperty(prop, "Not Found");
    }



    @RequestMapping("/instances")
    public List<ServiceInstance> instances() {
        return discoveryClient.getInstances(appName);
    }

    @RequestMapping("/feign")
    public String feign() {
        return sampleClient.choose();
    }

    @Bean
    public SampleProperties sampleProperties() {
        return new SampleProperties();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @FeignClient("testConsulApp")
    public interface SampleClient {

        @RequestMapping(value = "/choose", method = RequestMethod.GET)
        String choose();
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsulApp.class).web(true).run(args);
    }
}
