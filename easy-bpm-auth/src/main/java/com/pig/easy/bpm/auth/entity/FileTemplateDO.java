package com.pig.easy.bpm.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_file_template")
public class FileTemplateDO extends Model<FileTemplateDO> {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    private String tempalteId;

    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Integer processId;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 模板文件名称
     */
    @TableField("`file_name`")
    private String fileName;

    /**
     * 模板文件名称
     */
    @TableField("`file_md5_name`")
    private String fileMd5Name;

    /**
     * 文件后缀
     */
    @TableField("`file_extend`")
    private String fileExtend;

    /**
     * 文件路径
     */
    @TableField("`file_path`")
    private String filePath;

    /**
     * 文件大小
     */
    @TableField("`file_size`")
    private BigDecimal fileSize;

    /**
     * 备注
     */
    @TableField("`remarks`")
    private String remarks;

    /**
     * 排序值
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 状态 1 有效 0 失效
     */
    @TableField("`valid_state`")
    private Integer validState;

    /**
     * 操作人工号
     */
    @TableField("`operator_id`")
    private Integer operatorId;

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
     * 最后一次更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.tempalteId;
    }

}
