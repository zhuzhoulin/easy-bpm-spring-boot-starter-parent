/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.pig.easy.bpm.web.utils;


import com.pig.easy.bpm.common.utils.IpUtils;
import com.sun.management.OperatingSystemMXBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.*;

/**
 * Its own configuration information manipulation tool class.
 *
 */
public class EnvUtil {

    private static String localAddress = "";
    
    private static int port = -1;

    private static String contextPath = null;

    public static final String BPM_HOME_KEY = "bpm.home";

    private static String confPath = "";

    private static String bpmHomePath = null;
    
    private static final OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN = (OperatingSystemMXBean) ManagementFactory
            .getOperatingSystemMXBean();
    
    private static ConfigurableEnvironment environment;
    
    public static ConfigurableEnvironment getEnvironment() {
        return environment;
    }
    
    public static void setEnvironment(ConfigurableEnvironment environment) {
        EnvUtil.environment = environment;
    }
    
    public static boolean containsProperty(String key) {
        return environment.containsProperty(key);
    }
    
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }
    
    public static <T> T getProperty(String key, Class<T> targetType) {
        return environment.getProperty(key, targetType);
    }
    
    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return environment.getProperty(key, targetType, defaultValue);
    }
    
    public static String getRequiredProperty(String key) throws IllegalStateException {
        return environment.getRequiredProperty(key);
    }
    
    public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return environment.getRequiredProperty(key, targetType);
    }
    
    public static String resolvePlaceholders(String text) {
        return environment.resolvePlaceholders(text);
    }
    
    public static String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        return environment.resolveRequiredPlaceholders(text);
    }
    
    public static List<String> getPropertyList(String key) {
        List<String> valueList = new ArrayList<>();
        
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String value = environment.getProperty(key + "[" + i + "]");
            if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
                break;
            }
            
            valueList.add(value);
        }
        
        return valueList;
    }
    
    public static String getLocalAddress() {
        if (StringUtils.isBlank(localAddress)) {
            localAddress = IpUtils.getHostIp() + ":" + getPort();
        }
        return localAddress;
    }
    
    public static void setLocalAddress(String localAddress) {
        EnvUtil.localAddress = localAddress;
    }
    
    public static int getPort() {
        if (port == -1) {
            port = getProperty("server.port", Integer.class, 9993);
        }
        return port;
    }
    
    public static void setPort(int port) {
        EnvUtil.port = port;
    }
    
    public static String getContextPath() {
        if (Objects.isNull(contextPath)) {
            contextPath = getProperty( "server.servlet.context-path", "/api/bpm");
            if ("/".equals(contextPath)) {
                contextPath = StringUtils.EMPTY;
            }
        }
        return contextPath;
    }
    
    public static void setContextPath(String contextPath) {
        EnvUtil.contextPath = contextPath;
    }

    public static String getBpmHome() {
        if (StringUtils.isBlank(bpmHomePath)) {
            String bpmHome = System.getProperty(BPM_HOME_KEY);
            if (StringUtils.isBlank(bpmHome)) {
                bpmHome = Paths.get(System.getProperty("user.home"), "bpm").toString();
            }
            return bpmHome;
        }
        return bpmHomePath;
    }
    
    public static void setBpmHomePath(String bpmHomePath) {
        EnvUtil.bpmHomePath = bpmHomePath;
    }
    
    public static List<String> getIPsBySystemEnv(String key) {
        String env = getSystemEnv(key);
        List<String> ips = new ArrayList<>();
        if (StringUtils.isNotEmpty(env)) {
            ips = Arrays.asList(env.split(","));
        }
        return ips;
    }
    
    public static String getSystemEnv(String key) {
        return System.getenv(key);
    }
    
    public static float getLoad() {
        return (float) OPERATING_SYSTEM_MX_BEAN.getSystemLoadAverage();
    }
    
    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    public static float getCPU() {
        return (float) OPERATING_SYSTEM_MX_BEAN.getSystemCpuLoad();
    }
    
    public static float getMem() {
        return (float) (1
                - (double) OPERATING_SYSTEM_MX_BEAN.getFreePhysicalMemorySize() / (double) OPERATING_SYSTEM_MX_BEAN
                .getTotalPhysicalMemorySize());
    }

    public static Resource getDefaultApplicatioResource() {
        return getDefaultResource();
    }
    
    private static Resource getRelativePathResource(String parentPath, String path) {
        try {
            InputStream inputStream = new FileInputStream(Paths.get(parentPath, path).toFile());
            return new InputStreamResource(inputStream);
        } catch (Exception ignore) {
        }
        return null;
    }
    
    private static Resource getDefaultResource() {

        InputStream inputStream = EnvUtil.class.getClassLoader().getResourceAsStream("META-INF/bpm-default.properties");
        System.setProperty("bpm.ip", IpUtils.getHostIp());
        Properties props = new Properties();
        try {
            props.load(inputStream);
            for (Map.Entry<Object,Object> entry : props.entrySet()) {
                System.setProperty(entry.getKey() + "",entry.getValue() + "");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InputStreamResource(inputStream);
    }
    
}
