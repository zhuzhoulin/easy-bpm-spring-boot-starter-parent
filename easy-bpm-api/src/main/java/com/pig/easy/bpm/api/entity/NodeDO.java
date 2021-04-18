package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 流程节点表
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_node")
public class NodeDO extends Model<NodeDO> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    /**
     * 节点编号
     */
    @TableField("`node_id`")
    private String nodeId;

    /**
     * 节点名称
     */
    @TableField("`node_name`")
    private String nodeName;

    /**
     * 版本号
     */
    @TableField("`definition_id`")
    private String definitionId;

    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Long processId;

    /**
     * 流程key
     */
    @TableField("`process_key`")
    private String processKey;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 节点类型
     */
    @TableField("`node_type`")
    private String nodeType;

    /**
     * 任务类型 当前任务类型  start 起草 approve 审批  record  备案 archive 归档
     */
    @TableField("`task_type`")
    private String taskType;

    /**
     * 优先级
     */
    @TableField("`priority`")
    private Integer priority;

    /**
     * 关联表单KEY
     */
    @TableField("`form_key`")
    private String formKey;

    /**
     * 表单名称
     */
    @TableField("`form_name`")
    private String formName;

    /**
     * 1 内置表单, 2 外置表单
     */
    @TableField("`form_type`")
    private Integer formType;

    /**
     * 节点人员编号列表
     */
    @TableField("`user_id_list`")
    private String userIdList;

    /**
     * 节点人员名称列表，。多个人以 , 区分
     */
    @TableField("`user_name_list`")
    private String userNameList;

    /**
     * 节点角色组编号
     */
    @TableField("`role_group_code`")
    private String roleGroupCode;

    /**
     * 角色组名称
     */
    @TableField("`role_group_name`")
    private String roleGroupName;

    /**
     * 角色编码
     */
    @TableField("`role_code`")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField("`role_name`")
    private String roleName;

    /**
     * 节点用户类型 组合方式：1  角色组 2. 角色 3. 固定人员 4. 前端指定人员 5:申请人 6:同节点人员
     */
    @TableField("`find_user_type`")
    private Integer findUserType;

    /**
     * 组合方式：1 正常(找不到节点人员提示异常) 2 正常（找不到节点人员就跳过当前环节） 
     */
    @TableField("`combine_type`")
    private Integer combineType;

    /**
     * 用户节点人员分配字段名称
     */
    @TableField("`assignee_field`")
    private String assigneeField;

    /**
     * 是否选择路径 0 否 1 是
     */
    @TableField("`select_path`")
    private Integer selectPath;

    /**
     * 节点处理策略 skip: 执行人为空跳过,admin: 为空时管理员处理,error:为空时报错
     */
    @TableField("`handler_strategy`")
    private String handlerStrategy;

    /**
     * 依赖节点
     */
    @TableField("`relation_node_id`")
    private String relationNodeId;

    /**
     * 动作集合
     */
    @TableField("`action_list`")
    private String actionList;

    /**
     * 用户任务条件跳过表达式
     */
    @TableField("`skip_expression`")
    private String skipExpression;

    /**
     * 连线表达式
     */
    @TableField("`expression`")
    private String expression;

    /**
     * 连线来源节点NodeId
     */
    @TableField("`source_ref`")
    private String sourceRef;

    /**
     * 连线目标节点NodeId
     */
    @TableField("`target_ref`")
    private String targetRef;

    /**
     * 用户任务 多实例属性 parallel 并行审批，sequential 串行审批
     */
    @TableField("`sequential`")
    private String sequential;

    /**
     * 通过比例
     */
    @TableField("`proportion`")
    private String proportion;

    /**
     * 自定义查找人SQL
     */
    @TableField("`custom_sql`")
    private String customSql;

    /**
     * 自定义拓展数据集合JSON对象格式
     */
    @TableField("`custom_data`")
    private String customData;

    /**
     * 备注
     */
    @TableField("`remarks`")
    private String remarks;

    /**
     * 状态 1 有效 0 失效
     */
    @TableField("`valid_state`")
    private Integer validState;

    /**
     * 操作人工号
     */
    @TableField("`operator_id`")
    private Long operatorId;

    /**
     * 操作人姓名
     */
    @TableField("`operator_name`")
    private String operatorName;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public String getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(String userIdList) {
        this.userIdList = userIdList;
    }

    public String getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(String userNameList) {
        this.userNameList = userNameList;
    }

    public String getRoleGroupCode() {
        return roleGroupCode;
    }

    public void setRoleGroupCode(String roleGroupCode) {
        this.roleGroupCode = roleGroupCode;
    }

    public String getRoleGroupName() {
        return roleGroupName;
    }

    public void setRoleGroupName(String roleGroupName) {
        this.roleGroupName = roleGroupName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getFindUserType() {
        return findUserType;
    }

    public void setFindUserType(Integer findUserType) {
        this.findUserType = findUserType;
    }

    public Integer getCombineType() {
        return combineType;
    }

    public void setCombineType(Integer combineType) {
        this.combineType = combineType;
    }

    public String getAssigneeField() {
        return assigneeField;
    }

    public void setAssigneeField(String assigneeField) {
        this.assigneeField = assigneeField;
    }

    public Integer getSelectPath() {
        return selectPath;
    }

    public void setSelectPath(Integer selectPath) {
        this.selectPath = selectPath;
    }

    public String getHandlerStrategy() {
        return handlerStrategy;
    }

    public void setHandlerStrategy(String handlerStrategy) {
        this.handlerStrategy = handlerStrategy;
    }

    public String getRelationNodeId() {
        return relationNodeId;
    }

    public void setRelationNodeId(String relationNodeId) {
        this.relationNodeId = relationNodeId;
    }

    public String getActionList() {
        return actionList;
    }

    public void setActionList(String actionList) {
        this.actionList = actionList;
    }

    public String getSkipExpression() {
        return skipExpression;
    }

    public void setSkipExpression(String skipExpression) {
        this.skipExpression = skipExpression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public String getSequential() {
        return sequential;
    }

    public void setSequential(String sequential) {
        this.sequential = sequential;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getCustomSql() {
        return customSql;
    }

    public void setCustomSql(String customSql) {
        this.customSql = customSql;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getValidState() {
        return validState;
    }

    public void setValidState(Integer validState) {
        this.validState = validState;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
