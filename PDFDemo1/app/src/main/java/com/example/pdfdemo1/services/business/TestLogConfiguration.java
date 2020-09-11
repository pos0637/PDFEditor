package com.example.pdfdemo1.services.business;

import com.example.pdfdemo1.services.core.FileStorage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 原始记录配置
 *
 * @author Alex
 */
public final class TestLogConfiguration {
    /**
     * 读取原始记录配置
     *
     * @param path 路径
     * @param key  配置名称
     * @return 配置
     */
    public static Map<String, String> read(String path, String key) {
        // 读取原始记录配置文件
        String content = FileStorage.read(path, Charset.defaultCharset().name());
        if (content == null) {
            return null;
        }

        HashMap<String, String> map = new HashMap<String, String>();

        try {
            JsonObject obj = JsonParser.parseString(content).getAsJsonObject();
            JsonArray jsonArray = obj.get(key).getAsJsonArray();
            JsonObject json = (JsonObject) jsonArray.get(0);
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                map.put(entry.getKey(), entry.getValue().getAsString());
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
