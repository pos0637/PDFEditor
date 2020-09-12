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
     * @return 是否成功
     */
    boolean initialize(Object... arguments);

    /**
     * 填充
     *
     * @param form 表单
     * @param fields 域
     * @return 是否成功
     */
    boolean fill(final Form form, final Map<String, String> fields);
}
