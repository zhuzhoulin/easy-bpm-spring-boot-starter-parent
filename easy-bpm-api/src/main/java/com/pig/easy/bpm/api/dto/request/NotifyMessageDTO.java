package com.pig.easy.bpm.api.dto.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2020/12/31 16:14
 */
@Data
@ToString
public class NotifyMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tenantId;

    private String eventCode;

    private  String messageTypeCode;

    private String paltForm;

    private String subject;

    private String content;

    private List<String> sendToUserEmailList;

    private List<Long> sendToUserIdList;

    private Long applyId;

    private String nodeId;

    private String nodeName;

    private Long taskId;

    private Integer processId;

    private Map<String,Object> paramMap;


}
