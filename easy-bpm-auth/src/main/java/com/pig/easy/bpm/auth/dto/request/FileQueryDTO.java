package com.pig.easy.bpm.auth.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class FileQueryDTO extends BaseRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer  fileId;


    /**
     * 文件实例编号
     */
    private String  fileInstanceCode;


    /**
     * 文件名称
     */
    private String  fileName;


    /**
     * 加密后文件名称
     */
    private String  fileMd5Name;


    /**
     * 文件路径
     */
    private String  filePath;


    /**
     * 文件大小
     */
    private BigDecimal  fileSize;


    /**
     * 文件后缀
     */
    private String  fileExtend;


    /**
     * 状态 1： 文件生成中 2：文件生成成功 3：文件生成失败
     */
    private Integer  fileStatus;


    /**
     * 所属系统
     */
    private String  fileSystemCode;


    /**
     * 所属平台
     */
    private String  filePaltform;


    /**
     * 所属服务名称
     */
    private String  fileServiceName;


    /**
     * 所属于哪个方法
     */
    private String  fileMethodName;


    /**
     * 文件所属人员
     */
    private Integer  fileOwnerId;


    /**
     * 文件所属人姓名
     */
    private String  fileOwnerName;


    /**
     * 租户编号
     */
    private String  tenantId;


    /**
     * 错误日志
     */
    private String  errorMessage;


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
