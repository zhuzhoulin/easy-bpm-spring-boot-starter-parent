package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.UserQueryDTO;
import com.pig.easy.bpm.auth.dto.request.UserSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.entity.UserDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface UserService extends BaseService<UserDO> {

    Result<PageInfo<UserDTO>> getListPageByCondition(UserQueryDTO param);

    Result<Integer> insertUser(UserSaveOrUpdateDTO param);

    Result<Integer> updateUser(UserSaveOrUpdateDTO param);

    Result<Integer> deleteUser(UserSaveOrUpdateDTO param);

    Result<UserDTO> getUserById(String id);

    Result<List<UserDTO>> getListByCondition(UserQueryDTO param);

    /**
     * 功能描述: 根据员工编号生成 token ，如果 公司定制，可以重写该方法
     *
     *
     * @param userId 员工编号
     * @return : com.pig.easy.bpm.utils.Result<java.lang.String>
     * @author : pig
     * @date : 2020/5/15 11:40
     */
    Result<String> generateToken(Long userId);

    /**
     * 功能描述:  验证token 是否有效
     *
     * @param userId
     * @param token
     * @return : com.pig.easy.bpm.utils.Result<java.lang.Boolean>
     * @author : pig
     * @date : 2020/5/15 11:45
     */
    Result<UserDTO> verifyToken(Long userId, String token);

    /**
     * 功能描述: 从token获取员工编号
     *
     *
     * @param token
     * @return : com.pig.easy.bpm.utils.Result<java.lang.Integer>
     * @author : pig
     * @date : 2020/5/15 11:45
     */
    Result<Long> getUserIdByToken(String token);

    /**
     * 功能描述:  根据员工编号 邮箱 用户名 登录
     *
     *
     * @param userName
     * @param password
     * @param code
     * @param uuid
     * @return : com.pig.easy.bpm.utils.Result<com.pig.easy.bpm.dto.response.UserDTO>
     * @author : pig
     * @date : 2020/5/19 10:48
     */
    Result<UserDTO> login(String userName, String password,String code,String uuid);

    Result<UserDTO> getUserInfo(String userName);

    Result<Boolean> logout(Long userId, String accessToken);

    Result<UserDTO> getUserInfoById(Long userId);

    Result<List<TreeDTO>> getOrganUserTree(String tenantId, Long parentId);
}
