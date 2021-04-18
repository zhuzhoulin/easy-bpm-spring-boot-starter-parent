package com.pig.easy.bpm.auth.dto.response;

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
import com.alibaba.excel.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.math.*;
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
public class FileExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty( value = "编号")
    private Integer  fileId;

    /**
     * 文件实例编号
     */
    @ExcelProperty( value = "文件实例编号")
    private String  fileInstanceCode;

    /**
     * 文件名称
     */
    @ExcelProperty( value = "模板文件名称")
    private String  fileName;

    /**
     * 加密后文件名称
     */
    @ExcelProperty( value = "模板文件名称")
    private String  fileMd5Name;

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
     * 文件后缀
     */
    @ExcelProperty( value = "文件后缀")
    private String  fileExtend;

    /**
     * 状态 1： 文件生成中 2：文件生成成功 3：文件生成失败
     */
    @ExcelProperty( value = "状态")
    private Integer  fileStatus;

    /**
     * 所属系统
     */
    @ExcelProperty( value = "所属系统")
    private String  fileSystemCode;

    /**
     * 所属平台
     */
    @ExcelProperty( value = "所属平台")
    private String  filePaltform;

    /**
     * 所属服务名称
     */
    @ExcelProperty( value = "所属服务名称")
    private String  fileServiceName;

    /**
     * 所属于哪个方法
     */
    @ExcelProperty( value = "所属于哪个方法")
    private String  fileMethodName;

    /**
     * 文件所属人员
     */
    @ExcelProperty( value = "文件所属人员")
    private Integer  fileOwnerId;

    /**
     * 文件所属人姓名
     */
    @ExcelProperty( value = "文件所属人姓名")
    private String  fileOwnerName;

    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 错误日志
     */
    @ExcelProperty( value = "错误日志")
    private String  errorMessage;

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
     * 更新时间
     */
    @ExcelProperty( value = "最后一次更新时间")
    private LocalDateTime  updateTime;
}
