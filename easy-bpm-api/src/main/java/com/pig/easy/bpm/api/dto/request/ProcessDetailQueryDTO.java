package com.pig.easy.bpm.api.dto.request;

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
    * 
    * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class ProcessDetailQueryDTO extends BaseRequestDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 流程详细编号
     */
    private Long  processDetailId;


    /**
     * 租户编号
     */
    private String  tenantId;


    private Long  processId;


    /**
     * 流程XML格式数据
     */
    private String  processXml;


    /**
     * 默认版本号
     */
    private String  definitionId;


    /**
     * 申请标题规则
     */
    private String  applyTitleRule;


    /**
     * 流程到期时间
     */
    private LocalDateTime  applyDueDate;


    /**
     * 是否自动完成第一个节点任务 1 是 0 否
     */
    private Integer  autoCompleteFirstNode;


    /**
     * 1 未发布 2 已发布 
     */
    private Integer  publishStatus;


    /**
     * 1 默认版本 2 非默认版本
     */
    private Integer  mainVersion;


    /**
     * 备注
     */
    private String  remarks;


    /**
     * 状态 1 有效 0 失效
     */
    private Integer  validState;


    private Long  operatorId;


    /**
     * 操作人姓名
     */
    private String  operatorName;


    private LocalDateTime  createTime;


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
