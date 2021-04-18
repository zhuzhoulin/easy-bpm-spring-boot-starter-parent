package com.pig.easy.bpm.auth.entity;

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
@TableName("bpm_file")
public class FileDO extends Model<FileDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "`file_id`", type = IdType.AUTO)
    private Integer fileId;

    /**
     * 文件实例编号
     */
    @TableField("`file_instance_code`")
    private String fileInstanceCode;

    /**
     * 文件名称
     */
    @TableField("`file_name`")
    private String fileName;

    /**
     * 加密后文件名称
     */
    @TableField("`file_md5_name`")
    private String fileMd5Name;

    /**
     * 文件路径
     */
    @TableField("`file_path`")
    private String filePath;

    /**
     * 文件大小
     */
    @TableField("`file_size`")
    private Integer fileSize;

    /**
     * 文件后缀
     */
    @TableField("`file_extend`")
    private String fileExtend;

    /**
     * 状态 1： 文件生成中 2：文件生成成功 3：文件生成失败
     */
    @TableField("`file_status`")
    private Integer fileStatus;

    /**
     * 所属系统
     */
    @TableField("`file_system_code`")
    private String fileSystemCode;

    /**
     * 所属平台
     */
    @TableField("`file_paltform`")
    private String filePaltform;

    /**
     * 所属服务名称
     */
    @TableField("`file_service_name`")
    private String fileServiceName;

    /**
     * 所属于哪个方法
     */
    @TableField("`file_method_name`")
    private String fileMethodName;

    /**
     * 文件所属人员
     */
    @TableField("`file_owner_id`")
    private Integer fileOwnerId;

    /**
     * 文件所属人姓名
     */
    @TableField("`file_owner_name`")
    private String fileOwnerName;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 错误日志
     */
    @TableField("`error_message`")
    private String errorMessage;

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
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.fileId;
    }

}
