package com.pig.easy.bpm.api.service;

import com.pig.easy.bpm.api.entity.ProcessRuleDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
public interface ProcessRuleService extends BaseService<ProcessRuleDO> {

    Result<PageInfo<ProcessRuleDTO>> getListPageByCondition(ProcessRuleQueryDTO param);

    Result<Integer> insertProcessRule(ProcessRuleSaveOrUpdateDTO param);

    Result<Integer> updateProcessRule(ProcessRuleSaveOrUpdateDTO param);

    Result<Integer> deleteProcessRule(ProcessRuleSaveOrUpdateDTO param);

    Result<ProcessRuleDTO> getProcessRuleById(String ruleId);

    Result<List<ProcessRuleDTO>> getListByCondition(ProcessRuleQueryDTO param);
}
