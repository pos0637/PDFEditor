package com.example.pdfdemo1.miscs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
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

    /**
     * 获取数组
     *
     * @param map JSON散列表
     * @param key 键值
     * @return 数组
     */
    public static JsonArray getJsonArray(final Map<String, JsonElement> map, final String key) {
        if (!map.containsKey(key)) {
            return null;
        }

        JsonElement element = map.get(key);
        if (element == null) {
            return null;
        }

        return element.getAsJsonArray();
    }

    /**
     * JSON转对象
     *
     * @param json  JSON字符串
     * @param clazz 类型
     * @param <T>   对象类型
     * @return 对象
     */
    public static <T> T toObject(final String json, final Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    /**
     * JSON转对象
     *
     * @param json JSON字符串
     * @param type 类型
     * @param <T>  对象类型
     * @return 对象
     */
    public static <T> T toObject(final String json, final Type type) {
        return new Gson().fromJson(json, type);
    }
}
