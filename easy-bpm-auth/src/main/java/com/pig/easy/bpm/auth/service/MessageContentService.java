package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.MessageContentQueryDTO;
import com.pig.easy.bpm.auth.dto.request.MessageContentSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.MessageContentDTO;
import com.pig.easy.bpm.auth.entity.MessageContentDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 通知内容表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface MessageContentService extends BaseService<MessageContentDO> {

    Result<PageInfo<MessageContentDTO>> getListPageByCondition(MessageContentQueryDTO param);

    Result<Integer> insertMessageContent(MessageContentSaveOrUpdateDTO param);

    Result<Integer> updateMessageContent(MessageContentSaveOrUpdateDTO param);

    Result<Integer> deleteMessageContent(MessageContentSaveOrUpdateDTO param);

    Result<MessageContentDTO> getMessageContentById(Long contentId);

    Result<List<MessageContentDTO>> getListByCondition(MessageContentQueryDTO param);

    Result<MessageContentDTO> getMessageContent(MessageContentQueryDTO param);

    Result<MessageContentDTO> getMessageContent(Integer processId, String tenantId, String eventCode, String messageTypeCode, String messagePlatform);

}
