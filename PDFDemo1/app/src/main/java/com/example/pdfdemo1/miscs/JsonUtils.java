package com.example.pdfdemo1.miscs;

import com.google.gson.JsonElement;

import java.util.Map;

/**
 * JSON工具类
 *
 * @author Alex
 */
public final class JsonUtils {
    /**
     * 获取值
     *
     * @param map JSON散列表
     * @param key 键值
     * @return 值
     */
    public static String getValue(final Map<String, JsonElement> map, final String key) {
        if (!map.containsKey(key)) {
            return null;
        }

        JsonElement element = map.get(key);
        if (element == null) {
            return null;
        }

        return element.getAsString();
    }
}
