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
public class ConfigSaveOrUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置编码
     */
    private Long  configId;

    /**
     * 模板编号
     */
    private String  configCode;

    /**
     * 模板名称
     */
    private String  configName;

    /**
     * 模板编号
     */
    private Long  templateId;

    /**
     * 模板key
     */
    private String  configKey;

    /**
     * 模板值
     */
    private String  configValue;

    /**
     * 模板类型
     */
    private String  configType;

    /**
     * 租户编号
     */
    private String  tenantId;

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
