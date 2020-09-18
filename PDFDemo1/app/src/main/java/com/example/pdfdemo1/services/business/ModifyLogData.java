package com.example.pdfdemo1.services.business;

import com.example.pdfdemo1.entities.ModifyLog;
import com.example.pdfdemo1.miscs.JsonUtils;
import com.example.pdfdemo1.services.core.FileStorage;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

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

        ModifyLogEntity entity = JsonUtils.toObject(content, ModifyLogEntity.class);
        if (entity == null) {
            return null;
        }

        return entity.getModifylog();
    }

    @Getter
    @Setter
    private static class ModifyLogEntity {
        private List<Map<String, List<ModifyLog>>> modifylog;
    }
}
