package com.example.pdfdemo1.services.business;

import com.example.pdfdemo1.entities.ModifyLog;
import com.example.pdfdemo1.services.core.FileStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * 修改日志
 *
 * @author Alex
 */
public final class ModifyLogData {
    /**
     * 文件名
     */
    private static final String filename = "/changelog.ses";

    /**
     * 读取修改日志
     *
     * @param path 路径
     * @return 修改日志
     */
    public static List<Map<String, List<ModifyLog>>> read(final String path) {
        // 读取系统文件配置文件
        String content = FileStorage.read(path + filename, "utf-8");
        if (content == null) {
            return null;
        }

        return new Gson().fromJson(content, new TypeToken<List<Map<String, List<ModifyLog>>>>() {
        }.getType());
    }
}
