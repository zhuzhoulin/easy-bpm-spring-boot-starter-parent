package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.MenuQueryDTO;
import com.pig.easy.bpm.auth.dto.request.MenuSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.MenuDTO;
import com.pig.easy.bpm.auth.dto.response.MenuTreeDTO;
import com.pig.easy.bpm.auth.entity.MenuDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
public interface MenuService extends BaseService<MenuDO> {

    Result<PageInfo<MenuDTO>> getListPageByCondition(MenuQueryDTO param);

    Result<Integer> insertMenu(MenuSaveOrUpdateDTO param);

    Result<Integer> updateMenu(MenuSaveOrUpdateDTO param);

    Result<Integer> deleteMenu(MenuSaveOrUpdateDTO param);

    Result<MenuDTO> getMenuById(Long menuId);

    Result<List<MenuTreeDTO>> getMenuTree(String tenantId, Long parentId);

    Result<List<MenuDTO>> getListByCondition(MenuQueryDTO param);
}
