package com.example.pdfdemo1.services.presention;

import com.blankj.utilcode.util.ConvertUtils;
import com.example.pdfdemo1.entities.SystemLog;
import com.example.pdfdemo1.services.business.Source;
import com.example.pdfdemo1.services.business.SystemLogConfiguration;
import com.example.pdfdemo1.services.business.TestLogConfiguration;
import com.example.pdfdemo1.services.business.TestLogData;
import com.example.pdfdemo1.services.presention.filler.SourceFiller;
import com.example.pdfdemo1.services.presention.filler.TestLogDataFiller;
import com.example.pdfdemo1.services.presention.filler.TestLogFiller;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.sdk.pdf.interform.Form;
import com.foxit.uiextensions.UIExtensionsManager;
import com.google.gson.JsonElement;

import org.dom4j.Document;

import java.io.FileNotFoundException;
import java.util.HashMap;
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

    /**
     * 原始记录数据
     */
    private Map<String, JsonElement> testLogData;

    private Map<String, String> fields = new HashMap<>();

    public boolean load(String path, String eqpType) {
        try {
            loadResources(path, eqpType);
            openDocument(path);

            // 判断文档中是否存在表单
            if (document.hasForm()) {
                Form form = new Form(document);
                // 判断是否存在系统文件配置
                if (systemLog != null) {
                    // 判断是否为首次打开
                    if (systemLog.getFirstopen() == 0) {
                        if (systemLog.getCopydataflag() == 1) {
                            // 复制数据
                            new TestLogDataFiller().initialize(TestLogData.read(path)).fill(form, fields);
                            new SourceFiller().initialize(document, testLog).fill(form, fields);
                        } else if (systemLog.getCopyflag() == 1) {
                            // 复制PDF
                            new SourceFiller().initialize(document, testLog).fill(form, fields);
                        } else {
                            new TestLogFiller().initialize(testLog).fill(form, fields);
                            new SourceFiller().initialize(document, testLog).fill(form, fields);
                        }
                    }

                    /*
                    if (fieldMap.isEmpty())
						XMLUtil.nonentityField(outputFile + File.separator + Source, form, fieldMap);
					if (changelog != null && !changelog.getModifylog().isEmpty())
						modifylogs.addAll(changelog.getModifylog());
					else
						modifylogs = new ArrayList<Map<String, List<Modifylog>>>();
					initBookMark();
                    */
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
     * @throws FileNotFoundException 文件未找到异常
     */
    private void openDocument(String path) throws FileNotFoundException {
        uiExtensionsManager.openDocument(path + filename, ConvertUtils.string2Bytes(password));
        document = uiExtensionsManager.getPDFViewCtrl().getDoc();
        if (document == null) {
            throw new FileNotFoundException(path);
        }
    }
}
