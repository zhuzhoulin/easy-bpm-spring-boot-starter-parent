package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.redis.RedisCache;
import com.pig.easy.bpm.auth.dto.request.UserQueryDTO;
import com.pig.easy.bpm.auth.dto.request.UserSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ListDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDetailDTO;
import com.pig.easy.bpm.auth.entity.UserDO;
import com.pig.easy.bpm.auth.mapper.UserMapper;
import com.pig.easy.bpm.auth.service.CompanyService;
import com.pig.easy.bpm.auth.service.DeptService;
import com.pig.easy.bpm.auth.service.UserRoleService;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.constant.Constants;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.user.CaptchaException;
import com.pig.easy.bpm.common.execption.user.CaptchaExpireException;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.*;
import com.pig.easy.bpm.common.utils.id.SnowKeyGenUtils;
import com.pig.easy.bpm.common.utils.jwt.jwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    UserMapper mapper;

    /**
     * 令牌自定义标识:
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;
    /**
     * 签发人
     */
    @Value("${token.issuer}")
    private String issuer;
    /**
     * 默认用户名密码
     */
    @Value("${token.defaultUserPassword}")
    private String defaultUserPassword;
    /**
     * 过期时间(分钟)
     */
    @Value("${token.expireTime}")
    private Integer expireTime;
    /**
     * 刷新过期时间(分钟)
     */
    @Value("${token.refershExpireTime}")
    private Integer refershExpireTime;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private DeptService deptService;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserRoleService userRoleService;

    @Override
    public Result<PageInfo<UserDTO>> getListPageByCondition(UserQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<UserDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<UserDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<UserDTO>> getListByCondition(UserQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<UserDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<String> generateToken(Long userId) {
        return Result.responseSuccess(jwtUtils.generateToken(secret, expireTime * 60 * 1000, issuer, userId));
    }

    @Override
    public Result<UserDTO> verifyToken(Long userId, String token) {

        EasyBpmAsset.isAssetEmpty(userId);
        EasyBpmAsset.isAssetEmpty(token);
        Result<Long> result = getUserIdByToken(token);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        if (!userId.equals(result.getData()) || !jwtUtils.verify(secret, issuer, token)) {
            return Result.responseError(EntityError.ILLEGAL_ACCESS_ERROR);
        }

        String tokenKey = getTokenKey(token);
        UserDTO userInfoDTO = redisCache.getCacheObject(tokenKey);
        if (userInfoDTO == null) {
            return Result.responseError(EntityError.ILLEGAL_ACCESS_ERROR);
        }
        return Result.responseSuccess(userInfoDTO);
    }

    @Override
    public Result<Long> getUserIdByToken(String token) {
        EasyBpmAsset.isAssetEmpty(token);
        return Result.responseSuccess(jwtUtils.getUserId(token));
    }

    @Override
    public Result<UserDTO> login(String userName, String password, String code, String uuid) {

        EasyBpmAsset.isAssetEmptyByDefault(userName);
        EasyBpmAsset.isAssetEmpty(password);

        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(uuid)) {
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
            String captcha = redisCache.getCacheObject(verifyKey);
            if (StringUtils.isEmpty(captcha)) {
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)) {
                throw new CaptchaException();
            }
            redisCache.deleteObject(verifyKey);
        }

        UserDO user = mapper.login(userName);
        if (user == null) {
            return Result.responseError(EntityError.USERNAME_OR_PASSWORD_ERROR);
        }
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return Result.responseError(EntityError.USERNAME_OR_PASSWORD_ERROR);
        }
        UserDTO userInfoDTO = BeanUtils.switchToDTO(user, UserDTO.class);
        String token = generateToken(user.getUserId()).getData();
        userInfoDTO.setAccessToken(token);

        String tokenKey = getTokenKey(token);
        redisCache.setCacheObject(tokenKey, userInfoDTO, expireTime, TimeUnit.MINUTES);
        redisCache.setCacheObject(Constants.LOGIN_INFO_KEY + userInfoDTO.getUserId(), userInfoDTO, expireTime, TimeUnit.MINUTES);

        return Result.responseSuccess(userInfoDTO);
    }

    @Override
    public Result<UserDTO> getUserInfo(String userName) {

        EasyBpmAsset.isAssetEmptyByDefault(userName);
        UserDTO userInfoDTO = redisCache.getCacheObject(Constants.LOGIN_INFO_KEY + userName);
        if (userInfoDTO == null) {
            UserDO user = mapper.login(userName);
            if (user == null) {
                return Result.responseError(EntityError.USERNAME_OR_PASSWORD_ERROR);
            }
            userInfoDTO = BeanUtils.switchToDTO(user, UserDTO.class);
            setUserInfoValues(userInfoDTO);
            redisCache.setCacheObject(Constants.LOGIN_INFO_KEY + userInfoDTO.getUserId(), userInfoDTO, expireTime, TimeUnit.MINUTES);
        }
        return Result.responseSuccess(userInfoDTO);
    }

    @Override
    public Result<Boolean> logout(Long userId, String accessToken) {

        Result<UserDTO> result = verifyToken(userId, accessToken);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        redisCache.deleteObject(getTokenKey(accessToken));
        redisCache.deleteObject(Constants.LOGIN_INFO_KEY + userId);

        return Result.responseSuccess(true);
    }

    @Override
    public Result<UserDTO> getUserInfoById(Long userId) {

        EasyBpmAsset.isAssetEmptyByDefault(userId);
        UserDTO result = new UserDTO();
        UserDO user = mapper.selectOne(new QueryWrapper<>(UserDO.builder().userId(userId).validState(VALID_STATE).build()));

        if (user == null) {
            return Result.responseError(EntityError.USERNAME_OR_PASSWORD_ERROR);
        }
        result = BeanUtils.switchToDTO(user, UserDTO.class);
        setUserInfoValues(result);
        return Result.responseSuccess(result);
    }

    private void setUserInfoValues(UserDTO userInfoDTO) {

        if (userInfoDTO == null
                || CommonUtils.evalLong(userInfoDTO.getUserId()) < 0) {
            return;
        }

        Result<List<UserRoleDetailDTO>> result = userRoleService.getUserRoleDetailByUserId(userInfoDTO.getUserId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return;
        }
        List<UserRoleDetailDTO> userRoleList = result.getData();
        ListDTO listDTO = new ListDTO();
        // set Company List
        List<ListDTO> companyList = new ArrayList<>();

        Map<Long, UserRoleDetailDTO> companyMap = userRoleList.stream().collect(Collectors.toMap(UserRoleDetailDTO::getCompanyId, a -> a, (oldVal, newVal) -> newVal));
        for (Map.Entry<Long, UserRoleDetailDTO> companyEntry : companyMap.entrySet()) {
            listDTO = new ListDTO();
            listDTO.setId(companyEntry.getKey());
            listDTO.setCode(companyEntry.getValue().getCompanyCode());
            listDTO.setName(companyEntry.getValue().getCompanyName());
            companyList.add(listDTO);
        }
        userInfoDTO.setCompanyList(companyList);
        if (companyMap.get(userInfoDTO.getCompanyId()) != null) {
            userInfoDTO.setCompanyCode(companyMap.get(userInfoDTO.getCompanyId()).getCompanyCode());
            userInfoDTO.setCompanyName(companyMap.get(userInfoDTO.getCompanyId()).getCompanyName());
        }

        // set Dept List
        List<ListDTO> deptList = new ArrayList<>();
        Map<Long, UserRoleDetailDTO> deptMap = userRoleList.stream().collect(Collectors.toMap(UserRoleDetailDTO::getDeptId, a -> a, (oldVal, newVal) -> newVal));
        for (Map.Entry<Long, UserRoleDetailDTO> deptEntry : deptMap.entrySet()) {
            listDTO = new ListDTO();
            listDTO.setId(deptEntry.getKey());
            listDTO.setCode(deptEntry.getValue().getDeptCode());
            listDTO.setName(deptEntry.getValue().getDeptName());
            deptList.add(listDTO);
        }

        userInfoDTO.setDeptList(deptList);
        if (companyMap.get(userInfoDTO.getDeptId()) != null) {
            userInfoDTO.setDeptCode(companyMap.get(userInfoDTO.getDeptId()).getDeptCode());
            userInfoDTO.setDeptName(companyMap.get(userInfoDTO.getCompanyId()).getDeptName());
        }
        // set Role List
        List<ListDTO> roleList = new ArrayList<>();
        Map<Long, UserRoleDetailDTO> roleMap = userRoleList.stream().collect(Collectors.toMap(UserRoleDetailDTO::getRoleId, a -> a, (oldVal, newVal) -> newVal));
        for (Map.Entry<Long, UserRoleDetailDTO> roleEntry : roleMap.entrySet()) {
            listDTO = new ListDTO();
            listDTO.setId(roleEntry.getKey());
            listDTO.setCode(roleEntry.getValue().getRoleCode());
            listDTO.setName(roleEntry.getValue().getRoleName());
            roleList.add(listDTO);
        }
        userInfoDTO.setRoleList(roleList);
    }


    @Override
    public Result<List<TreeDTO>> getOrganUserTree(String tenantId, Long parentId) {
        EasyBpmAsset.isEmpty(tenantId);
        EasyBpmAsset.isEmpty(parentId);

        /* 人只能挂在部门下 */
        Result<List<TreeDTO>> result = deptService.getOrganTree(tenantId, parentId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        List<TreeDTO> organUserTreeList = new CopyOnWriteArrayList<>();

        for (TreeDTO treeDTO : result.getData()) {
            organUserTreeList.add(makeOrganTree(treeDTO,tenantId,treeDTO.getTreeId(),3));
        }

        return Result.responseSuccess(organUserTreeList);
    }

    private TreeDTO makeOrganTree(TreeDTO tree, String tenantId, Long parentId, Integer treeType) {

        List<TreeDTO> tempList = new ArrayList<>();
        if (tree.getChildren() == null) {
            tree.setChildren(new ArrayList<>());
        }

        if(tree.getTreeType().equals(2)){
            UserQueryDTO userQueryDTO = new UserQueryDTO();
            userQueryDTO.setPageIndex(DEFAULT_PAGE_INDEX);
            userQueryDTO.setPageSize(MAX_PAGE_SIZE);
            userQueryDTO.setTenantId(tenantId);
            userQueryDTO.setDeptId(parentId);
            Result<List<UserDTO>> result = getListByCondition(userQueryDTO);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                log.error("makeOrganTree error,message :{}", result.getEntityError());
                return tree;
            }

            TreeDTO treeDTO = new TreeDTO();
            for(UserDTO userInfoDTO: result.getData()){
                treeDTO = new TreeDTO();
                treeDTO.setId(SnowKeyGenUtils.getInstance().getNextId());
                treeDTO.setTreeId(userInfoDTO.getUserId());
                treeDTO.setTreeCode(userInfoDTO.getUserName());
                treeDTO.setTreeName(userInfoDTO.getRealName());
                treeDTO.setTempTreeId(userInfoDTO.getCompanyId());
                treeDTO.setParentId(userInfoDTO.getDeptId());
                treeDTO.setTreeTypeCode(BpmConstant.TREE_TYPE_CODE_USER);
                treeDTO.setTreeType(BpmConstant.TREE_TYPE_USER);
                tempList.add(treeDTO);
            }
        }
        List<TreeDTO> children = tree.getChildren();

        for (TreeDTO temp : children) {
            // if (temp.getTreeType().equals(2)) {
            tempList.add(makeOrganTree(temp, tenantId, temp.getTreeId(), treeType));
            //  }
        }
        tree.setChildren(tempList);
        return tree;
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    @Override
    public Result<Integer> insertUser(UserSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        EasyBpmAsset.isEmpty(param.getUserName(), "用户账户不能为空！");
        EasyBpmAsset.isEmpty(param.getRealName(), "用户名称不能为空！");
        EasyBpmAsset.isEmpty(param.getPassword(), "密码不能为空！");

        UserDO temp = BeanUtils.switchToDO(param, UserDO.class);
        /* 使用BCryptPasswordEncoder 加密 */
        temp.setPassword(new BCryptPasswordEncoder().encode(org.apache.commons.lang3.StringUtils.isEmpty(temp.getPassword()) ? defaultUserPassword : temp.getPassword()));
        if (CommonUtils.evalInt(temp.getUserId()) < 0) {
            temp.setUserId(mapper.getUserId());
        }
        temp.setId(SnowKeyGenUtils.getInstance().getNextId());

        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateUser(UserSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        EasyBpmAsset.isAssetEmptyByDefault(param.getId());
        /* 不允许更新密码 */
        param.setPassword(null);
        UserDO temp = BeanUtils.switchToDO(param, UserDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteUser(UserSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        UserDO temp = BeanUtils.switchToDO(param, UserDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<UserDTO> getUserById(String id) {

        if (id == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        UserDO query = new UserDO();
        query.setId(id);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        UserDTO result = BeanUtils.switchToDTO(query, UserDTO.class);
        return Result.responseSuccess(result);
    }
}
