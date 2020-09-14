package com.example.pdfdemo1.services.presention;

import com.blankj.utilcode.util.ConvertUtils;
import com.example.pdfdemo1.entities.SystemLog;
import com.example.pdfdemo1.services.business.Source;
import com.example.pdfdemo1.services.business.SystemLogConfiguration;
import com.example.pdfdemo1.services.business.TestLogConfiguration;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.uiextensions.UIExtensionsManager;
import com.google.gson.JsonElement;

import org.dom4j.Document;

import java.util.Map;

/**
 * PDF加载器
 *
 * @author Alex
 */
public final class PdfLoader {
    /**
     * 密码
     */
    private static final String password = "sesadmin";

    /**
     * 文件名
     */
    private static final String filename = "/teslog.pdf";

    /**
     * 界面扩展管理器
     */
    private UIExtensionsManager uiExtensionsManager;

    /**
     * PDF文档
     */
    private PDFDoc document;

    /**
     * 数据源
     */
    private Document source;

    /**
     * 系统文件配置
     */
    private SystemLog systemLog;

    /**
     * 模板配置
     */
    private Map<String, JsonElement> testLog;

    public boolean load(String path, String eqpType) {
        try {
            loadResources(path, eqpType);
            openDocument(path);

            // 判断是否为首次打开
            if ((systemLog != null) && (systemLog.getFirstopen() == 0)) {
                if (systemLog.getCopydataflag() == 1) {
                    // 复制数据
                } else if (systemLog.getCopyflag() == 1) {
                    // 复制PDF
                } else {

                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取资源文件
     *
     * @param path    路径
     * @param eqpType 业务类型
     */
    private void loadResources(String path, String eqpType) {
        source = Source.read(path);
        systemLog = SystemLogConfiguration.read(path);
        testLog = TestLogConfiguration.read(path, eqpType);
    }

    /**
     * 打开PDF文档
     *
     * @param path 路径
     */
    private void openDocument(String path) {
        uiExtensionsManager.openDocument(path + filename, ConvertUtils.string2Bytes(password));
        document = uiExtensionsManager.getPDFViewCtrl().getDoc();
    }
}
