package com.pig.easy.bpm.common.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/16 15:31
 */
@Slf4j
public class BeanUtils {


    /**
     * 转换成 DTO
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T switchToDTO(Object source, Class<T> clazz) {
       return objectToBean(source, clazz);
    }

    /**
     * 将 DTO 转换成 DO
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T switchToDO(Object source, Class<T> clazz) {
        T result = objectToBean(source, clazz);
        setProperty(result, "updateTime", CommonUtils.formatTime(LocalDateTime.now(),CommonUtils.yyyy_MM_ddHHmmss));
        return result;
    }

    /**
     * 设置属性值
     *
     * @param bean
     * @param name
     * @param value
     */
    public static void setProperty(Object bean, String name, Object value) {
        if ((bean != null) && (name != null) && (value != null)) {
            Class clazz = bean.getClass();
            Method[] methods = clazz.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    if ((method.getName().startsWith("set"))
                            && (name.toLowerCase().replaceAll("_", "").equals(method.getName().substring(3).toLowerCase().replaceAll("_", "")))) {
                        Class[] types = method.getParameterTypes();
                        if ((types != null) && (types.length > 0)) {
                            Object val = convertValue(types[0], String.valueOf(value));
                            try {
                                method.invoke(bean, new Object[] {val});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private static Object convertValue(Class clazz, String value) {
        Object rs = null;
        if ((clazz != null) && (value != null)) {
            if (String.class.equals(clazz)) {
                rs = value;
            } else if ((Integer.class.equals(clazz)) || (Integer.TYPE.equals(clazz))) {
                rs = getInteger(value);
            } else if ((Short.class.equals(clazz)) || (Short.TYPE.equals(clazz))) {
                rs = getShort(value);
            } else if ((Long.class.equals(clazz)) || (Long.TYPE.equals(clazz))) {
                rs = getLong(value);
            } else if ((Double.class.equals(clazz)) || (Double.TYPE.equals(clazz))) {
                rs = getDouble(value);
            } else if ((Float.class.equals(clazz)) || (Float.TYPE.equals(clazz))) {
                rs = getFloat(value);
            } else if (BigDecimal.class.equals(clazz)) {
                rs = getBigDecimal(value);
            } else if ((Byte.class.equals(clazz)) || (Byte.TYPE.equals(clazz))) {
                rs = getByte(value);
            }else if(LocalDateTime.class.equals(clazz)){
               rs = CommonUtils.parseLocalDateyyyy_MM_ddHHmmss(value);
            } else if (Date.class.equals(clazz)
                    || clazz.getSimpleName().toLowerCase().contains("date")) {
                if (value.contains(".")) {
                    rs = getDate(value, "yyyy-MM-dd HH:mm:ss.SSS");
                } else if (value.contains(":")) {
                    rs = getDate(value, "yyyy-MM-dd HH:mm:ss");
                } else if (value.contains("-")) {
                    rs = getDate(value, "yyyy-MM-dd");
                } else {
                    rs = getDate(value, "yyyyMMddHHmmss");
                }
            }
        }

        return rs;
    }

    private static Long getLong(String value) {
        Long rs = null;
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
                rs = Long.valueOf(value);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static Integer getInteger(String value) {
        Integer rs = null;
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
                rs = Integer.valueOf(value.split("\\.")[0]);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static Short getShort(String value) {
        Short rs = null;
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
                rs = Short.valueOf(value);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static Double getDouble(String value) {
        Double rs = null;
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
                rs = Double.valueOf(value);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static Float getFloat(String value) {
        Float rs = null;
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
                rs = Float.valueOf(value);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static BigDecimal getBigDecimal(String value) {
        BigDecimal rs = null;
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
                rs = new BigDecimal(value);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static Byte getByte(String value) {
        Byte rs = null;
        try {
            if (StringUtils.isNotEmpty(value)) {
                rs = Byte.valueOf(value);
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return rs;
    }

    private static Date getDate(String value, String pattem) {
        SimpleDateFormat format = null;
        if (pattem != null) {
            format = new SimpleDateFormat(pattem);
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            return format.parse(value);
        } catch (Exception e) {
            log.error("com.xunlei.game.activity.utils.DateUtil.getDate:", e);
        }
        return null;
    }


//    /**
//     * 功能描述:
//     *
//     * @param object
//     * @param field
//     * @param value
//     */
//    public static final <T> void setProperty(final Object object, final String field, final T value) {
//        try {
////            for (Method method : object.getClass().getMethods()) {
//////                if (StringUtils.equals(method.getName(), "set" + org.apache.commons.lang3.StringUtils.capitalize(field))) {
//////                    method.invoke(object, value);
//////                    break;
//////                }
//////            }
//            PropertyUtils.setProperty(object,field,value);
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//    }

    public static <T> T objectToBean(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }

        try {
            String jsonString = JSON.toJSONString(source);
            return JSON.parseObject(jsonString, clazz);
        } catch (RuntimeException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 将对象装换为 map,对象转成 map，key肯定是字符串
     *
     * @param bean 转换对象
     * @return 返回转换后的 map 对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> beanToMap(Object bean) {
        return null == bean ? null : BeanMap.create(bean);
    }

    /**
     * map 装换为 java bean 对象
     *
     * @param map   转换 MAP
     * @param clazz 对象 Class
     * @return 返回 bean 对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T bean = ClassUtils.newInstance(clazz);
        BeanMap.create(bean).putAll(map);
        return bean;
    }

    /**
     * List&lt;T&gt; 转换为 List&lt;Map&lt;String, Object&gt;&gt;
     *
     * @param beans 转换对象集合
     * @return 返回转换后的 bean 列表
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(com.baomidou.mybatisplus.core.toolkit.BeanUtils::beanToMap).collect(toList());
    }

    /**
     * List&lt;Map&lt;String, Object&gt;&gt; 转换为 List&lt;T&gt;
     *
     * @param maps  转换 MAP 集合
     * @param clazz 对象 Class
     * @return 返回转换后的 bean 集合
     */
    public static <T> List<T> mapsToBeans(List<Map<String, Object>> maps, Class<T> clazz) {
        if (CollectionUtils.isEmpty(maps)) {
            return Collections.emptyList();
        }
        return maps.stream().map(e -> mapToBean(e, clazz)).collect(toList());
    }

    public static <T> List<T> listToList(List<Map<String, Object>> maps, Class<T> clazz) {
        if (CollectionUtils.isEmpty(maps)) {
            return Collections.emptyList();
        }
        return maps.stream().map(e -> mapToBean(e, clazz)).collect(toList());
    }
}
