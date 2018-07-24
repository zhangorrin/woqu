package orrin.woqu.common.consul;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author orrin on 2018/7/3
 */
@ConfigurationProperties("sample")
@Data
public class SampleProperties {

    private String prop = "default value";
}