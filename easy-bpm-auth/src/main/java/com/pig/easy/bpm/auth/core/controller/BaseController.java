package com.pig.easy.bpm.auth.core.controller;


import com.pig.easy.bpm.auth.core.context.GlobalUserInfoContext;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.ip.IpUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * todo:
 *
 * @author : pig
 */
@Slf4j
public class BaseController {

    protected UserDTO currentUserInfo() {
        return GlobalUserInfoContext.getLoginInfo();
    }

    /**
     * 转换成 DTO
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T switchToDTO(Object source, Class<T> clazz) {

        T result = BeanUtils.objectToBean(source, clazz);
        BeanUtils.setProperty(result, "currentUserId", currentUserInfo() == null ? null : currentUserInfo().getUserId());
        BeanUtils.setProperty(result, "currentUserName", currentUserInfo() == null ? null : currentUserInfo().getUserName());
        BeanUtils.setProperty(result, "currentRealName", currentUserInfo() == null ? null : currentUserInfo().getRealName());
        BeanUtils.setProperty(result, "accessToken", currentUserInfo() == null ? null : currentUserInfo().getAccessToken());
        BeanUtils.setProperty(result, "currentTenantId", currentUserInfo() == null ? null : currentUserInfo().getTenantId());
        BeanUtils.setProperty(result, "currentSystem", currentUserInfo() == null ? null : currentUserInfo().getSystem());
        BeanUtils.setProperty(result, "currentPlatform", currentUserInfo() == null ? null : currentUserInfo().getPlatform());
        BeanUtils.setProperty(result, "tenantId", currentUserInfo() == null ? null : currentUserInfo().getTenantId());
        BeanUtils.setProperty(result, "system", currentUserInfo() == null ? null : currentUserInfo().getSystem());

        return result;
    }

    protected String getRemoteIP(HttpServletRequest request) {
        return IpUtils.getIpAddr(request);
    }
}
