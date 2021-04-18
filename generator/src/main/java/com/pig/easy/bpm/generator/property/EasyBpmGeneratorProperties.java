package com.pig.easy.bpm.generator.property;

import com.pig.easy.bpm.common.property.BaseProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/3 11:12
 */
@Data
@ToString
@ConfigurationProperties(prefix = EasyBpmGeneratorProperties.PREFIX)
public class EasyBpmGeneratorProperties extends BaseProperties {

    public transient static final String PREFIX = "easy.bpm.generator";

    private static final long serialVersionUID = 1L;

    private InitDataProperties initData;

    private String version;
}
