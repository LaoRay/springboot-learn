package com.zhengtoon.framework.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil {
    
    public static Map reverseKV(Map map) {
        Iterator<Map.Entry<String, String>> it = (map.entrySet()).iterator();
        Map result = new HashMap();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }
    
    public static Map<String, Object> ConvertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(
                        fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (Exception ignored) {
                
                }
            }
        } catch (SecurityException ignored) {
        
        }
        return reMap;
    }
}
