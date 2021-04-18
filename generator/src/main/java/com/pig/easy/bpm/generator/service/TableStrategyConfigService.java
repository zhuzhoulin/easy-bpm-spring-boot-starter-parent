package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.generator.entity.TableStrategyConfigDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;
import java.util.List;
/**
 * <p>
 * 配置策略表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
public interface TableStrategyConfigService extends BaseService<TableStrategyConfigDO> {

    Result<PageInfo<TableStrategyConfigDTO>> getListPageByCondition(TableStrategyConfigQueryDTO param);

    Result<Integer> insertTableStrategyConfig(TableStrategyConfigSaveOrUpdateDTO param);

    Result<Integer> updateTableStrategyConfig(TableStrategyConfigSaveOrUpdateDTO param);

    Result<Integer> deleteTableStrategyConfig(TableStrategyConfigSaveOrUpdateDTO param);

    Result<TableStrategyConfigDTO> getTableStrategyConfigById(Long configId);

    Result<List<TableStrategyConfigDTO>> getListByCondition(TableStrategyConfigQueryDTO param);
}
