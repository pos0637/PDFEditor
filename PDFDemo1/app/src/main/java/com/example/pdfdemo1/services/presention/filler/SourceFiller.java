package com.example.pdfdemo1.services.presention.filler;

import com.blankj.utilcode.util.StringUtils;
import com.foxit.sdk.pdf.interform.Field;
import com.foxit.sdk.pdf.interform.Form;

import org.dom4j.Document;
import org.dom4j.Element;

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
    private static final String equipinfo = "equipinfo";

    /**
     * 数据源XML文档
     */
    private Document document;

    @Override
    public boolean initialize(Object... arguments) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean fill(Form form, final Map<String, String> fields) {
        try {
            Element rootElement = document.getRootElement();
            List<Element> list = rootElement.elements();
            for (Element element : list) {
                List<Element> bean = element.elements();
                // 根据XML节点名称进行匹配
                if (element.getName().equals(equipinfo)) {
                    // 查找是否存在指定域名的域
                    Field field = form.getField(0, bean.get(0).getStringValue());
                    if (field != null) {
                        // 填充域内容
                        if (!StringUtils.isEmpty(bean.get(1).getStringValue())) {
                            field.setValue(bean.get(1).getStringValue());
                        }
                    } else {
                        fields.put(bean.get(0).getStringValue(), bean.get(1).getStringValue());
                    }
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
