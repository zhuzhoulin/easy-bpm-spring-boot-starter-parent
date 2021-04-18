package com.pig.easy.bpm.auth.dto.response;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
    * 字典表
    * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty( value = "主键")
    private Long  dictId;

    /**
     * 字典编码
     */
    @ExcelProperty( value = "字典编码")
    private String  dictCode;

    /**
     * 字典名称
     */
    @ExcelProperty( value = "字典名称")
    private String  dictName;

    /**
     * 是否为树
     */
    @ExcelProperty( value = "树化 1 是 0 否")
    private Integer  dictTree;


    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注")
    private String  remark;

    /**
     * 状态 1 有效 0 失效
     */
    @ExcelProperty( value = "状态")
    private Integer  validState;

    /**
     * 操作人工号
     */
    @ExcelProperty( value = "操作人工号")
    private Long  operatorId;

    /**
     * 操作人姓名
     */
    @ExcelProperty( value = "操作人姓名")
    private String  operatorName;

    @ExcelProperty( value = "")
    private LocalDateTime  updateTime;

    @ExcelProperty( value = "")
    private LocalDateTime  createTime;
}
