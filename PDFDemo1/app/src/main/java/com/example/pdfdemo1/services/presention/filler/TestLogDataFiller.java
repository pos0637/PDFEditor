package com.example.pdfdemo1.services.presention.filler;

import com.foxit.sdk.pdf.interform.Form;

import java.util.Map;

/**
 * 原始记录数据填充器
 *
 * @author Alex
 */
public class TestLogDataFiller implements Filler {

    @Override
    public boolean initialize(Object... arguments) {
        return false;
    }

    @Override
    public boolean fill(Form form, Map<String, String> fields) {
        return false;
    }
}
