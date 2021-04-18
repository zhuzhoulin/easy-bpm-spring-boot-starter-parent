package com.pig.easy.bpm.auth.dto.request;

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
import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import java.io.Serializable;
import java.util.Date;
import java.math.*;
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

public class SqlLogQueryDTO extends BaseRequestDTO implements Serializable {


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



    /**
     *  当前页码
     */
    private Integer pageIndex;

    /**
     * 每页展示数量
     */
    private Integer pageSize;

}
