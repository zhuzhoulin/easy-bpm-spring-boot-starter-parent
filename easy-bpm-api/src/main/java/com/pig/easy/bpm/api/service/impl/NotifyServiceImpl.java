package com.pig.easy.bpm.api.service.impl;

import com.pig.easy.bpm.api.dto.request.NotifyMessageDTO;
import com.pig.easy.bpm.api.dto.response.ApplyDTO;
import com.pig.easy.bpm.api.service.ApplyService;
import com.pig.easy.bpm.api.service.FormDataService;
import com.pig.easy.bpm.api.service.NotifyService;
import com.pig.easy.bpm.auth.dto.request.UserQueryDTO;
import com.pig.easy.bpm.auth.dto.response.MessageContentDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.service.MessageContentService;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.utils.SendEmailUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/12/31 16:20
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Value("${email.smtpHost}")
    private String smtpHost;

    @Value("${email.smtpPort}")
    private Integer smtpPort;

    @Value("${email.smtpUsername}")
    private String smtpUser;

    @Value("${email.smtpPassword}")
    private String smtpPass;

    @Autowired
    MessageContentService messageContentService;
    @Autowired
    UserService userService;
    @Resource
    FreeMarkerConfigurationFactory freeMarkerConfigurationFactory;
    @Resource
    HistoryService historyService;
    @Autowired
    ApplyService applyService;
    @Autowired
    FormDataService formDataService;


    @Override
    public Result<Boolean> sendNotify(NotifyMessageDTO notifyMessageDTO) {

        EasyBpmAsset.isAssetEmpty(notifyMessageDTO);
        EasyBpmAsset.isAssetEmpty(notifyMessageDTO.getEventCode());
        EasyBpmAsset.isAssetEmpty(notifyMessageDTO.getPaltForm());

        List<String> sendToUserEmailList = notifyMessageDTO.getSendToUserEmailList() != null ? notifyMessageDTO.getSendToUserEmailList() : new CopyOnWriteArrayList<>();

        if (notifyMessageDTO.getSendToUserIdList() != null && notifyMessageDTO.getSendToUserIdList().size() > 0) {
            List<Long> userIdList = new CopyOnWriteArrayList<>();
            UserQueryDTO userQueryDTO = new UserQueryDTO();
            userQueryDTO.setUserIdList(userIdList);
            Result<List<UserDTO>> result = userService.getListByCondition(userQueryDTO);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
            result.getData().stream().forEach(userInfoDTO -> {
                sendToUserEmailList.add(userInfoDTO.getEmail());
            });
        }

        String content = notifyMessageDTO.getContent();
        String subject = StringUtils.isEmpty(notifyMessageDTO.getSubject()) ? "EasyBpm": notifyMessageDTO.getSubject();
        if (StringUtils.isEmpty(content)){
            Result<MessageContentDTO> result = messageContentService.getMessageContent(notifyMessageDTO.getProcessId(), notifyMessageDTO.getTenantId(), notifyMessageDTO.getEventCode(), notifyMessageDTO.getMessageTypeCode(), notifyMessageDTO.getPaltForm());
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
             content = result.getData().getMessageContent();
        }

        Map<String, Object> businessData = Collections.synchronizedMap(new HashMap<>());

        if(CommonUtils.evalInt(notifyMessageDTO.getApplyId())>0){

            Result<Map<String, Object>> result = formDataService.getFormDataByApplyId(notifyMessageDTO.getApplyId());
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
            businessData.putAll(result.getData());

            Result<ApplyDTO> result2 = applyService.getApplyByApplyId(notifyMessageDTO.getApplyId());
            if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result2.getEntityError());
            }
            ApplyDTO applyDTO = result2.getData();
            List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(applyDTO.getProcInstId()).list();
            for (HistoricVariableInstance historicVariableInstance : list) {
                businessData.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
            }
        }

        if (notifyMessageDTO.getParamMap() != null && notifyMessageDTO.getParamMap().size() > 0) {
            businessData.putAll(notifyMessageDTO.getParamMap());
        }

        content = formatSendContent(content, businessData);
        try {
            log.info("sendEmail info params: {}", notifyMessageDTO);
            SendEmailUtils.sendEmail(smtpHost, smtpPort, smtpUser, smtpPass, sendToUserEmailList.toArray(new String[sendToUserEmailList.size()]), subject, content);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.responseError(EntityError.SYSTEM_ERROR);
        }

        return Result.responseSuccess(Boolean.TRUE);
    }

    private String formatSendContent(String content,Map<String,Object> businessData ){

        String formatContent = "";
        try {
            freemarker.template.Configuration configuration = freeMarkerConfigurationFactory.createConfiguration();
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("easyBpmTemplate", content);
            configuration.setTemplateLoader(stringLoader);
            freemarker.template.Template temp = null;
            temp = configuration.getTemplate("easyBpmTemplate", "utf-8");
            StringWriter stringWriter = new StringWriter(2048);
            temp.process(businessData, stringWriter);
            formatContent = stringWriter.toString();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return formatContent;
    }

}
