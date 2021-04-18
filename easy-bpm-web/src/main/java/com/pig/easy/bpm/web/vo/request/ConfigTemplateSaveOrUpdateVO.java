package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
    * 
    * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConfigTemplateSaveOrUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编号
     */
    private Long  templateId;

    /**
     * 模板编号
     */
    private String  templateCode;

    /**
     * 模板名称
     */
    private String  templateName;

    /**
     * 模板key
     */
    private String  templateKey;

    /**
     * 模板值
     */
    private String  templateValue;

    /**
     * 模板类型
     */
    private String  templateType;

    /**
     * 模板字段状态 1 未发布 2 已发布
     */
    private Integer  templateStatus;

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

    /**
     * 创建时间
     */
    private LocalDateTime  createTime;

    /**
     * 更新时间
     */
    private LocalDateTime  updateTime;




}
