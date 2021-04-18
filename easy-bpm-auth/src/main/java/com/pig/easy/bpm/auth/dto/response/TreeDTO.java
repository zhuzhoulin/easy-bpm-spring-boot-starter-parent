package com.pig.easy.bpm.auth.dto.response;

import com.pig.easy.bpm.common.generator.dto.response.BaseResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/27 16:16
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class TreeDTO extends BaseResponseDTO {

    private static final long serialVersionUID = -4921659616060082663L;

    private String id;

    private Long treeId;

    private String treeCode;

    private String treeName;

    private Integer treeType;

    private String treeTypeCode;

    private Long parentId;

    private Long tempTreeId;

    /** 用于禁用项目选择。 */
    private Boolean disabled;

    /** 用于为新节点赋予不同的颜色。 */
    private Boolean newFlag;

    /** 默认情况下是否应扩展此文件夹选项。 */
    private Boolean defaultExpanded;

    private List<TreeDTO> children;

}
