package com.example.pdfdemo1.services.presention.filler;

import com.blankj.utilcode.util.StringUtils;
import com.example.pdfdemo1.miscs.JsonUtils;
import com.foxit.sdk.pdf.interform.Field;
import com.foxit.sdk.pdf.interform.Form;
import com.google.gson.JsonElement;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 数据源填充器
 *
 * @author Alex
 */
public final class SourceFiller implements Filler {
    /**
     * 所有域名信息键值
     */
    private static final String EQUIP_INFO = "equipinfo";

    /**
     * 需要复制域名
     */
    private static final List<String> DEFAULT_COPY_FIELDS = new ArrayList<>(Arrays.asList("lognum", "EQP_COD", "EQP_MOD", "FACTORY_COD", "OIDNO", "reportno", "REPORT_COD"));

    /**
     * 不复制域名
     */
    private static final String NO_COPY_FIELD = "nocopyfield";

    /**
     * 数据源XML文档
     */
    private Document document;

    /**
     * 需要复制域名
     */
    private List<String> copyFields;

    @Override
    @SuppressWarnings("unchecked")
    public Filler initialize(Object... arguments) {
        document = (Document) arguments[0];

        // 构造需要复制域名
        if (arguments[1] != null) {
            copyFields = new ArrayList<>(DEFAULT_COPY_FIELDS);
            Map<String, JsonElement> testLog = (Map<String, JsonElement>) arguments[1];
            String noCopyFields = JsonUtils.getValue(testLog, NO_COPY_FIELD);
            if (noCopyFields != null) {
                Collections.addAll(copyFields, noCopyFields.split(","));
            }
        }

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fill(Form form, final Map<String, String> fields) throws Exception {
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.elements();
        for (Element element : list) {
            List<Element> bean = element.elements();
            String fieldName = bean.get(0).getStringValue();
            String fieldValue = bean.get(1).getStringValue();

            // 根据XML节点名称进行匹配
            if (element.getName().equals(EQUIP_INFO)) {
                // 查找是否存在指定域名的域
                Field field = form.getField(0, fieldName);
                if ((field != null) && isCopyField(fieldName)) {
                    // 填充域内容
                    if (!StringUtils.isEmpty(fieldValue)) {
                        field.setValue(fieldValue);
                    }
                } else {
                    fields.put(fieldName, fieldValue);
                }
            }
        }
    }

    /**
     * 是否需要复制指定域
     *
     * @param fieldName 域名
     * @return 是否需要复制
     */
    private boolean isCopyField(String fieldName) {
        if (copyFields == null) {
            return true;
        }

        // TODO: 和客户确定需要复制域
        return copyFields.contains(fieldName)
                || (fieldName.contains("_GCRES"))
                || (fieldName.contains("_CLRES"));
    }
}
