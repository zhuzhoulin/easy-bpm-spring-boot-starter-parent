package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.generator.entity.DbConfigDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;
import java.util.List;
/**
 * <p>
 * 数据源表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-12
 */
public interface DbConfigService extends BaseService<DbConfigDO> {

    Result<PageInfo<DbConfigDTO>> getListPageByCondition(DbConfigQueryDTO param);

    Result<Integer> insertDbConfig(DbConfigSaveOrUpdateDTO param);

    Result<Integer> updateDbConfig(DbConfigSaveOrUpdateDTO param);

    Result<Integer> deleteDbConfig(DbConfigSaveOrUpdateDTO param);

    Result<DbConfigDTO> getDbConfigById(Long dbId);

    Result<List<DbConfigDTO>> getListByCondition(DbConfigQueryDTO param);
}
