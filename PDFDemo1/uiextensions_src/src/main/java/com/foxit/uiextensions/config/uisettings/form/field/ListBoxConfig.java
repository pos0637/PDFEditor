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
package com.foxit.uiextensions.config.uisettings.form.field;


import com.foxit.uiextensions.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ListBoxConfig extends BaseFieldConfig {

    private static final String KEY_MULTI_SELECTION = "multipleSelection";

    public boolean multipleSelection = false;

    @Override
    public void parseConfig(JSONObject jsonObject, String name) {
        super.parseConfig(jsonObject, name);
        try {
            JSONObject object = jsonObject.getJSONObject(name);
            if (object.has(KEY_MULTI_SELECTION))
                multipleSelection = JsonUtil.getBoolean(object, KEY_MULTI_SELECTION, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
