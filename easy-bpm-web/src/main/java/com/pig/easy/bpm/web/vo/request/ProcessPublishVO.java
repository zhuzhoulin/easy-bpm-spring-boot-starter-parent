package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/18 14:24
 */
@Data
@ToString
public class ProcessPublishVO implements Serializable {

    private static final long serialVersionUID = 3964415376707120587L;

    private String processKey;

    private String tenantId;

    private Long processDetailId;

    private String processXml;

}
