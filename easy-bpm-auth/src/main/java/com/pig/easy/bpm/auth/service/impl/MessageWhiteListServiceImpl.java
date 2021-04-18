package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.MessageWhiteListDO;
import com.pig.easy.bpm.auth.mapper.MessageWhiteListMapper;
import com.pig.easy.bpm.auth.service.MessageWhiteListService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
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
 * 通知白名单 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class MessageWhiteListServiceImpl extends BaseServiceImpl<MessageWhiteListMapper, MessageWhiteListDO> implements MessageWhiteListService {

    @Autowired
    MessageWhiteListMapper mapper;

    @Override
    public Result<PageInfo<MessageWhiteListDTO>> getListPageByCondition(MessageWhiteListQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<MessageWhiteListDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<MessageWhiteListDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<MessageWhiteListDTO>> getListByCondition(MessageWhiteListQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<MessageWhiteListDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertMessageWhiteList(MessageWhiteListSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        MessageWhiteListDO temp = BeanUtils.switchToDO(param, MessageWhiteListDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateMessageWhiteList(MessageWhiteListSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MessageWhiteListDO temp = BeanUtils.switchToDO(param, MessageWhiteListDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteMessageWhiteList(MessageWhiteListSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MessageWhiteListDO temp = BeanUtils.switchToDO(param, MessageWhiteListDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<MessageWhiteListDTO> getMessageWhiteListById(String whiteId){

        if (whiteId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MessageWhiteListDO query = new MessageWhiteListDO();
        query.setWhiteId(whiteId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        MessageWhiteListDTO result = BeanUtils.switchToDTO(query, MessageWhiteListDTO.class);
        return Result.responseSuccess(result);
    }
}
