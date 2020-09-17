package com.example.pdfdemo1.services.presention.filler;

import com.foxit.sdk.pdf.interform.Form;

import java.util.Map;

/**
 * 填充器
 *
 * @author Alex
 */
public interface Filler {
    /**
     * 初始化
     *
     * @param arguments 参数
     * @return 填充器
     * @throws Exception 异常
     */
    Filler initialize(Object... arguments) throws Exception;

    /**
     * 填充
     *
     * @param form   表单
     * @param fields 域
     * @throws Exception 异常
     */
    void fill(final Form form, final Map<String, String> fields) throws Exception;
}
