package com.pig.easy.bpm.auth.service;

import com.pig.easy.bpm.auth.entity.MessageWhiteListDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;
import java.util.List;
/**
 * <p>
 * 通知白名单 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface MessageWhiteListService extends BaseService<MessageWhiteListDO> {

    Result<PageInfo<MessageWhiteListDTO>> getListPageByCondition(MessageWhiteListQueryDTO param);

    Result<Integer> insertMessageWhiteList(MessageWhiteListSaveOrUpdateDTO param);

    Result<Integer> updateMessageWhiteList(MessageWhiteListSaveOrUpdateDTO param);

    Result<Integer> deleteMessageWhiteList(MessageWhiteListSaveOrUpdateDTO param);

    Result<MessageWhiteListDTO> getMessageWhiteListById(String whiteId);

    Result<List<MessageWhiteListDTO>> getListByCondition(MessageWhiteListQueryDTO param);
}
