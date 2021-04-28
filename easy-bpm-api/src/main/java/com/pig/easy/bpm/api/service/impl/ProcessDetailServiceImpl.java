package com.pig.easy.bpm.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.ProcessDTO;
import com.pig.easy.bpm.api.dto.response.ProcessDetailDTO;
import com.pig.easy.bpm.api.entity.NodeDO;
import com.pig.easy.bpm.api.entity.ProcessDetailDO;
import com.pig.easy.bpm.api.mapper.ProcessDetailMapper;
import com.pig.easy.bpm.api.service.NodeService;
import com.pig.easy.bpm.api.service.ProcessDetailService;
import com.pig.easy.bpm.api.service.ProcessService;
import com.pig.easy.bpm.api.utils.FlowElementUtils;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Service
public class ProcessDetailServiceImpl extends BaseServiceImpl<ProcessDetailMapper, ProcessDetailDO> implements ProcessDetailService {

    @Autowired
    ProcessDetailMapper mapper;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ProcessService processService;
    @Autowired
    NodeService nodeService;

    @Override
    public Result<PageInfo<ProcessDetailDTO>> getListPageByCondition(ProcessDetailQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<ProcessDetailDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<ProcessDetailDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<ProcessDetailDTO>> getListByCondition(ProcessDetailQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<ProcessDetailDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<Boolean> checkProcessXml(String processXml) {
        if (StringUtils.isEmpty(processXml)) {
            return Result.responseSuccess(false);
        }
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] bytes = processXml.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(inputStream, "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xtr);
            //验证组装bpmnmodel是否正确
            ProcessValidator defaultProcessValidator = new ProcessValidatorFactory().createDefaultProcessValidator();
            List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
            if (validate.size() > 0) {
                log.error("checkProcessXml error " + JSONArray.toJSONString(validate));
                return Result.responseError(new EntityError(EntityError.SYSTEM_ERROR.getCode(), JSONArray.toJSONString(validate)));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Result.responseError(new EntityError(EntityError.SYSTEM_ERROR.getCode(), e.getMessage()));
        } catch (XMLStreamException e) {
            return Result.responseError(new EntityError(EntityError.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
        return Result.responseSuccess(true);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> processPublish(ProcessPublishDTO processPublishDTO) {

        EasyBpmAsset.isAssetEmpty(processPublishDTO);
        EasyBpmAsset.isAssetEmpty(processPublishDTO.getTenantId());
        EasyBpmAsset.isAssetEmpty(processPublishDTO.getProcessKey());
        EasyBpmAsset.isAssetEmpty(processPublishDTO.getProcessDetailId());
        EasyBpmAsset.isAssetEmpty(processPublishDTO.getProcessXml());

        ProcessDetailDO processDetailDO = mapper.selectById(processPublishDTO.getProcessDetailId());
        if (processDetailDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }

        /* 校验流程格式 */
        Result<Boolean> result = checkProcessXml(processPublishDTO.getProcessXml());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        /* 校验processKey */
        Result<ProcessDTO> result1 = processService.getProcessByProcessKey(processPublishDTO.getProcessKey());
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }

        ProcessDTO processDTO = result1.getData();
        Deployment deployment = null;
        try {
            deployment = repositoryService.createDeployment()
                    .name(processDTO.getProcessName())
                    .key(processPublishDTO.getProcessKey())
                    .tenantId(processPublishDTO.getTenantId())
                    .addBytes(processPublishDTO.getProcessKey() + ".bpmn20.xml", processPublishDTO.getProcessXml().getBytes("UTF-8"))
                    .deploy();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Result.responseError(new EntityError(EntityError.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

        ProcessDetailDO tempProcessDetail = processDetailDO;
        tempProcessDetail.setDefinitionId(((DeploymentEntityImpl) deployment).getDeployedArtifacts(ProcessDefinitionEntityImpl.class).get(0).getId());
        if (CommonUtils.evalLong(processPublishDTO.getOperatorId()) > 0) {
            tempProcessDetail.setOperatorId(processPublishDTO.getOperatorId());
        }
        if (StringUtils.isEmpty(processPublishDTO.getOperatorName())) {
            tempProcessDetail.setOperatorName(processPublishDTO.getOperatorName());
        }

        // 保存 nodeList
        Result<Boolean> result3 = saveNodeList(processPublishDTO.getProcessXml(),tempProcessDetail.getDefinitionId(), processPublishDTO.getProcessKey(),processPublishDTO.getOperatorId(),processPublishDTO.getOperatorName());
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }
        // 如果是已发布,则重新创建一个
        if (processDetailDO.getPublishStatus() == BpmConstant.PROCESS_DETAIL_PUBLISH) {
            tempProcessDetail.setProcessDetailId(null);
            tempProcessDetail.setPublishStatus(BpmConstant.PROCESS_DETAIL_PUBLISH);
            tempProcessDetail.setMainVersion(BpmConstant.PROCESS_DERTAIL_NOT_MAIN_VERSION);
            mapper.insert(tempProcessDetail);
        }

        if (processDetailDO.getPublishStatus() == BpmConstant.PROCESS_DETAIL_UNPUBLISH) {
            tempProcessDetail.setPublishStatus(BpmConstant.PROCESS_DETAIL_PUBLISH);
            mapper.updateById(tempProcessDetail);
        }

        // 判断 流程表 发布状态 如果为 未发布，则更新为已发布
        if (processDTO.getProcessStatus() == BpmConstant.PROCESS_DETAIL_UNPUBLISH) {
            ProcessQueryDTO processReqDTO = new ProcessQueryDTO();
            processReqDTO.setProcessStatus(BpmConstant.PROCESS_DETAIL_PUBLISH);
            processReqDTO.setProcessKey(processPublishDTO.getProcessKey());
            Result<Integer> result2 = processService.updateProcessByProcessKey(processReqDTO);
            if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result2.getEntityError());
            }
        }

        return Result.responseSuccess(true);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Boolean> updateDefaultVersion(Long processId, Long processDetailId, Long operatorId, String operatorName) {

        EasyBpmAsset.isAssetEmpty(processId);
        EasyBpmAsset.isAssetEmpty(processDetailId);
        EasyBpmAsset.isAssetEmpty(operatorId);
        EasyBpmAsset.isAssetEmpty(operatorName);

        Result<ProcessDetailDTO> result = getProcessDetailById(processDetailId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ProcessDetailDTO processDetailDTO = result.getData();

        Result<ProcessDTO> result1 = processService.getProcessById(processId);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }
        ProcessDTO processDTO = result1.getData();

        // 未发布流程不允许设置为默认主版本
        if (processDetailDTO.getPublishStatus() == BpmConstant.PROCESS_DETAIL_UNPUBLISH) {
            return Result.responseError(EntityError.UNPUBLISH_PROCESS_DETAIL_DEFAULT_ERROR);
        }

        // 如果当前版本为 主版本，并且 流程信息关联的为当前详细编号，则跳过
        if (processDetailDTO.getMainVersion() == BpmConstant.PROCESS_DERTAIL_IS_MAIN_VERSION
                && processDTO.getProcessDetailId().equals(processDetailDTO.getProcessDetailId())) {
            return Result.responseSuccess(true);
        }

        ProcessDetailDO tempProcessDetail = new ProcessDetailDO();
        tempProcessDetail.setMainVersion(BpmConstant.PROCESS_DERTAIL_NOT_MAIN_VERSION);
        tempProcessDetail.setOperatorName(operatorName);
        tempProcessDetail.setOperatorId(operatorId);
        tempProcessDetail.setUpdateTime(LocalDateTime.now());
        mapper.update(tempProcessDetail, new QueryWrapper<ProcessDetailDO>()
                .eq("main_version",BpmConstant.PROCESS_DERTAIL_IS_MAIN_VERSION)
                .eq("process_id", processId)
                .ne("process_detail_id", processDetailId));

        ProcessDetailSaveOrUpdateDTO processDetailQueryDTO = new ProcessDetailSaveOrUpdateDTO();
        processDetailQueryDTO.setProcessDetailId(processDetailId);
        processDetailQueryDTO.setOperatorId(operatorId);
        processDetailQueryDTO.setOperatorName(operatorName);
        processDetailQueryDTO.setMainVersion(BpmConstant.PROCESS_DERTAIL_IS_MAIN_VERSION);
        Result<Integer> result2 = updateProcessDetail(processDetailQueryDTO);
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }

        ProcessQueryDTO processQueryDTO = BeanUtils.switchToDTO(processDTO, ProcessQueryDTO.class);
        processQueryDTO.setProcessDetailId(processDetailId);
        Result<Integer> result3 = processService.updateProcessByProcessKey(processQueryDTO);
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }

        return Result.responseSuccess(true);
    }

    @Override
    public Result<Boolean> saveNodeList(String processXml, String definitionId,String processKey,Long operatorId,String operatorName) {

        Result<ProcessDTO> result1 = processService.getProcessByProcessKey(processKey);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }
        ProcessDTO processDTO = result1.getData();

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] bytes = new byte[0];
        try {
            bytes = processXml.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Result.responseError(EntityError.SYSTEM_ERROR);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = null;

        try {
            in = new InputStreamReader(inputStream, "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xtr);
            List<NodeDO> nodeDOList = FlowElementUtils.bpmnModelToNodeDOList(bpmnModel);
            List<NodeSaveOrUpdateDTO> addNodeInfoDTOS = new ArrayList<>();
            NodeSaveOrUpdateDTO nodeInfoDTO = null;
            for (NodeDO nodeDO : nodeDOList) {
                nodeInfoDTO = BeanUtils.switchToDTO(nodeDO,NodeSaveOrUpdateDTO.class);
                nodeInfoDTO.setProcessId(processDTO.getProcessId());
                nodeInfoDTO.setProcessKey(processDTO.getProcessKey());
                nodeInfoDTO.setTenantId(processDTO.getTenantId());
                nodeInfoDTO.setValidState(VALID_STATE);
                if (CommonUtils.evalLong(operatorId) > 0) {
                    nodeInfoDTO.setOperatorId(operatorId);
                }
                if (StringUtils.isEmpty(operatorName)) {
                    nodeInfoDTO.setOperatorName(operatorName);
                }
                addNodeInfoDTOS.add(nodeInfoDTO);
            }

            Result<Integer> result2 = nodeService.batchInsertOrUpdateNodeInfo(addNodeInfoDTOS, definitionId);
            if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result2.getEntityError());
            }


        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.responseError(EntityError.SYSTEM_ERROR);
        }
        return Result.responseSuccess(true);
    }

    @Override
    public Result<ProcessDetailDTO> getProcessDetailByProcessId(Long processId) {

        if (CommonUtils.evalLong(processId) < 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDetailDO processDetail = ProcessDetailDO.builder()
                .processId(processId)
                .mainVersion(BpmConstant.PROCESS_DERTAIL_IS_MAIN_VERSION)
                .validState(VALID_STATE)
                .build();
        QueryWrapper<ProcessDetailDO> entityWrapper = new QueryWrapper<>(processDetail);
        processDetail = mapper.selectOne(entityWrapper);

        ProcessDetailDTO processDetailDTO = BeanUtils.switchToDTO(processDetail, ProcessDetailDTO.class);
        return Result.responseSuccess(processDetailDTO);
    }

    @Override
    public Result<ProcessDetailDTO> getProcessDetailByDefinitionId(String definitionId) {
        if (StringUtils.isEmpty(definitionId)) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDetailDO processDetail = ProcessDetailDO.builder()
                .definitionId(definitionId)
                .build();
        QueryWrapper<ProcessDetailDO> entityWrapper = new QueryWrapper<>(processDetail);
        processDetail = mapper.selectOne(entityWrapper);

        ProcessDetailDTO processDetailDTO = BeanUtils.switchToDTO(processDetail, ProcessDetailDTO.class);
        return Result.responseSuccess(processDetailDTO);
    }



    @Override
    public Result<Integer> insertProcessDetail(ProcessDetailSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        ProcessDetailDO temp = BeanUtils.switchToDO(param, ProcessDetailDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(temp.getProcessDetailId().intValue());
    }

    @Override
    public Result<Integer>updateProcessDetail(ProcessDetailSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDetailDO temp = BeanUtils.switchToDO(param, ProcessDetailDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteProcessDetail(ProcessDetailSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDetailDO temp = BeanUtils.switchToDO(param, ProcessDetailDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<ProcessDetailDTO> getProcessDetailById(Long processDetailId){

        if (processDetailId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDetailDO query = new ProcessDetailDO();
        query.setProcessDetailId(processDetailId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ProcessDetailDTO result = BeanUtils.switchToDTO(query, ProcessDetailDTO.class);
        return Result.responseSuccess(result);
    }
}
