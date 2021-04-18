package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/26 15:16
 */
@Data
@ToString
public class UserTaskInfoQueryDTO extends BaseRequestDTO {

    private static final long serialVersionUID = -1070238279536333547L;

    private Long applyId;

    private Long processId;

    private String procInstId;

    private List<Long> processIdList;

    private String applySn;

    private Integer applyStatus;

    private String companyCode;

    private String deptCode;

    private String tenantId;

    private String applyUserId;

    private String applyRealName;

    private String system;

    private Long taskId;

    private String taskNodeCode;

    private String taskName;

    private Long lastId;

    private Integer taskStatus;

    private LocalDateTime applyStartDate;

    private LocalDateTime applyEndDate;

    private String seachName;

    private int pageSize;

    private int pageIndex;

}
