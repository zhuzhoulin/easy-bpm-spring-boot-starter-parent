package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
    * 模板文件表
    * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileTemplateSaveOrUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    private String  tempalteId;

    /**
     * 流程编号
     */
    private Integer  processId;

    /**
     * 租户编号
     */
    private String  tenantId;

    /**
     * 模板文件名称
     */
    private String  fileName;

    /**
     * 模板文件名称
     */
    private String  fileMd5Name;

    /**
     * 文件后缀
     */
    private String  fileExtend;

    /**
     * 文件路径
     */
    private String  filePath;

    /**
     * 文件大小
     */
    private BigDecimal  fileSize;

    /**
     * 备注
     */
    private String  remarks;

    /**
     * 排序值
     */
    private Integer  sort;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer  validState;

    /**
     * 操作人工号
     */
    private Integer  operatorId;

    /**
     * 操作人姓名
     */
    private String  operatorName;

    /**
     * 创建时间
     */
    private LocalDateTime  createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime  updateTime;




}
