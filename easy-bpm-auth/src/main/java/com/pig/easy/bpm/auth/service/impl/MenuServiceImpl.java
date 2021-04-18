package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.MenuQueryDTO;
import com.pig.easy.bpm.auth.dto.request.MenuSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.MenuDTO;
import com.pig.easy.bpm.auth.dto.response.MenuTreeDTO;
import com.pig.easy.bpm.auth.entity.MenuDO;
import com.pig.easy.bpm.auth.mapper.MenuMapper;
import com.pig.easy.bpm.auth.service.MenuService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, MenuDO> implements MenuService {

    @Autowired
    MenuMapper mapper;

    @Override
    public Result<PageInfo<MenuDTO>> getListPageByCondition(MenuQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);


        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<MenuDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<MenuDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<MenuDTO>> getListByCondition(MenuQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<MenuDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertMenu(MenuSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        MenuDO temp = BeanUtils.switchToDO(param, MenuDO.class);
        // 默认 key 格式   租户 +"-" + key
        if (!temp.getMenuCode().startsWith((temp.getTenantId()))) {
            temp.setMenuCode(temp.getTenantId()
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + temp.getMenuType()
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + temp.getMenuCode());
        }
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateMenu(MenuSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MenuDO temp = BeanUtils.switchToDO(param, MenuDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteMenu(MenuSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MenuDO temp = BeanUtils.switchToDO(param, MenuDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<MenuDTO> getMenuById(Long menuId){

        if (menuId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        MenuDO query = new MenuDO();
        query.setMenuId(menuId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        MenuDTO result = BeanUtils.switchToDTO(query, MenuDTO.class);
        return Result.responseSuccess(result);
    }

    @Override
    public Result<List<MenuTreeDTO>> getMenuTree(String tenantId, Long parentId) {

        EasyBpmAsset.isAssetEmpty(tenantId);
        if (parentId == null) {
            parentId = 0L;
        }
        List<MenuDO> list = mapper.selectList(new QueryWrapper<>(
                MenuDO.builder().tenantId(tenantId).validState(VALID_STATE).build()).orderByAsc("parent_id","sort"));
        if(list == null){
            list = new ArrayList<>();
        }
        List<MenuTreeDTO> menuList = new ArrayList<>();
        for(MenuDO menuDO : list){
            menuList.add(BeanUtils.switchToDTO(menuDO,MenuTreeDTO.class));
        }

        menuList = makeMenuTree(menuList,parentId);
        return Result.responseSuccess(menuList);
    }

    private List<MenuTreeDTO> makeMenuTree(List<MenuTreeDTO> treeList, Long parentId) {

        List<MenuTreeDTO> tempTreeList = new ArrayList<>();
        for(MenuTreeDTO menuTreeDTO : treeList){
            if(parentId.equals(menuTreeDTO.getParentId())) {
                tempTreeList.add(findChildren(menuTreeDTO, treeList, menuTreeDTO.getMenuId()));
            }
        }

        return tempTreeList;
    }

    private static MenuTreeDTO findChildren(MenuTreeDTO treeDTO,List<MenuTreeDTO> treeList, Long parentId) {

        List<MenuTreeDTO> children = treeList.stream().filter(x -> parentId.equals(x.getParentId())).collect(Collectors.toList());
        children.forEach(x ->
                {
                    if (treeDTO.getChildren() == null) {
                        treeDTO.setChildren(new ArrayList<>());
                    }
                    treeDTO.getChildren().add(findChildren(x,treeList, x.getMenuId()));
                }
        );

        return treeDTO;
    }
}
