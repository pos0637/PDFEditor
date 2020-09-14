package com.example.pdfdemo1.services.presention.filler;

import com.example.pdfdemo1.miscs.JsonUtils;
import com.foxit.sdk.pdf.interform.Field;
import com.foxit.sdk.pdf.interform.Form;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * 原始记录数据填充器
 *
 * @author Alex
 */
public class TestLogDataFiller implements Filler {
    /**
     * 原始记录数据
     */
    private Map<String, JsonElement> testLogData;

    @Override
    public boolean initialize(Object... arguments) {
        return false;
    }

    @Override
    public boolean fill(Form form, Map<String, String> fields) {
        try {
            // 遍历所有表单域
            int count = form.getFieldCount(null);
            for (int i = 0; i < count; i++) {
                Field field = form.getField(i, null);
                String value = JsonUtils.getValue(testLogData, field.getName());
                if (value != null) {
                    field.setValue(value);
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
