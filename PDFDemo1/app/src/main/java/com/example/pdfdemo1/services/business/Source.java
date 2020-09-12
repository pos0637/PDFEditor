package com.example.pdfdemo1.services.business;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 数据源
 *
 * @author Alex
 */
public final class Source {
    /**
     * 文件名
     */
    private static final String filename = "/source.xml";

    /**
     * 读取数据源
     *
     * @param path 路径
     * @return 数据源
     */
    public static Document read(String path) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(path + filename);
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("utf-8");
            return saxReader.read(is);
        } catch (Exception e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
