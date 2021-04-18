package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/26 16:54
 */
@Data
@ToString
public class UserTaskQueryVO implements Serializable {

    private static final long serialVersionUID = 6609412713865602311L;

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
