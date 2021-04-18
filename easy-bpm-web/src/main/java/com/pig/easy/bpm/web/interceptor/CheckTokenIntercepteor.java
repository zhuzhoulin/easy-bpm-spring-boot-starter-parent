package com.pig.easy.bpm.web.interceptor;

import com.pig.easy.bpm.auth.core.context.GlobalUserInfoContext;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.annotation.Login;
import com.pig.easy.bpm.common.core.context.GlobalVarContext;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/15 10:33
 */
@Component
@Slf4j
public class CheckTokenIntercepteor implements HandlerInterceptor {

   @Autowired
   private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        Login login = method.getAnnotation(Login.class);
        System.out.println("method = " + method);
        if (login == null) {
            Class<?> cls = method.getDeclaringClass();
            login = cls.getAnnotation(Login.class);
        }
        if (login != null && !login.value()) {
            return true;
        } else {
            Long userId = CommonUtils.evalLong(request.getHeader("userId"));
            String accessToken = request.getHeader("accessToken");
            String tenantId = request.getHeader("tenantId");
            String system = request.getHeader("system");
            String platform = request.getHeader("platform");
            try {
                if (StringUtils.isNotEmpty(accessToken)) {
                    accessToken = URLDecoder.decode(accessToken, "UTF-8");
                }
                if (StringUtils.isNotEmpty(tenantId)) {
                    tenantId = URLDecoder.decode(tenantId, "UTF-8");
                }
                if (StringUtils.isNotEmpty(system)) {
                    system = URLDecoder.decode(system, "UTF-8");
                }
                if (StringUtils.isNotEmpty(platform)) {
                    platform = URLDecoder.decode(platform, "UTF-8");
                }
            } catch (UnsupportedEncodingException var6) {
                log.warn("preHandle UnsupportedEncodingException.decoder is e.:" + var6.getMessage());
            } catch (NullPointerException e) {
                throw BpmException.builder().entityError(EntityError.ILLEGAL_ACCESS_ERROR).build();
            }

            if (StringUtils.isNotEmpty(accessToken) && userId > 0) {
                /* 校验 accessToken 是否有效 */
                Result<UserDTO> result = userService.verifyToken(userId, accessToken);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    throw BpmException.builder().entityError(result.getEntityError()).build();
                }
                UserDTO userInfoDTO = result.getData();
                /* 如果header 有传 tenantId,则修改 tenantId,*/
                if(StringUtils.isNotEmpty(tenantId)){
                    userInfoDTO.setTenantId(tenantId);
                }
                if(StringUtils.isNotEmpty(system)){
                    userInfoDTO.setSystem(system);
                }
                if(StringUtils.isNotEmpty(platform)){
                    userInfoDTO.setPlatform(platform);
                }

                Map<String,Object> globalVarMap = Collections.synchronizedMap(new HashMap<>());
                globalVarMap.put("userId",userInfoDTO.getUserId());
                globalVarMap.put("userName",userInfoDTO.getUserName());
                globalVarMap.put("realName",userInfoDTO.getRealName());
                GlobalVarContext.setGlobalVarMap(globalVarMap);
                GlobalUserInfoContext.setLoginInfo(userInfoDTO);
            } else {
                throw BpmException.builder().entityError(EntityError.ILLEGAL_ACCESS_ERROR).build();
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        GlobalUserInfoContext.removeInfo();
        GlobalVarContext.removeGlobalVar();
    }
}
