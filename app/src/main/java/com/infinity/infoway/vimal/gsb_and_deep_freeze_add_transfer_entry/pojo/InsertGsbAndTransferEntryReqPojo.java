package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo;

import java.io.Serializable;

public class InsertGsbAndTransferEntryReqPojo implements Serializable {


    String app_version;
    String android_id;
    String device_id;
    String user_id;
    String key;
    String comp_id;
    String transfer_type;
    String transfer_dnt;
    String from_distributor_id;
    String to_distributor_id;
    String from_retailer_id;
    String to_retailer_id;
    String remarks;
    String item_detail;
    String id;
    String serial_no;
    String fridge_type;
    String fridge_comp_name;

    public InsertGsbAndTransferEntryReqPojo(String app_version, String android_id, String device_id, String user_id, String key, String comp_id, String transfer_type, String transfer_dnt, String from_distributor_id, String to_distributor_id, String from_retailer_id, String to_retailer_id, String remarks, String item_detail, String id, String serial_no, String fridge_type, String fridge_comp_name) {
        this.app_version = app_version;
        this.android_id = android_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.key = key;
        this.comp_id = comp_id;
        this.transfer_type = transfer_type;
        this.transfer_dnt = transfer_dnt;
        this.from_distributor_id = from_distributor_id;
        this.to_distributor_id = to_distributor_id;
        this.from_retailer_id = from_retailer_id;
        this.to_retailer_id = to_retailer_id;
        this.remarks = remarks;
        this.item_detail = item_detail;
        this.id = id;
        this.serial_no = serial_no;
        this.fridge_type = fridge_type;
        this.fridge_comp_name = fridge_comp_name;
    }
}
