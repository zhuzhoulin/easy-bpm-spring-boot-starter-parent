package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/5 16:03
 */
@Data
@ToString
public class DiagramNodeDTO implements Serializable {

    private static final long serialVersionUID = 2948541452635157454L;

//    fill：元素的填充色
//    stroke：元素的边框颜色
//    strokenWidth：元素边框的宽度

    private boolean approved;

    private boolean current;

    private boolean unDo;

    private String fill;

    private String stroke;

    private String strokenWidth;

    private String nodeId;

    private String nodeName;
}
