package com.pig.easy.bpm.common.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * todo: 全局变量传递
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/22 11:24
 */
public class GlobalVarContext {

    private static final TransmittableThreadLocal<Map<String, Object>> threadLocal = new TransmittableThreadLocal<>();

    public GlobalVarContext() {
    }

    public static Map<String, Object> getGlobalVarMap() {
        return threadLocal.get();
    }

    public static void setGlobalVarMap(Map<String, Object> map) {
        threadLocal.set(map);
    }

    public static void removeGlobalVar() {
        threadLocal.remove();
    }
}
