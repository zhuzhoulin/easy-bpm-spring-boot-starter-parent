package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/20 10:24
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskReqDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 1133408342648490921L;

    private Long taskId;

    private String actTaskId;

    private Long applyId;

    private Long processId;

    private List<Long> processIdList;

    private String procInstId;

    private String tenantId;

    private Integer taskStatus;

    private List<Integer> taskStatusList;

    private Long taskAssigneeUserId;

    private String taskAssigneeRealName;

    private Integer taskPriority;

    private String taskType;

    private String taskName;

    private String taskNodeCode;

    private String formKey;

    private Date claimTime;

    private LocalDateTime dueTime;

    private LocalDateTime approveTime;

    private String system;

    private String platform;

    private LocalDateTime applyStartDate;

    private LocalDateTime applyEndDate;

    private Integer validState;

    private int pageSize;

    private int pageIndex;

}
