package com.example.pdfdemo1.services.presention;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.pdfdemo1.entities.SystemLog;
import com.example.pdfdemo1.services.business.SystemLogConfiguration;
import com.example.pdfdemo1.services.business.TestLogConfiguration;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.uiextensions.UIExtensionsManager;
import com.google.gson.JsonElement;

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

    private UIExtensionsManager uiExtensionsManager;

    /**
     * PDF文档
     */
    private PDFDoc document;

    /**
     * 系统文件配置
     */
    private SystemLog systemLog;

    public boolean load(String path, String eqpType) {
        try {
            uiExtensionsManager.openDocument(path + filename, ConvertUtils.string2Bytes(password));
            document = uiExtensionsManager.getPDFViewCtrl().getDoc();
            systemLog = SystemLogConfiguration.read(path);

            // 判断是否为首次打开
            if ((systemLog != null) && (systemLog.getFirstopen() == 0)) {
                if (systemLog.getCopydataflag() == 1) {

                }
            }
            // 读取模板配置
            Map<String, JsonElement> testlog = TestLogConfiguration.read(path, eqpType);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
