package com.server.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/18/18
 * Time: 4:02 PM
 */
public class ReflectBeanUtils {

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Class classz = obj.getClass();

            Field[] declaredFields = classz.getDeclaredFields();
            for (Field field : declaredFields) {
                int mod = field.getModifiers();
                //过滤 static 和 final 类型
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        return map;
    }

}
