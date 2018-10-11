package com.zhengtoon.framework.pay.utils;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.Element;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leiqiyun
 * @date 2018/9/29 10:19
 */
public class MapUtils {

    /**
     * 对象转map
     *
     * @param obj
     * @return
     */
    public static Map<String, String> buildMap(Object obj) {
        Map<String, String> map = new HashMap<>();

        try {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();

                //如果 element 注解 name 字段设置了内容, 使用其当成字段名
                Element element = field.getAnnotation(Element.class);
                if (element != null && StringUtils.isNotEmpty(element.name())) {
                    fieldName = element.name();
                }

                String value = field.get(obj) == null ? "" : String.valueOf(field.get(obj));
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
