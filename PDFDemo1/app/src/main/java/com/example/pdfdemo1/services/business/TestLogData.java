package com.example.pdfdemo1.services.business;

import com.example.pdfdemo1.services.core.FileStorage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 原始记录数据
 *
 * @author Alex
 */
public final class TestLogData {
    /**
     * 文件名
     */
    private static final String filename = "/testlogdata.ses";

    /**
     * 读取原始记录数据
     *
     * @param path 路径
     * @return 数据
     */
    public static Map<String, JsonElement> read(final String path) {
        // 读取原始记录配置文件
        String content = FileStorage.read(path + filename, "utf-8");
        if (content == null) {
            return null;
        }

        HashMap<String, JsonElement> map = new HashMap<String, JsonElement>();

        try {
            // JSON字符串转换为JSON对象
            JsonObject object = JsonParser.parseString(content).getAsJsonObject();
            // 根据配置名称查找键值
            JsonArray jsonArray = object.get("testlogdata").getAsJsonArray();
            // 获取取首个元素
            JsonObject json = (JsonObject) jsonArray.get(0);
            // 映射为键值对
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }

            return map;
        } catch (Exception e) {
            // TODO: 异常处理
            e.printStackTrace();
        }

        return map;
    }
}
