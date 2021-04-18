package com.pig.easy.bpm.api.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BaseElement;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.impl.bpmn.parser.BpmnParse;
import org.flowable.engine.parse.BpmnParseHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 10:54
 */
@AutoConfigureAfter({ProcessEngine.class, DataSource.class})
@Slf4j
@Component
public class BpmBpmnParseHandler implements BpmnParseHandler {
    @Override
    public Collection<Class<? extends BaseElement>> getHandledTypes() {
        return null;
    }

    @Override
    public void parse(BpmnParse bpmnParse, BaseElement element) {

    }
}
