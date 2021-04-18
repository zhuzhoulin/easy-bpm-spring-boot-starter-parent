package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 节点人员表
 * </p>
 *
 * @author pig
 * @since 2020-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_node_user")
public class NodeUserDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 节点人员名称
     */
    @TableId(value = "node_user_id", type = IdType.AUTO)
    private Long nodeUserId;

    /**
     * 申请编号
     */
    private Long applyId;

    /**
     * 流程实例编号
     */
    private String procInstId;

    /**
     * 流程编号
     */
    private Long processId;

    /**
     * 流程KEY
     */
    private String processKey;

    /**
     * 节点编号
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 父节点编号
     */
    private String parentNodeId;

    /**
     * 父节点名称
     */
    private String parentNodeName;

    /**
     * 流程定义编号
     */
    private String definitionId;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 节点分配人员工号 多个人以 , 区分
     */
    private String assigneeUserIdList;

    /**
     * 节点分配人员姓名 多个人以 , 区分
     */
    private String assigneeUserNameList;

    /**
     * 当处理策略为 节点找不到人时 跳过则为 1，否则为 0
     */
    @TableField("skip")
    private Integer skip;

    /**
     * 当处理策略为 节点找不到人时 管理员处理则为 1，否则为 0
     */
    @TableField("default_set_admin")
    private Integer defaultSetAdmin;

    /**
     * 当处理策略为 节点找不到人时 抛出异常处理则为 1，否则为 0
     */
    private Integer error;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer validState;

    /**
     * 操作人工号
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
