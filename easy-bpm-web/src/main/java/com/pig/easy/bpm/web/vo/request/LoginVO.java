package com.pig.easy.bpm.web.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/15 18:04
 */
@Data
@ToString
@ApiModel("登录")
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 7804580880579635580L;

    @NotNull(message = "用户名不能为空！")
    @NotEmpty(message = "用户名不能为空！")
    @ApiModelProperty("用户名 支持工号，邮箱 等方式登录")
    private String username;

    @NotNull(message = "密码不能为空！")
    @NotEmpty(message = "密码不能为空！")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty("验证码")
    private String code;
}
