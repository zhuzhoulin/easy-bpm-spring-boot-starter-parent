package com.pig.easy.bpm.generator.vo.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/2 20:49
 */
@Data
@ToString
public class GeneratorVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dbId;

    private Long templeateId;

    private String includeTables;

    private Boolean preView;

    private Boolean download;

    private List<String> selectTemplateList;

    private TableStrategyConfigSaveOrUpdateVO tableStrategyConfig;
}
