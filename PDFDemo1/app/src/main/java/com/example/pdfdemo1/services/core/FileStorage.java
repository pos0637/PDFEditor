package com.example.pdfdemo1.services.core;

import com.blankj.utilcode.util.FileIOUtils;

import java.io.File;

/**
 * 文件管理器
 *
 * @author Alex
 */
public class FileStorage {
    /**
     * 读取文件
     *
     * @param path        路径
     * @param charsetName 字符集
     * @return 文件内容
     */
    public String read(final String path, final String charsetName) {
        return FileIOUtils.readFile2String(new File(path), charsetName);
    }
}
