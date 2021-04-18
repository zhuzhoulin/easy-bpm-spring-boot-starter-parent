package com.pig.easy.bpm.generator.controller;

import com.pig.easy.bpm.common.utils.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/23 12:10
 */
@RestController
@RequestMapping("/vue-admin-template/user")
public class CodeLoginController {

    @RequestMapping("/login")
    public JsonResult login(){
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("token","admin");
        return JsonResult.success(responseData);
    }

    @RequestMapping("/info")
    public JsonResult info(){
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("roles","[admin]");
        responseData.put("name","admin");
        responseData.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return JsonResult.success(responseData);
    }

    @RequestMapping("/logout")
    public JsonResult logout(){
        return JsonResult.success();
    }
}
