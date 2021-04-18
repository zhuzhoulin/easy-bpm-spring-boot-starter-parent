package com.pig.easy.bpm.web.handler;


import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BaseException;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/14 16:10
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public JsonResult BaseException(HttpServletRequest request, BaseException baseException){
        log.error("GlobalExceptionHandler BaseException : ", baseException);
        return JsonResult.error(new EntityError(EntityError.SYSTEM_ERROR.getCode(),baseException.getMessage()));
    }
    /**
     * 功能描述: BPM 异常拦截器
     */
    @ExceptionHandler(BpmException.class)
    public JsonResult bpmException(HttpServletRequest request, BpmException baseException){
        log.error("GlobalExceptionHandler bpmException : ", baseException);
        return JsonResult.error((baseException.getEntityError()));
    }

    /**
     * 参数校验异常
     * @param request
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException methodArgumentNotValidException){
        log.error("GlobalExceptionHandler methodArgumentN otValidExceptionHandler :{}", methodArgumentNotValidException);
        try {
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuffer errMsg = new StringBuffer();
            fieldErrors.forEach(fieldError -> errMsg.append(fieldError.getDefaultMessage()).append(";  ") );
            return JsonResult.error(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), errMsg.toString()));
        } catch (Exception e){
            e.printStackTrace();
            return JsonResult.error(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

    }

    /**
     * 功能描述: 404
     *
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public JsonResult noHandlerFoundExceptionHandler(HttpServletRequest request, NoHandlerFoundException noHandlerFoundException){
        log.error("GlobalExceptionHandler noHandlerFoundExceptionHandler : ", noHandlerFoundException);
        return JsonResult.error(EntityError.ILLEGAL_PATH_ERROR);
    }


    @ExceptionHandler(BindException.class)
    public JsonResult bindExceptionHandler(HttpServletRequest request, BindException exception) throws Exception {
        log.error("globalExceptionHandler handler : ", exception);
        if (StringUtils.isNotEmpty(exception.getMessage())){
            return JsonResult.error(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), exception.getMessage()));
        }
        return JsonResult.error(EntityError.ILLEGAL_ARGUMENT_ERROR);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public JsonResult illegalArgumentExceptionHandler(HttpServletRequest request, IllegalArgumentException exception) throws Exception {
        log.error("globalExceptionHandler handler : ", exception);
        if (StringUtils.isNotEmpty(exception.getMessage())){
            return JsonResult.error(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), exception.getMessage()));
        }
        return JsonResult.error(EntityError.ILLEGAL_ARGUMENT_ERROR);
    }

    /**
     * 其它异常拦截器
     */
    @ExceptionHandler(Exception.class)
    public JsonResult exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {

        if(exception instanceof BpmException){
            System.out.println("request = " + request);
           return JsonResult.error(((BpmException)exception).getEntityError());
        }

        log.error("globalExceptionHandler handler : ", exception);
        return new JsonResult(EntityError.SYSTEM_ERROR.getCode(), EntityError.SYSTEM_ERROR.getMessage(),new Date(), errorTrackSpace(exception),true);
    }

    private static Map errorTrackSpace(Exception e) {
        Map<String,Object> errData = new HashMap<>();
        errData.put("exceptionType", e.getClass().getName());
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> exceptionContent = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            exceptionContent.add(stackTraceElement.toString());
        }
        errData.put("exceptionContent",exceptionContent);
        return errData;
    }
}
