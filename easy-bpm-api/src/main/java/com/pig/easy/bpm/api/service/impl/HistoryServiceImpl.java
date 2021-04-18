package com.pig.easy.bpm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pig.easy.bpm.api.dto.request.HistorySaveDTO;
import com.pig.easy.bpm.api.dto.response.ApplyDTO;
import com.pig.easy.bpm.api.dto.response.HistoryDTO;
import com.pig.easy.bpm.api.entity.HistoryDO;
import com.pig.easy.bpm.api.mapper.HistoryMapper;
import com.pig.easy.bpm.api.service.ApplyService;
import com.pig.easy.bpm.api.service.HistoryService;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 审批历史表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-07-01
 */
@Service
public class HistoryServiceImpl extends BaseServiceImpl<HistoryMapper, HistoryDO> implements HistoryService {

    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    @Lazy
    ApplyService applyService;

    @Override
    public Result<List<HistoryDTO>> getListByApplyId(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);

        Result<List<ApplyDTO>> result1 = applyService.getChildrenListByApplyId(applyId);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }
        List<Long> applyList = new ArrayList<>();
        applyList.add(applyId);
        for (ApplyDTO applyDTO : result1.getData()) {
            if (!applyList.contains(applyDTO.getApplyId())) {
                applyList.add(applyDTO.getApplyId());
            }
        }
        QueryWrapper<HistoryDO> queryWrapper = new QueryWrapper<>(HistoryDO.builder().validState(VALID_STATE).build())
                .in("apply_id", applyList)
                .orderByDesc("apply_id","history_id");
        List<HistoryDO> list = historyMapper.selectList(queryWrapper);
        if (list == null) {
            list = new ArrayList<>();
        }
        List<HistoryDTO> result = new ArrayList<>();
        HistoryDTO historyDTO = null;
        for (HistoryDO historyDO : list) {
            historyDTO = BeanUtils.switchToDTO(historyDO, HistoryDTO.class);
            result.add(historyDTO);
        }

        return Result.responseSuccess(result);
    }

    @Override
    public Result<HistoryDTO> getHistoryByTaskId(Long taskId) {

        EasyBpmAsset.isAssetEmpty(taskId);
        HistoryDO historyDO = historyMapper.selectOne(new QueryWrapper<>(HistoryDO.builder().taskId(taskId).build()));
        if (historyDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        HistoryDTO historyDTO = BeanUtils.switchToDTO(historyDO, HistoryDTO.class);

        return Result.responseSuccess(historyDTO);
    }

    @Override
    public Result<Integer> addHistory(HistorySaveDTO historySaveDTO) {

        EasyBpmAsset.isAssetEmpty(historySaveDTO);
        EasyBpmAsset.isAssetEmpty(historySaveDTO.getApplyId());
        EasyBpmAsset.isAssetEmpty(historySaveDTO.getApproveOpinion());

        HistoryDO historyDO = BeanUtils.switchToDO(historySaveDTO, HistoryDO.class);
        int insert = historyMapper.insert(historyDO);

        return Result.responseSuccess(insert);
    }

    @Override
    public Result<Integer> updateHistoryById(HistorySaveDTO historySaveDTO) {

        EasyBpmAsset.isAssetEmpty(historySaveDTO);
        EasyBpmAsset.isAssetEmpty(historySaveDTO.getHistoryId());

        HistoryDO historyDO = BeanUtils.switchToDO(historySaveDTO, HistoryDO.class);
        int num = historyMapper.updateById(historyDO);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> insertHistory(Long applyId, String tenantId, Long taskId, String taskName, Long approverUserId, String approveRealName, String approveActionCode, String approveOpinion,String systemCode, String platform) {

        EasyBpmAsset.isAssetEmpty(applyId);
        EasyBpmAsset.isAssetEmpty(tenantId);
        EasyBpmAsset.isAssetEmpty(approverUserId);
        EasyBpmAsset.isAssetEmpty(approveRealName);
        EasyBpmAsset.isAssetEmpty(approveOpinion);

        HistoryDO historyDO = HistoryDO.builder().applyId(applyId).tenantId(tenantId)
                .approveUserId(approverUserId).approveRealName(approveRealName).approveOpinion(approveOpinion)
                .operatorId(approverUserId).operatorName(approveRealName).build();
        if (CommonUtils.evalLong(taskId) > 0) {
            historyDO.setTaskId(taskId);
        }
        if (!StringUtils.isEmpty(taskName)) {
            historyDO.setTaskName(taskName);
        }
        if (!StringUtils.isEmpty(approveActionCode)) {

            historyDO.setApproveActionCode(approveActionCode);
            // 这里需要放入MAP获取
            historyDO.setApproveActionName(approveActionCode);
        }
        if (!StringUtils.isEmpty(systemCode)) {
            historyDO.setSystem(systemCode);
        }
        if (!StringUtils.isEmpty(platform)) {
            historyDO.setPlatform(platform);
        }
        int insert = historyMapper.insert(historyDO);
        return Result.responseSuccess(insert);
    }
}
