/**
 * Copyright (C) 2003-2020, Foxit Software Inc..
 * All Rights Reserved.
 * <p>
 * http://www.foxitsoftware.com
 * <p>
 * The following code is copyrighted and is the proprietary of Foxit Software Inc.. It is not allowed to
 * distribute any parts of Foxit PDF SDK to third party or public without permission unless an agreement
 * is signed between Foxit Software Inc. and customers to explicitly grant customers permissions.
 * Review legal.txt for additional license and legal information.
 */
package com.foxit.uiextensions.annots.common;

import com.foxit.sdk.Task;
import com.foxit.uiextensions.utils.Event;

public class EditAnnotTask extends Task {

    private EditAnnotEvent mEvent = null;
    private boolean bSuccess = true;

    public EditAnnotTask(EditAnnotEvent event, final Event.Callback callBack) {
        super(new CallBack() {
            @Override
            public void result(Task task) {
                EditAnnotTask task1 = (EditAnnotTask) task;
                if (callBack != null) {
                    callBack.result(task1.mEvent, task1.bSuccess);
                }
            }
        });

        mEvent = event;
    }

    @Override
    protected void execute() {
        if (mEvent != null) {
            bSuccess = mEvent.execute();
        }
    }

    @Override
    protected boolean isModify() {
        return super.isModify();
    }
}
