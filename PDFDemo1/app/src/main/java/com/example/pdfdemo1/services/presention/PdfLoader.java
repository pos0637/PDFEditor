package com.example.pdfdemo1.services.presention;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.pdfdemo1.entities.Bookmark;
import com.example.pdfdemo1.entities.ModifyLog;
import com.example.pdfdemo1.entities.SystemLog;
import com.example.pdfdemo1.miscs.JsonUtils;
import com.example.pdfdemo1.miscs.PdfUtils;
import com.example.pdfdemo1.services.business.ModifyLogData;
import com.example.pdfdemo1.services.business.Source;
import com.example.pdfdemo1.services.business.SystemLogConfiguration;
import com.example.pdfdemo1.services.business.TestLogConfiguration;
import com.example.pdfdemo1.services.business.TestLogData;
import com.example.pdfdemo1.services.presention.filler.SourceFiller;
import com.example.pdfdemo1.services.presention.filler.TestLogDataFiller;
import com.example.pdfdemo1.services.presention.filler.TestLogFiller;
import com.foxit.sdk.PDFException;
import com.foxit.sdk.PDFViewCtrl;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.sdk.pdf.ReadingBookmark;
import com.foxit.sdk.pdf.interform.Form;
import com.foxit.uiextensions.UIExtensionsManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.dom4j.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static final String PASSWORD = "sesadmin";

    /**
     * 文件名
     */
    private static final String FILENAME = "/testlog.pdf";

    /**
     * 数字输入域键值
     */
    private static final String NUMBER_INPUT_FIELD_KEY = "input_num";

    /**
     * 书签键值
     */
    private static final String BOOKMARK_KEY = "_bookmarks";

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

    /**
     * 修改记录
     */
    private List<Map<String, List<ModifyLog>>> modifyLog = new ArrayList<>();

    /**
     * 域
     */
    private Map<String, String> fields = new HashMap<>();

    /**
     * 数字输入域
     */
    private List<String> numberInputFields = new ArrayList<>();

    /**
     * 书签
     */
    private List<Bookmark> bookmarks = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param uiExtensionsManager 界面扩展管理器
     */
    public PdfLoader(UIExtensionsManager uiExtensionsManager) {
        this.uiExtensionsManager = uiExtensionsManager;
    }

    /**
     * 加载
     *
     * @param path    路径
     * @param eqpType 业务类型
     * @return 是否成功
     */
    public boolean load(String path, String eqpType) {
        try {
            loadResources(path, eqpType);
            openDocument(path);

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
        testLogData = TestLogData.read(path);
        modifyLog = ModifyLogData.read(path);
    }

    /**
     * 加载PDF文档
     *
     * @param document PDF文档
     */
    private void loadDocument(PDFDoc document) {
        try {
            // 判断文档中是否存在表单
            if (document.hasForm()) {
                Form form = new Form(document);
                // 判断是否存在系统文件配置
                if (systemLog != null) {
                    // 判断是否为首次打开
                    if (systemLog.getFirstopen() == 0) {
                        if (systemLog.getCopydataflag() == 1) {
                            // 复制数据
                            new TestLogDataFiller().initialize(testLogData).fill(form, fields);
                            new SourceFiller().initialize(source, testLog).fill(form, fields);
                        } else if (systemLog.getCopyflag() == 1) {
                            // 复制PDF
                            new SourceFiller().initialize(source, testLog).fill(form, fields);
                        } else {
                            new TestLogFiller().initialize(testLog).fill(form, fields);
                            new SourceFiller().initialize(source, testLog).fill(form, fields);
                        }
                    }

                    if (fields.isEmpty()) {
                        new SourceFiller().initialize(source, testLog).fill(form, fields);
                    }
                }

                // 初始化数字输入域
                initializeNumberInputFields();
                // 初始化表单样式
                initializeFormStyle();
            }

            // 初始化书签
            initializeBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开PDF文档
     *
     * @param path 路径
     */
    private void openDocument(String path) {
        uiExtensionsManager.getPDFViewCtrl().registerDocEventListener(new PDFViewCtrl.IDocEventListener() {
            @Override
            public void onDocWillOpen() {
            }

            @Override
            public void onDocOpened(PDFDoc pdfDoc, int i) {
                if (pdfDoc != null) {
                    loadDocument(pdfDoc);
                }
            }

            @Override
            public void onDocWillClose(PDFDoc pdfDoc) {
            }

            @Override
            public void onDocClosed(PDFDoc pdfDoc, int i) {
            }

            @Override
            public void onDocWillSave(PDFDoc pdfDoc) {
            }

            @Override
            public void onDocSaved(PDFDoc pdfDoc, int i) {
            }
        });

        uiExtensionsManager.openDocument(path + FILENAME, ConvertUtils.string2Bytes(PASSWORD));
    }

    /**
     * 初始化表单样式
     */
    private void initializeFormStyle() {
        UIExtensionsManager.enableFormHighlight();
        uiExtensionsManager.setFormHighlightColor(0x80B0E0E6);
    }

    /**
     * 初始化数字输入域
     */
    private void initializeNumberInputFields() {
        if (testLog == null) {
            return;
        }

        JsonArray array = JsonUtils.getJsonArray(testLog, NUMBER_INPUT_FIELD_KEY);
        if (array != null) {
            int size = array.size();
            for (int i = 0; i < size; i++) {
                numberInputFields.add(array.get(i).getAsString());
            }
        }
    }

    /**
     * 初始化书签
     */
    private void initializeBookmarks() throws PDFException {
        if ((testLog == null) || (!testLog.containsKey(BOOKMARK_KEY))) {
            return;
        }

        int count = document.getReadingBookmarkCount();
        for (int i = 0; i < count; i++) {
            ReadingBookmark bookmark = document.getReadingBookmark(i);
            if (!bookmark.isEmpty()) {
                bookmarks.add(new Bookmark(bookmark.getTitle(), bookmark.getPageIndex()));
            }
        }

        String value = JsonUtils.getValue(testLog, BOOKMARK_KEY);
        if (!StringUtils.isEmpty(value)) {
            bookmarks = JsonUtils.toObject(value, new TypeToken<ArrayList<Bookmark>>() {
            }.getType());
            for (Bookmark bookmark : bookmarks) {
                PdfUtils.addBookmark(document, bookmark.getTitle(), bookmark.getPage());
            }
        }
    }
}
