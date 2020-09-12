package com.example.pdfdemo1.services.business;

import com.example.pdfdemo1.entities.SystemLog;
import com.example.pdfdemo1.services.core.FileStorage;
import com.google.gson.Gson;

/**
 * 系统文件配置
 *
 * @author Alex
 */
public final class SystemLogConfiguration {
    /**
     * 文件名
     */
    private static final String filename = "/systemlog.ses";

    /**
     * 读取系统文件配置
     *
     * @param path 路径
     * @return 配置
     */
    public static SystemLog read(final String path) {
        // 读取系统文件配置文件
        String content = FileStorage.read(path + filename, "utf-8");
        if (content == null) {
            return null;
        }

        return new Gson().fromJson(content, SystemLog.class);
    }
}
