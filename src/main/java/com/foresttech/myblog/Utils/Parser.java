package com.foresttech.myblog.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    //    记录日志，将message转字符串
    public static Map<String, Object> toMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    public static String toString(Object obj) throws IllegalAccessException {
        Map map = Parser.toMap(obj);

        if(map == null) {
            return "{}";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{ ");
        for(Object s : map.keySet()) {
            sb.append("\""+s+"\":\""+map.get(s)+"\",");
        }
        sb.replace(sb.length()-1, sb.length(), "}");
        return sb.toString();
    }
}
