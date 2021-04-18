package com.pig.easy.bpm.api.core.flowable;

import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/28 10:20
 */
public class BpmProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
    @Override
    protected void prepareBpmnModel(BpmnModel bpmnModel) {

        if (bpmnModel.getProcesses().get(0).getFlowElements().size() > (bpmnModel.getLocationMap().size() + bpmnModel.getFlowLocationMap().size())) {
            new BpmnAutoLayout(bpmnModel).execute();
        }
        super.prepareBpmnModel(bpmnModel);
    }
}
