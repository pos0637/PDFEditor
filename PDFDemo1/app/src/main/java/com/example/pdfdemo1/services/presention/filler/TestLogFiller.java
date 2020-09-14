package com.example.pdfdemo1.services.presention.filler;

import com.example.pdfdemo1.miscs.PdfUtils;
import com.foxit.sdk.pdf.interform.Field;
import com.foxit.sdk.pdf.interform.Form;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * 模板配置填充器
 *
 * @author Alex
 */
public final class TestLogFiller implements Filler {
    /**
     * 模板配置
     */
    private Map<String, JsonElement> testLogConfiguration;

    @Override
    @SuppressWarnings("unchecked")
    public boolean initialize(Object... arguments) {
        testLogConfiguration = (Map<String, JsonElement>) arguments[0];
        return false;
    }

    @Override
    public boolean fill(Form form, final Map<String, String> fields) {
        try {
            // 遍历所有表单域
            int count = form.getFieldCount(null);
            for (int i = 0; i < count; i++) {
                Field field = form.getField(i, null);

                // 根据表单域类型进行处理
                if (field.getType() == Field.e_TypeComboBox) {
                    // 表单域类型为下拉菜单
                    JsonElement element = testLogConfiguration.get(field.getName());
                    if (element != null) {
                        // 设置下拉菜单表单域
                        PdfUtils.setComboBoxField(field, element.getAsJsonArray());
                    } else {
                        // 对未配置内容的表单域进行默认填充
                        if (PdfUtils.isEmptyField(field)) {
                            field.setValue(" ");
                        }
                    }
                } else {
                    // 设置表单域内容为默认值
                    PdfUtils.setDefaultValue(field);
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
