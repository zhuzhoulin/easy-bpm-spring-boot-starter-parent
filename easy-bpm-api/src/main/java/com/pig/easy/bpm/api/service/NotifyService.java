package com.pig.easy.bpm.api.service;


import com.pig.easy.bpm.api.dto.request.NotifyMessageDTO;
import com.pig.easy.bpm.common.utils.Result;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2020/12/31 16:12
 */
public interface NotifyService {

    Result<Boolean> sendNotify(NotifyMessageDTO notifyMessageDTO);
}
