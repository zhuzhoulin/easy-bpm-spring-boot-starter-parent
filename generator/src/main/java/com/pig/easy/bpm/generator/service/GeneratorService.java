package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.TableStrategyConfigSaveOrUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/28 16:29
 */
public interface GeneratorService {

    Result<Map<String, Object>> initAndExecute(Long dbId,Long templateGroupId,List<String> selectTemplateList,boolean download, boolean preView, String... tableNames);

    Result<Map<String, Object>> generatorCode(Long dbId, Long templateGroupId, List<String> selectTemplateList, TableStrategyConfigSaveOrUpdateDTO tableStrategyConfig, boolean download, boolean preView, String... tableNames);

}
