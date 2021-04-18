package com.pig.easy.bpm.api.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * todo:
 *
 * @author :
 * @date : 2020/10/29 16:39
 */

public class BpmEventListenerImpl implements BpmEventListener {

    private Logger logger = LoggerFactory.getLogger(BpmEventListenerImpl.class);

    private BpmSourceDTO bpmSourceDTO;

    @Override
    public void onEvent(BpmEvent event) {

        if (event == null) {
            if(logger.isDebugEnabled()) {
                logger.debug("even must not be null !");
            }
            throw new RuntimeException("even must not be null !");
        }
        bpmSourceDTO = event.getBpmSourceDTO();

        switch (BpmEventType.valueOf(bpmSourceDTO.getEventType().name())){

            case ENTITY_CREATED:
                break;

            case ENTITY_UPDATED:
                break;

            default:


                break;
        }
    }
}
