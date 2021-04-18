package com.pig.easy.bpm.auth.dto.response;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.alibaba.excel.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.math.*;
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
public class FileTemplateExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @ExcelProperty( value = "GUID主键")
    private String  tempalteId;

    /**
     * 流程编号
     */
    @ExcelProperty( value = "流程编号")
    private Integer  processId;

    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 模板文件名称
     */
    @ExcelProperty( value = "模板文件名称")
    private String  fileName;

    /**
     * 模板文件名称
     */
    @ExcelProperty( value = "模板文件名称")
    private String  fileMd5Name;

    /**
     * 文件后缀
     */
    @ExcelProperty( value = "文件后缀")
    private String  fileExtend;

    /**
     * 文件路径
     */
    @ExcelProperty( value = "文件路径")
    private String  filePath;

    /**
     * 文件大小
     */
    @ExcelProperty( value = "文件大小")
    private BigDecimal  fileSize;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注")
    private String  remarks;

    /**
     * 排序值
     */
    @ExcelProperty( value = "排序值")
    private Integer  sort;

    /**
     * 状态 1 有效 0 失效
     */
    @ExcelProperty( value = "状态")
    private Integer  validState;

    /**
     * 操作人工号
     */
    @ExcelProperty( value = "操作人工号")
    private Integer  operatorId;

    /**
     * 操作人姓名
     */
    @ExcelProperty( value = "操作人姓名")
    private String  operatorName;

    /**
     * 创建时间
     */
    @ExcelProperty( value = "创建时间")
    private LocalDateTime  createTime;

    /**
     * 最后一次更新时间
     */
    @ExcelProperty( value = "最后一次更新时间")
    private LocalDateTime  updateTime;
}
