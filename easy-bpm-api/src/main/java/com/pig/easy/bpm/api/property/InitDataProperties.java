package com.pig.easy.bpm.api.property;

import com.pig.easy.bpm.common.property.BaseProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/3 11:38
 */
@Data
@ToString
@ConfigurationProperties(prefix = InitDataProperties.PREFIX)
public class InitDataProperties extends BaseProperties{

    public transient static final String PREFIX = "easy.bpm.api.init-data";

    private static final long serialVersionUID = 1L;

    private Boolean autoInitData;

    private Boolean continueOnError;

    private String sqlScriptEncoding;
}
