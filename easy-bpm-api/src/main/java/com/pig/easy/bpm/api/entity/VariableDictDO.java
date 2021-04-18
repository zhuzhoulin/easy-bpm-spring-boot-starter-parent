package com.pig.easy.bpm.api.entity;

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

/**
 * <p>
 * 变量表
 * </p>
 *
 * @author pig
 * @since 2021-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_variable_dict")
public class VariableDictDO extends Model<VariableDictDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 
     */
    @TableId(value = "`variable_id`", type = IdType.AUTO)
    private Long variableId;

    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Long processId;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 字段英文名称
     */
    @TableField("`data_key`")
    private String dataKey;

    /**
     * 字段中文名称
     */
    @TableField("`data_name`")
    private String dataName;

    /**
     * 数据类型
     */
    @TableField("`data_type`")
    private String dataType;

    /**
     * 校验规则（填写JUEL表达式）
     */
    @TableField("`check_rule`")
    private String checkRule;

    /**
     * 特殊值1
     */
    @TableField("`special_value`")
    private String specialValue;

    /**
     * 特殊值2
     */
    @TableField("`special_value2`")
    private String specialValue2;

    /**
     * 1 流程变量 0 非流程变量
     */
    @TableField("`process_data`")
    private Boolean processData;

    /**
     * 排序值
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 允许编辑节点 默认发起人可以编辑字段
     */
    @TableField("`allow_edit_node_id`")
    private String allowEditNodeId;

    /**
     * 不允许读取字段 默认所有节点都可以读取
     */
    @TableField("`hidden_node_id`")
    private String hiddenNodeId;

    /**
     * 哪些节点当前字段必须传
     */
    @TableField("`required_node_id`")
    private String requiredNodeId;

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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.variableId;
    }

}
