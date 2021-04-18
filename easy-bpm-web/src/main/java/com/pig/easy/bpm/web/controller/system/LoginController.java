package com.pig.easy.bpm.web.controller.system;

import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.annotation.Login;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/23 12:10
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录管理", value = "登录管理")
public class LoginController extends BaseController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping("/login")
    @Login(false)
    public JsonResult login(@RequestBody @Valid LoginVO loginVO){

        Result<UserDTO> result = userService.login(loginVO.getUsername(), loginVO.getPassword(),loginVO.getCode(),loginVO.getUuid());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "获取用户详情", notes = "获取用户详情",  produces = "application/json")
    @PostMapping("/getUserInfo/{username}")
    public JsonResult getUserInfo(@ApiParam(required = true, name = "用户名称", value = "username", example = "pig") @PathVariable("username") String username) {

        Result<UserDTO> result = userService.getUserInfo(username);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "退出登录", notes = "新增用户",  produces = "application/json")
    @PostMapping("/logout")
    public JsonResult logout() {

        Result<Boolean> result = userService.logout(currentUserInfo().getUserId(),currentUserInfo().getAccessToken());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
