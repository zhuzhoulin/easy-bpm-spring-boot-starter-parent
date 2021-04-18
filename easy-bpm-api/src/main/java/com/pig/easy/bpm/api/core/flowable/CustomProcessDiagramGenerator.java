package com.pig.easy.bpm.api.core.flowable;

import org.flowable.bpmn.model.*;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.util.ObjectUtils;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * todo:
 *
 * @author : pig
 */
public class CustomProcessDiagramGenerator extends DefaultProcessDiagramGenerator {

    private ConcurrentHashMap<String, Object> flowNodeIdMap;
    private ConcurrentHashMap<String, String> flowNodeNameMap;
    private List<String> currentActivityIds;
    private ReentrantLock reentrantLock = new ReentrantLock();

    public CustomProcessDiagramGenerator(ConcurrentHashMap<String, Object> flowNodeIdMap,ConcurrentHashMap<String,String> flowNodeNameMap, List<String> currentActivityIds) {
        this.flowNodeIdMap = flowNodeIdMap;
        this.flowNodeNameMap = flowNodeNameMap;
        this.currentActivityIds = currentActivityIds;
    }

    public CustomProcessDiagramGenerator(double scaleFactor) {
        super(scaleFactor);
    }

    protected Graphics2D g;
    protected static Color LAST_USERTASK_HIGHLIGHT_COLOR = Color.red;
    protected static Stroke THICK_TASK_BORDER_STROKE;

    private static void drawHighLight(DefaultProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
    }

    private static void drawCurrentTaskHighLight(CustomProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawCurrentTaskHighLight1((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
    }

    private static void drawDisabledTaskHighLight(CustomProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawDisabledTaskHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
    }

    protected void drawActivity(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode, List<String> highLightedActivities, List<String> highLightedFlows, double scaleFactor, Boolean drawSequenceFlowNameWithNoLabelDI) {

        ActivityDrawInstruction drawInstruction = (ActivityDrawInstruction) this.activityDrawInstructions.get(flowNode.getClass());

        boolean highLighted;
        if (drawInstruction != null) {
            drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);
            flowNode.setName(flowNode.getName());
            boolean multiInstanceSequential = false;
            boolean multiInstanceParallel = false;
            highLighted = false;
            if (flowNode instanceof Activity) {
                Activity activity = (Activity) flowNode;
                MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
                if (multiInstanceLoopCharacteristics != null) {
                    multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
                    multiInstanceParallel = !multiInstanceSequential;
                }
            }

            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
            if (!(flowNode instanceof SubProcess)) {
                if (flowNode instanceof CallActivity) {
                    highLighted = true;
                }
            } else {
                highLighted = graphicInfo.getExpanded() != null && !graphicInfo.getExpanded().booleanValue();
            }

            if (scaleFactor == 1.0D) {
                processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), multiInstanceSequential, multiInstanceParallel, highLighted);
            }

            if (highLightedActivities.contains(flowNode.getId())) {
                drawHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
            } else if (currentActivityIds.contains(flowNode.getId())) {
                drawCurrentTaskHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
            } else if (ObjectUtils.isEmpty(flowNodeIdMap.get(flowNode.getId())) && !(flowNode instanceof StartEvent) && !(flowNode instanceof EndEvent)) {
                drawDisabledTaskHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
            }
        }

        Iterator var26 = flowNode.getOutgoingFlows().iterator();

        while (var26.hasNext()) {
            SequenceFlow sequenceFlow = (SequenceFlow) var26.next();
            highLighted = highLightedFlows.contains(sequenceFlow.getId());
            String defaultFlow = null;
            if (flowNode instanceof Activity) {
                defaultFlow = ((Activity) flowNode).getDefaultFlow();
            } else if (flowNode instanceof Gateway) {
                defaultFlow = ((Gateway) flowNode).getDefaultFlow();
            }

            boolean isDefault = false;
            if (defaultFlow != null && defaultFlow.equalsIgnoreCase(sequenceFlow.getId())) {
                isDefault = true;
            }

            boolean drawConditionalIndicator = sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);
            String sourceRef = sequenceFlow.getSourceRef();
            String targetRef = sequenceFlow.getTargetRef();
            FlowElement sourceElement = bpmnModel.getFlowElement(sourceRef);
            FlowElement targetElement = bpmnModel.getFlowElement(targetRef);
            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
            if (graphicInfoList != null && graphicInfoList.size() > 0) {
                graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);
                int[] xPoints = new int[graphicInfoList.size()];
                int[] yPoints = new int[graphicInfoList.size()];

                GraphicInfo lineCenter;
                for (int i = 1; i < graphicInfoList.size(); ++i) {
                    lineCenter = (GraphicInfo) graphicInfoList.get(i);
                    GraphicInfo previousGraphicInfo = (GraphicInfo) graphicInfoList.get(i - 1);
                    if (i == 1) {
                        xPoints[0] = (int) previousGraphicInfo.getX();
                        yPoints[0] = (int) previousGraphicInfo.getY();
                    }

                    xPoints[i] = (int) lineCenter.getX();
                    yPoints[i] = (int) lineCenter.getY();
                }

                processDiagramCanvas.drawSequenceflow(xPoints, yPoints, drawConditionalIndicator, isDefault, highLighted, scaleFactor);
                GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());

                if (labelGraphicInfo != null) {
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);

                } else if (drawSequenceFlowNameWithNoLabelDI.booleanValue()) {
                    lineCenter = getLineCenter2(graphicInfoList);
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, false);
                }
            }
        }

        if (flowNode instanceof FlowElementsContainer) {
            var26 = ((FlowElementsContainer) flowNode).getFlowElements().iterator();

            while (var26.hasNext()) {
                FlowElement nestedFlowElement = (FlowElement) var26.next();
                if (nestedFlowElement instanceof FlowNode && !this.isPartOfCollapsedSubProcess(nestedFlowElement, bpmnModel)) {
                    this.drawActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement, highLightedActivities, highLightedFlows, scaleFactor, drawSequenceFlowNameWithNoLabelDI);
                }
            }
        }
    }


    protected static GraphicInfo getLineCenter2(List<GraphicInfo> graphicInfoList) {

        GraphicInfo gi = new GraphicInfo();

        int xPoints[] = new int[graphicInfoList.size()];
        int yPoints[] = new int[graphicInfoList.size()];

        double length = 0;
        double[] lengths = new double[graphicInfoList.size()];
        lengths[0] = 0;
        double m;
        for (int i = 1; i < graphicInfoList.size(); i++) {
            GraphicInfo graphicInfo = graphicInfoList.get(i);
            GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

            if (i == 1) {
                xPoints[0] = (int) previousGraphicInfo.getX();
                yPoints[0] = (int) previousGraphicInfo.getY();
            }
            xPoints[i] = (int) graphicInfo.getX();
            yPoints[i] = (int) graphicInfo.getY();

            length += Math.sqrt(
                    Math.pow((int) graphicInfo.getX() - (int) previousGraphicInfo.getX(), 2) +
                            Math.pow((int) graphicInfo.getY() - (int) previousGraphicInfo.getY(), 2)
            );
            lengths[i] = length;
        }
        m = length / 2;
        int p1 = 0, p2 = 1;

        GraphicInfo graphicInfo1 = graphicInfoList.get(p1);
        GraphicInfo graphicInfo2 = graphicInfoList.get(p2);

        double AB = (int) graphicInfo2.getX() - (int) graphicInfo1.getX();
        double OA = (int) graphicInfo2.getY() - (int) graphicInfo1.getY();
        double OB = lengths[p2] - lengths[p1];
        double ob = m - lengths[p1];
        double ab = AB * ob / OB;
        double oa = OA * ob / OB;

        double mx, my;

        //自定义控制 标签显示的位置
        mx = graphicInfo1.getX() + AB / 2;
        my = graphicInfo1.getY() + OA / 2;

        gi.setX(mx);
        gi.setY(my);
        return gi;
    }

    protected static GraphicInfo getLineCenter(List<GraphicInfo> graphicInfoList) {
        GraphicInfo gi = new GraphicInfo();

        int xPoints[] = new int[graphicInfoList.size()];
        int yPoints[] = new int[graphicInfoList.size()];

        double length = 0;
        double[] lengths = new double[graphicInfoList.size()];
        lengths[0] = 0;
        double m;
        for (int i = 1; i < graphicInfoList.size(); i++) {
            GraphicInfo graphicInfo = graphicInfoList.get(i);
            GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

            if (i == 1) {
                xPoints[0] = (int) previousGraphicInfo.getX();
                yPoints[0] = (int) previousGraphicInfo.getY();
            }
            xPoints[i] = (int) graphicInfo.getX();
            yPoints[i] = (int) graphicInfo.getY();

            length += Math.sqrt(
                    Math.pow((int) graphicInfo.getX() - (int) previousGraphicInfo.getX(), 2) +
                            Math.pow((int) graphicInfo.getY() - (int) previousGraphicInfo.getY(), 2)
            );
            lengths[i] = length;
        }
        m = length / 2;
        int p1 = 0, p2 = 1;
        for (int i = 1; i < lengths.length; i++) {
            double len = lengths[i];
            p1 = i - 1;
            p2 = i;
            if (len > m) {
                break;
            }
        }


        GraphicInfo graphicInfo1 = graphicInfoList.get(p1);
        GraphicInfo graphicInfo2 = graphicInfoList.get(p2);

        double AB = (int) graphicInfo2.getX() - (int) graphicInfo1.getX();
        double OA = (int) graphicInfo2.getY() - (int) graphicInfo1.getY();
        double OB = lengths[p2] - lengths[p1];
        double ob = m - lengths[p1];
        double ab = AB * ob / OB;
        double oa = OA * ob / OB;

        double mx = graphicInfo1.getX() + ab;
        double my = graphicInfo1.getY() + oa;

        gi.setX(mx);
        gi.setY(my);
        return gi;

    }


    @Override
    protected DefaultProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI) {
        this.prepareBpmnModel(bpmnModel);
        CustomProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
        Iterator var13 = bpmnModel.getPools().iterator();

        while (var13.hasNext()) {
            Pool pool = (Pool) var13.next();
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo, scaleFactor);
        }

        var13 = bpmnModel.getProcesses().iterator();

        GraphicInfo graphicInfoAnnotation = new GraphicInfo(10, 10, 70, 250);
//        processDiagramCanvas.drawProcessTextAnnotation(annoText, graphicInfoAnnotation, 1.0D);

        org.flowable.bpmn.model.Process process;
        Iterator var22;
        while (var13.hasNext()) {
            process = (org.flowable.bpmn.model.Process) var13.next();
            var22 = process.getLanes().iterator();

            while (var22.hasNext()) {
                Lane lane = (Lane) var22.next();
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo, scaleFactor);
            }
        }

        var13 = bpmnModel.getProcesses().iterator();

        while (var13.hasNext()) {
            process = (org.flowable.bpmn.model.Process) var13.next();
            var22 = process.findFlowElementsOfType(FlowNode.class).iterator();

            while (var22.hasNext()) {

                reentrantLock.lock();
                FlowNode flowNode = (FlowNode) var22.next();
                String nodeName = flowNode.getName();

                if (!ObjectUtils.isEmpty(flowNodeIdMap.get(flowNode.getId()))) {
                    flowNode.setName(flowNodeNameMap.getOrDefault(flowNode.getId(),flowNode.getName()) + "(" + flowNodeIdMap.get(flowNode.getId()) + ")");
                }

                if (!this.isPartOfCollapsedSubProcess(flowNode, bpmnModel)) {
                    this.drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows, scaleFactor, Boolean.valueOf(drawSequenceFlowNameWithNoLabelDI));
                }
                flowNode.setName(nodeName);
                reentrantLock.unlock();
            }
        }

        var13 = bpmnModel.getProcesses().iterator();

        label75:
        while (true) {
            List subProcesses;
            do {
                if (!var13.hasNext()) {
                    return processDiagramCanvas;
                }

                process = (org.flowable.bpmn.model.Process) var13.next();
                var22 = process.getArtifacts().iterator();

                while (var22.hasNext()) {
                    Artifact artifact = (Artifact) var22.next();
                    this.drawArtifact(processDiagramCanvas, bpmnModel, artifact);
                }

                subProcesses = process.findFlowElementsOfType(SubProcess.class, true);
            } while (subProcesses == null);

            Iterator var26 = subProcesses.iterator();

            while (true) {
                GraphicInfo graphicInfo;
                SubProcess subProcess;
                do {
                    do {
                        if (!var26.hasNext()) {
                            continue label75;
                        }

                        subProcess = (SubProcess) var26.next();
                        graphicInfo = bpmnModel.getGraphicInfo(subProcess.getId());
                    }
                    while (graphicInfo != null && graphicInfo.getExpanded() != null && !graphicInfo.getExpanded().booleanValue());
                } while (this.isPartOfCollapsedSubProcess(subProcess, bpmnModel));

                Iterator var19 = subProcess.getArtifacts().iterator();

                while (var19.hasNext()) {
                    Artifact subProcessArtifact = (Artifact) var19.next();
                    this.drawArtifact(processDiagramCanvas, bpmnModel, subProcessArtifact);
                }
            }


        }
    }


    protected static CustomProcessDiagramCanvas initProcessDiagramCanvas(BpmnModel bpmnModel, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        double minX = 1.7976931348623157E308D;
        double maxX = 0.0D;
        double minY = 1.7976931348623157E308D;
        double maxY = 0.0D;

        GraphicInfo graphicInfo;
        for (Iterator var14 = bpmnModel.getPools().iterator(); var14.hasNext(); maxY = graphicInfo.getY() + graphicInfo.getHeight()) {
            Pool pool = (Pool) var14.next();
            graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            minX = graphicInfo.getX();
            maxX = graphicInfo.getX() + graphicInfo.getWidth();
            minY = graphicInfo.getY();
        }

        List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
        Iterator var24 = flowNodes.iterator();

        label155:
        while (var24.hasNext()) {
            FlowNode flowNode = (FlowNode) var24.next();

            GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
            if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
                maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
            }

            if (flowNodeGraphicInfo.getX() < minX) {
                minX = flowNodeGraphicInfo.getX();
            }

            if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
                maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
            }

            if (flowNodeGraphicInfo.getY() < minY) {
                minY = flowNodeGraphicInfo.getY();
            }

            Iterator var18 = flowNode.getOutgoingFlows().iterator();

            while (true) {
                List graphicInfoList;
                do {
                    if (!var18.hasNext()) {
                        continue label155;
                    }

                    SequenceFlow sequenceFlow = (SequenceFlow) var18.next();

                    graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());

                } while (graphicInfoList == null);

                Iterator var21 = graphicInfoList.iterator();

                while (var21.hasNext()) {
                    graphicInfo = (GraphicInfo) var21.next();
                    if (graphicInfo.getX() > maxX) {
                        maxX = graphicInfo.getX();
                    }

                    if (graphicInfo.getX() < minX) {
                        minX = graphicInfo.getX();
                    }

                    if (graphicInfo.getY() > maxY) {
                        maxY = graphicInfo.getY();
                    }

                    if (graphicInfo.getY() < minY) {
                        minY = graphicInfo.getY();
                    }
                }
            }
        }

        List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
        Iterator var27 = artifacts.iterator();

        //   GraphicInfo graphicInfo;
        while (var27.hasNext()) {
            Artifact artifact = (Artifact) var27.next();
            GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact.getId());
            if (artifactGraphicInfo != null) {
                if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX) {
                    maxX = artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth();
                }

                if (artifactGraphicInfo.getX() < minX) {
                    minX = artifactGraphicInfo.getX();
                }

                if (artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight() > maxY) {
                    maxY = artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight();
                }

                if (artifactGraphicInfo.getY() < minY) {
                    minY = artifactGraphicInfo.getY();
                }
            }

            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
            if (graphicInfoList != null) {
                Iterator var35 = graphicInfoList.iterator();

                while (var35.hasNext()) {
                    graphicInfo = (GraphicInfo) var35.next();
                    if (graphicInfo.getX() > maxX) {
                        maxX = graphicInfo.getX();
                    }

                    if (graphicInfo.getX() < minX) {
                        minX = graphicInfo.getX();
                    }

                    if (graphicInfo.getY() > maxY) {
                        maxY = graphicInfo.getY();
                    }

                    if (graphicInfo.getY() < minY) {
                        minY = graphicInfo.getY();
                    }
                }
            }
        }

        int nrOfLanes = 0;
        Iterator var30 = bpmnModel.getProcesses().iterator();

        while (var30.hasNext()) {
            org.flowable.bpmn.model.Process process = (org.flowable.bpmn.model.Process) var30.next();
            Iterator var34 = process.getLanes().iterator();

            while (var34.hasNext()) {
                Lane l = (Lane) var34.next();
                ++nrOfLanes;
                graphicInfo = bpmnModel.getGraphicInfo(l.getId());
                if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
                    maxX = graphicInfo.getX() + graphicInfo.getWidth();
                }

                if (graphicInfo.getX() < minX) {
                    minX = graphicInfo.getX();
                }

                if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
                    maxY = graphicInfo.getY() + graphicInfo.getHeight();
                }

                if (graphicInfo.getY() < minY) {
                    minY = graphicInfo.getY();
                }
            }
        }

        if (flowNodes.isEmpty() && bpmnModel.getPools().isEmpty() && nrOfLanes == 0) {
            minX = 0.0D;
            minY = 0.0D;
        }

        return new CustomProcessDiagramCanvas((int) maxX + 150, (int) maxY + 150, (int) minX, (int) minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }

}