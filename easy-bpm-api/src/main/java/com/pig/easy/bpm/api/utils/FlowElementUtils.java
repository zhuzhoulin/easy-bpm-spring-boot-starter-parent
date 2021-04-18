package com.pig.easy.bpm.api.utils;

import com.pig.easy.bpm.api.entity.NodeDO;
import com.pig.easy.bpm.common.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/19 18:03
 */
public class FlowElementUtils {

    public static final String CUSTOME_EXTENSIONELEMENT = "customProperties";

    /**
     * 功能描述: 从 flowElement 获取 指定名称的 拓展元素
     *
     * @param flowElement          元素
     * @param extensionElementName 拓展元素名称
     * @return : org.flowable.bpmn.model.ExtensionElement
     * @author : pig
     * @date : 2020/6/19 18:28
     */
    public static ExtensionElement getExtensionElementFromFlowElementByName(FlowElement flowElement, String extensionElementName) {

        if (flowElement == null) {
            return null;
        }
        if (StringUtils.isEmpty(extensionElementName)) {
            extensionElementName = CUSTOME_EXTENSIONELEMENT;
        }
        Map<String, List<ExtensionElement>> extensionElements = flowElement.getExtensionElements();
        for (Map.Entry<String, List<ExtensionElement>> stringEntry : extensionElements.entrySet()) {
            if (stringEntry.getKey().equals(extensionElementName)) {
                for (ExtensionElement extensionElement : stringEntry.getValue()) {
                    if (extensionElement.getName().equals(extensionElementName)) {
                        return extensionElement;
                    }
                }
            }
        }

        return null;
    }

    /**
     * 功能描述: 从拓展元素 获取 拓展 属性值
     *
     * @param extensionElement 拓展元素
     * @param attributesName   属性名称
     * @return : java.lang.String
     * @author : pig
     * @date : 2020/6/19 18:30
     */
    public static String getAttributesFromExtensionElementByName(ExtensionElement extensionElement, String attributesName) {

        if (extensionElement == null
                || StringUtils.isEmpty(attributesName)) {
            return null;
        }
        Map<String, List<ExtensionAttribute>> stringListMap = extensionElement.getAttributes();
        for (Map.Entry<String, List<ExtensionAttribute>> listEntry : stringListMap.entrySet()) {
            if (listEntry.getKey().equals(attributesName)) {
                return listEntry.getValue() != null && listEntry.getValue().size() > 0 ? listEntry.getValue().get(0).getValue() : null;
            }
        }
        return null;
    }

    public static List<NodeDO> bpmnModelToNodeDOList(BpmnModel bpmnModel) {

        List<NodeDO> nodeDOList = new ArrayList<>();

        if (bpmnModel != null
                && bpmnModel.getProcesses().size() > 0) {
            org.flowable.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
            switchNodeList(process.getFlowElements(), nodeDOList);
        }
        return nodeDOList;
    }

    private static void switchNodeList(Collection<FlowElement> flowElementList, List<NodeDO> nodeDOList) {
        NodeDO nodeDO = null;
        for (FlowElement flowElement : flowElementList) {
            if (flowElement instanceof SubProcess) {
                switchNodeList(((SubProcess) flowElement).getFlowElements(), nodeDOList);
            } else {
                nodeDO = new NodeDO();
                ExtensionElement extensionElement = getExtensionElementFromFlowElementByName(flowElement, null);

                for (Field field : nodeDO.getClass().getDeclaredFields()) {

                    if(field.getName().equals("serialVersionUID")
                            || "pkVal".equals(field.getName())){
                        continue;
                    }

                    field.setAccessible(true);

                    if (field.getName().equals("nodeId")) {
                        setProperty(nodeDO, field.getName(), flowElement.getId());
                        continue;
                    }
                    if (field.getName().equals("nodeName")) {
                        setProperty(nodeDO, field.getName(), flowElement.getName());
                        continue;
                    }

                    if (field.getName().equals("nodeType")) {
                        setProperty(nodeDO, field.getName(), flowElement.getId().split("_")[0]);
                        continue;
                    }

                    if (field.getName().equals("formKey")) {
                        setProperty(nodeDO, field.getName(), getAttributesFromExtensionElementByName(extensionElement, "selectFormKey"));
                        continue;
                    }

                    String fieldValueString = getAttributesFromExtensionElementByName(extensionElement, field.getName());
                    setProperty(nodeDO, field.getName(), fieldValueString);
                }
                nodeDOList.add(nodeDO);
            }
        }
    }

    public static void setProperty(final Object bean, final String name, final Object value) {


        BeanUtils.setProperty(bean, name, value);
    }


    public static FlowElement getSubProcess(Collection<FlowElement> flowElements, FlowElement flowElement) {
        for (FlowElement flowElement1 : flowElements) {
            if (flowElement1 instanceof SubProcess) {
                for (FlowElement flowElement2 : ((SubProcess) flowElement1).getFlowElements()) {
                    if (flowElement.equals(flowElement2)) {
                        return flowElement1;
                    }
                }
            }
        }
        return null;
    }

    public static FlowElement getFlowElementById(String Id, Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement.getId().equals(Id)) {
                //如果是子任务，则查询出子任务的开始节点
                if (flowElement instanceof SubProcess) {
                    return getStartFlowElement(((SubProcess) flowElement).getFlowElements());
                }
                return flowElement;
            }
            if (flowElement instanceof SubProcess) {
                FlowElement flowElement1 = getFlowElementById(Id, ((SubProcess) flowElement).getFlowElements());
                if (flowElement1 != null) {
                    return flowElement1;
                }
            }
        }
        return null;
    }

    public static FlowElement getStartFlowElement(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof StartEvent) {
                return flowElement;
            }
        }
        return null;
    }

    public static boolean getJuleValue(String expression, Map<String, Object> formDataMap) {

        if (StringUtils.isEmpty(expression)) {
            return false;
        }
        org.flowable.common.engine.impl.javax.el.ExpressionFactory factory = new org.flowable.common.engine.impl.de.odysseus.el.ExpressionFactoryImpl();
        org.flowable.common.engine.impl.de.odysseus.el.util.SimpleContext context = new org.flowable.common.engine.impl.de.odysseus.el.util.SimpleContext();

        for (Object k : formDataMap.keySet()) {
            if (!ObjectUtils.isEmpty(formDataMap.get(k))) {
                context.setVariable(k.toString(), factory.createValueExpression(formDataMap.get(k), formDataMap.get(k).getClass()));
            }
        }
        org.flowable.common.engine.impl.javax.el.ValueExpression e = factory.createValueExpression(context, expression, boolean.class);
        return (Boolean) e.getValue(context);
    }
}
