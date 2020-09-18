package com.example.pdfdemo1.miscs;

import com.blankj.utilcode.util.StringUtils;
import com.foxit.sdk.PDFException;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.sdk.pdf.interform.ChoiceOption;
import com.foxit.sdk.pdf.interform.ChoiceOptionArray;
import com.foxit.sdk.pdf.interform.Field;
import com.google.gson.JsonArray;

/**
 * PDF工具类
 *
 * @author Alex
 */
public final class PdfUtils {
    /**
     * 是否为空表单域
     *
     * @param field 表单域
     * @return 是否为空
     */
    public static boolean isEmptyField(final Field field) {
        try {
            return StringUtils.isTrimEmpty(field.getValue());
        } catch (PDFException e) {
            return true;
        }
    }

    /**
     * 设置表单域为默认值
     *
     * @param field 表单域
     */
    public static void setDefaultValue(final Field field) {
        try {
            if (!StringUtils.isTrimEmpty(field.getDefaultValue())) {
                field.setValue(field.getDefaultValue());
            }
        } catch (PDFException ignored) {
        }
    }

    /**
     * 设置下拉菜单表单域
     *
     * @param field 表单域
     * @param array 内容数组
     */
    public static void setComboBoxField(final Field field, final JsonArray array) {
        try {
            ChoiceOptionArray options = new ChoiceOptionArray();
            for (int i = 0; i < array.size(); i++) {
                String item = array.get(i).getAsString();
                options.add(new ChoiceOption(item, item, false, false));
            }

            field.setOptions(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加书签
     *
     * @param document  PDF文档
     * @param title     标题
     * @param pageIndex 页码
     */
    public static void addBookmark(final PDFDoc document, final String title, final int pageIndex) {
        try {
            document.insertReadingBookmark(document.getReadingBookmarkCount(), title, pageIndex);
        } catch (PDFException e) {
            e.printStackTrace();
        }
    }
}
