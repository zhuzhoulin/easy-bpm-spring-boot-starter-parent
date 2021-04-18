package com.pig.easy.bpm.api.dto.response;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.math.*;
/**
 * <p>
    * 流程节点表
    * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long  id;

    /**
     * 节点编号
     */
    private String  nodeId;

    /**
     * 节点名称
     */
    private String  nodeName;

    /**
     * 版本号
     */
    private String  definitionId;

    /**
     * 流程编号
     */
    private Long  processId;

    /**
     * 流程key
     */
    private String  processKey;

    /**
     * 租户编号
     */
    private String  tenantId;

    /**
     * 节点类型
     */
    private String  nodeType;

    /**
     * 任务类型 当前任务类型  start 起草 approve 审批  record  备案 archive 归档
     */
    private String  taskType;

    /**
     * 优先级
     */
    private Integer  priority;

    /**
     * 关联表单KEY
     */
    private String  formKey;

    /**
     * 表单名称
     */
    private String  formName;

    /**
     * 1 内置表单, 2 外置表单
     */
    private Integer  formType;

    /**
     * 节点人员编号列表
     */
    private String  userIdList;

    /**
     * 节点人员名称列表，。多个人以 , 区分
     */
    private String  userNameList;

    /**
     * 节点角色组编号
     */
    private String  roleGroupCode;

    /**
     * 角色组名称
     */
    private String  roleGroupName;

    /**
     * 角色编码
     */
    private String  roleCode;

    /**
     * 角色名称
     */
    private String  roleName;

    /**
     * 节点用户类型 组合方式：1  角色组 2. 角色 3. 固定人员 4. 前端指定人员 5:申请人 6:同节点人员
     */
    private Integer  findUserType;

    /**
     * 组合方式：1 正常(找不到节点人员提示异常) 2 正常（找不到节点人员就跳过当前环节） 
     */
    private Integer  combineType;

    /**
     * 用户节点人员分配字段名称
     */
    private String  assigneeField;

    /**
     * 是否选择路径 0 否 1 是
     */
    private Integer  selectPath;

    /**
     * 节点处理策略 skip: 执行人为空跳过,admin: 为空时管理员处理,error:为空时报错
     */
    private String  handlerStrategy;

    /**
     * 依赖节点
     */
    private String  relationNodeId;

    /**
     * 动作集合
     */
    private String  actionList;

    /**
     * 用户任务条件跳过表达式
     */
    private String  skipExpression;

    /**
     * 连线表达式
     */
    private String  expression;

    /**
     * 连线来源节点NodeId
     */
    private String  sourceRef;

    /**
     * 连线目标节点NodeId
     */
    private String  targetRef;

    /**
     * 用户任务 多实例属性 parallel 并行审批，sequential 串行审批
     */
    private String  sequential;

    /**
     * 通过比例
     */
    private String  proportion;

    /**
     * 自定义查找人SQL
     */
    private String  customSql;

    /**
     * 自定义拓展数据集合JSON对象格式
     */
    private String  customData;

    /**
     * 备注
     */
    private String  remarks;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer  validState;

    /**
     * 操作人工号
     */
    private Long  operatorId;

    /**
     * 操作人姓名
     */
    private String  operatorName;

    private LocalDateTime  createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime  updateTime;



}
