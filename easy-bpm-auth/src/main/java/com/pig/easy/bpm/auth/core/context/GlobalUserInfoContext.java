package com.pig.easy.bpm.auth.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.pig.easy.bpm.auth.dto.response.UserDTO;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/15 10:20
 */
public class GlobalUserInfoContext {

    private static final TransmittableThreadLocal<UserDTO> loginInfoThreadLocal = new TransmittableThreadLocal<>();

    public GlobalUserInfoContext() {
    }

    public static UserDTO getLoginInfo(){
        return loginInfoThreadLocal.get();
    }

    public static void setLoginInfo(UserDTO userInfoDTO){
        loginInfoThreadLocal.set(userInfoDTO);
    }

    public static void removeInfo() {
        loginInfoThreadLocal.remove();
    }
}
