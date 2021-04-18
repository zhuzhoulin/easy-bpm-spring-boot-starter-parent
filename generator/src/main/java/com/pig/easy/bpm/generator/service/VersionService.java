package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.generator.entity.VersionDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;
import java.util.List;
/**
 * <p>
 * 版本表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-10
 */
public interface VersionService extends BaseService<VersionDO> {

    Result<PageInfo<VersionDTO>> getListPageByCondition(VersionQueryDTO param);

    Result<Integer> insertVersion(VersionSaveOrUpdateDTO param);

    Result<Integer> updateVersion(VersionSaveOrUpdateDTO param);

    Result<Integer> deleteVersion(VersionSaveOrUpdateDTO param);

    Result<VersionDTO> getVersionById(Long id);

    Result<List<VersionDTO>> getListByCondition(VersionQueryDTO param);
}
