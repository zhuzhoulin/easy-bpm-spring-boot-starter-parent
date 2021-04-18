package com.pig.easy.bpm.api.dto.response;

import com.pig.easy.bpm.auth.dto.response.TreeItemDTO;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/28 11:08
 */
@Data
@ToString
public class DynamicFormDataDTO implements Serializable {

    private static final long serialVersionUID = 6446626824600341333L;

    private String formKey;

    private Long processId;

    private String processKey;

    private String processName;

    private String formJsonData;

    private Map<String,List<TreeItemDTO>> dynamicDataMap;
}
