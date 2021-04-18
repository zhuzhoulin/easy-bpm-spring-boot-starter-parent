package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
    * SQL日志表
    * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SqlLogSaveOrUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long  logId;

    /**
     * 64
     */
    private String  logType;

    /**
     * sql
     */
    private String  logSql;

    /**
     * 花费时间（ms）
     */
    private Long  spendTime;

    /**
     * 备注
     */
    private String  remarks;

    /**
     * 有效状态；0表示无效，1表示有效
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
