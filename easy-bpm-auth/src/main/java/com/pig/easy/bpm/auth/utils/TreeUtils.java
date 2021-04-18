package com.pig.easy.bpm.auth.utils;

import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.entity.ConfigDO;
import com.pig.easy.bpm.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/26 14:42
 */
public class TreeUtils {

    public static List<TreeDTO> makeTree(List<TreeDTO> treeList, Long parentId) {

        List<TreeDTO> tempList = new ArrayList<>();
        for (TreeDTO treeDTO : treeList) {
            if (parentId.equals(treeDTO.getParentId())) {
                tempList.add(findChildren(treeDTO, treeList, treeDTO.getTreeId()));
            }
        }
        return tempList;
    }

    public static TreeDTO findChildren(TreeDTO treeDTO, List<TreeDTO> treeList, Long parentId) {
        List<TreeDTO> children = treeList.stream().filter(x -> parentId.equals(x.getParentId())).collect(Collectors.toList());
        children.forEach(x ->
                {
                    if (treeDTO.getChildren() == null) {
                        treeDTO.setChildren(new ArrayList<>());
                    }
                    treeDTO.getChildren().add(findChildren(x, treeList, x.getTreeId()));
                }
        );

        return treeDTO;
    }

    public static Object getConfigValueByKey(ConfigDO config) {

        if (config == null) {
            return null;
        }
        Object obj = null;
        switch (config.getConfigType().toLowerCase()) {
            case "string":
                obj = config.getConfigValue();
                break;
            case "int":
            case "integer":
                obj = CommonUtils.evalInt(config.getConfigValue());
                break;
            case "long":
                obj = CommonUtils.evalLong(config.getConfigValue());
                break;
            case "boolean":
                obj = CommonUtils.evalBoolean(config.getConfigValue());
                break;
            case "double":
                obj = CommonUtils.evalDouble(config.getConfigValue());
                break;
            case "float":
                obj = CommonUtils.evalFloat(config.getConfigValue());
                break;
            case "date":
                obj = CommonUtils.evalDate(config.getConfigValue());
                break;
            default:
                obj = config.getConfigValue();
                break;
        }
        return obj;
    }
}
