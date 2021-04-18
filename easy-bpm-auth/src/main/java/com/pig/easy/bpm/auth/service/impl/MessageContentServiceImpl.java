package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.MessageContentDO;
import com.pig.easy.bpm.auth.mapper.MessageContentMapper;
import com.pig.easy.bpm.auth.service.MessageContentService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 通知内容表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class MessageContentServiceImpl extends BaseServiceImpl<MessageContentMapper, MessageContentDO> implements MessageContentService {

    @Autowired
    MessageContentMapper mapper;

    @Override
    public Result<PageInfo<MessageContentDTO>> getListPageByCondition(MessageContentQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<MessageContentDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<MessageContentDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<MessageContentDTO>> getListByCondition(MessageContentQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<MessageContentDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<MessageContentDTO> getMessageContent(MessageContentQueryDTO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getMessagePlatform());
        EasyBpmAsset.isAssetEmpty(param.getMessageTypeCode());

        Integer processId = param.getProcessId();
        if (CommonUtils.evalInt(processId) <= 0) {
            param.setProcessId(0);
            processId = 0;
        }
        List<Integer> processIdList = new ArrayList<>();
        processIdList.add(param.getProcessId());

        if (!processIdList.contains(0)) {
            processIdList.add(0);
        }

        MessageContentDO messageContentDO = BeanUtils.switchToDO(param, MessageContentDO.class);
        messageContentDO.setEventCodes(null);
        messageContentDO.setProcessId(null);
        List<MessageContentDO> notifyContentDOList = mapper.selectList(new QueryWrapper<>(messageContentDO)
                .in("event_codes", param.getEventCodes())
                .in("process_id", processIdList));

        if (notifyContentDOList == null || notifyContentDOList.size() == 0) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        MessageContentDTO messageContentDTO = BeanUtils.switchToDTO(notifyContentDOList.get(0), MessageContentDTO.class);
        return Result.responseSuccess(messageContentDTO);

    }

    @Override
    public Result<MessageContentDTO> getMessageContent(Integer processId, String tenantId, String eventCode, String messageTypeCode, String messagePlatform) {

        MessageContentQueryDTO param = new MessageContentQueryDTO();
        param.setProcessId(processId);
        param.setTenantId(tenantId);
        param.setEventCodes(eventCode);
        param.setMessagePlatform(messagePlatform);
        param.setMessageTypeCode(messageTypeCode);

        return getMessageContent(param);
    }


    @Override
    public Result<Integer> insertMessageContent(MessageContentSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        MessageContentDO temp = BeanUtils.switchToDO(param, MessageContentDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateMessageContent(MessageContentSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MessageContentDO temp = BeanUtils.switchToDO(param, MessageContentDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteMessageContent(MessageContentSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MessageContentDO temp = BeanUtils.switchToDO(param, MessageContentDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<MessageContentDTO> getMessageContentById(Long contentId) {

        if (contentId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MessageContentDO query = new MessageContentDO();
        query.setContentId(contentId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        MessageContentDTO result = BeanUtils.switchToDTO(query, MessageContentDTO.class);
        return Result.responseSuccess(result);
    }
}
