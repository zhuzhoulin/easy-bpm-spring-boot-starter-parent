package com.pig.easy.bpm.common.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pig.easy.bpm.common.annotation.HideLogAspect;
import com.pig.easy.bpm.common.aspect.AspectProperties;
import com.pig.easy.bpm.common.utils.id.SnowKeyGenUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * todo:
 */
@Configuration
@ConditionalOnClass(AspectProperties.class)
@ConditionalOnProperty(prefix = AspectProperties.PREFIX, name = "enable", havingValue = "true")
public class LogAspectAdvisorConfig {

    private static Logger logger = LoggerFactory.getLogger(LogAspectAdvisorConfig.class);

    @Value("${easy.bpm.aspect.pointcut:}")
    private String pointcut;

    private static final String TRACE_ID = "traceId";

    @Bean
    @Primary
    public AspectJExpressionPointcutAdvisor configAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(traceAdvice());
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean(LogAdvice.class)
    public LogAdvice traceAdvice() {
        return new LogAdvice();
    }

    class LogAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            Method method = invocation.getMethod();

            HideLogAspect hideTraceAspect = method.getAnnotation(HideLogAspect.class);
            if (hideTraceAspect == null) {
                Class<?> cls = invocation.getClass();
                hideTraceAspect = cls.getAnnotation(HideLogAspect.class);
            }

            boolean saveLog = true;
            if (hideTraceAspect != null && hideTraceAspect.value()) {
                saveLog = false;
            }

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            /* 作用域 traceId 也可以从 globalVarMap 获取 */
            boolean traceIdExist = false;
            try{
                traceIdExist = Optional.ofNullable(attributes.getAttribute(TRACE_ID, 0)).isPresent();
            }catch (Exception e){

            }
            String traceId = traceIdExist ? attributes.getAttribute(TRACE_ID, 0).toString() : SnowKeyGenUtils.getInstance().getNextId() ;
            if (traceIdExist) {
                attributes.setAttribute(TRACE_ID, traceId, 0);
            }
            MDC.put(TRACE_ID, traceId);
            Object proceed = null;
            long startTime = System.currentTimeMillis();
            String declaringClassName = invocation.getMethod().getDeclaringClass().getName();
            if (logger.isInfoEnabled() && saveLog) {
                logger.info("traceId[{}] start，declaringClassName: {},method：{}，param：{}", traceId, declaringClassName, method.getName(), invocation.getArguments());
            }

            proceed = invocation.proceed();

            if (logger.isInfoEnabled() && saveLog) {
                String result = null;
                try {
                    result = JSONObject.toJSONStringWithDateFormat(proceed, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
                } finally {
                    logger.info("traceId[{}] end,declaringClassName: {}，method：{},spendTime：{}ms，result：{}", traceId, declaringClassName, method.getName(), System.currentTimeMillis() - startTime, result);
                }
            }
            MDC.clear();

            return proceed;
        }
    }

}
