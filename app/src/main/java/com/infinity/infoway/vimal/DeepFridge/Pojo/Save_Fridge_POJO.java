package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONObject;

public class Save_Fridge_POJO {
    public String MESSAGE;
    public int ID;
    public int FLAG;

    public Save_Fridge_POJO(String json) throws Exception {
        this(new JSONObject(json));
    }

    public Save_Fridge_POJO(JSONObject json) throws Exception {
        MESSAGE = json.optString("MESSAGE");
        ID = json.optInt("ID");
        FLAG = json.optInt("FLAG");

    }
}
