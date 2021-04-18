package com.pig.easy.bpm.common.aspect;

import com.pig.easy.bpm.common.property.BaseProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author : pig
 */
@Component
@Data
@ConfigurationProperties(prefix = AspectProperties.PREFIX)
public class AspectProperties extends BaseProperties {

    public transient static final String PREFIX = "easy.bpm.aspect";

    private static final long serialVersionUID = 1L;

    private String pointcut;

    private boolean enable;
}
