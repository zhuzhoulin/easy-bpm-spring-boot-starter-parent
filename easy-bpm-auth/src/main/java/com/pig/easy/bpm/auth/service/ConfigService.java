package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.ConfigQueryDTO;
import com.pig.easy.bpm.auth.dto.request.ConfigSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigDTO;
import com.pig.easy.bpm.auth.entity.ConfigDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface ConfigService extends BaseService<ConfigDO> {

    Result<PageInfo<ConfigDTO>> getListPageByCondition(ConfigQueryDTO param);

    Result<Integer> insertConfig(ConfigSaveOrUpdateDTO param);

    Result<Integer> updateConfig(ConfigSaveOrUpdateDTO param);

    Result<Integer> deleteConfig(ConfigSaveOrUpdateDTO param);

    Result<ConfigDTO> getConfigById(Long configId);

    Result<List<ConfigDTO>> getListByCondition(ConfigQueryDTO param);

    Result<Object> getConfigValue(String tenantId, String configKey);
}
