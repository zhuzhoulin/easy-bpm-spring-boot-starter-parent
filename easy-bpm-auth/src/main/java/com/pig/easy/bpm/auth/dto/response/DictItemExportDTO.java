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
    * 字典详细表
    * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictItemExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty( value = "主键ID")
    private Long  itemId;

    /**
     * 字典编号
     */
    @ExcelProperty( value = "字典编号")
    private Long  dictId;

    /**
     * 字典项值
     */
    @ExcelProperty( value = "字典项值")
    private String  itemValue;

    /**
     * 字典项文本
     */
    @ExcelProperty( value = "字典项文本")
    private String  itemText;

    /**
     * 父级编号
     */
    @ExcelProperty( value = "父级编号")
    private Long  parentId;

    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 排序
     */
    @ExcelProperty( value = "排序")
    private Integer  sort;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注")
    private String  remark;

    /**
     * 有效状态；0表示无效，1表示有效
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

    /**
     * 更新时间
     */
    @ExcelProperty( value = "更新时间")
    private LocalDateTime  updateTime;

    /**
     * 创建时间
     */
    @ExcelProperty( value = "创建时间")
    private LocalDateTime  createTime;
}
