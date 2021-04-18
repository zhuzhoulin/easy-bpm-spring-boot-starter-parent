package com.pig.easy.bpm.web.vo.request;

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
public class DictQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long  dictId;


    /**
     * 字典编码
     */
    private String  dictCode;

    private Integer dictTree;



    /**
     * 字典名称
     */
    private String  dictName;


    /**
     * 租户编号
     */
    private String  tenantId;


    /**
     * 备注
     */
    private String  remark;


    /**
     * 状态 1 有效 0 失效
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


    private LocalDateTime  updateTime;


    private LocalDateTime  createTime;



    /**
     *  当前页码
     */
    private Integer pageIndex;

    /**
     * 每页展示数量
     */
    private Integer pageSize;

}
