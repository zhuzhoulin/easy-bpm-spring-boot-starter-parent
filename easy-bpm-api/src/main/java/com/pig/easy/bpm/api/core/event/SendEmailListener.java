package com.pig.easy.bpm.api.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/1/13 9:41
 */
@Component
public class SendEmailListener implements BpmEventListener {

    private Logger logger = LoggerFactory.getLogger(SendEmailListener.class);

    @Override
    public void onEvent(BpmEvent event) {
        BpmSourceDTO bpmSourceDTO = event.getBpmSourceDTO();
//        Object object = BeanUtils.objectToBean(bpmSourceDTO.getData(), bpmSourceDTO.getClassClz());
    }
}
