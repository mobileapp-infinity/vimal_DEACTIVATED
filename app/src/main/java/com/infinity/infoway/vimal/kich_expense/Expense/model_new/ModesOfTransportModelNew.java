package com.infinity.infoway.vimal.kich_expense.Expense.model_new;

import com.google.gson.annotations.SerializedName;
import com.infinity.kich.Expense.Pojo.Get_All_Transport_Expense_Mode_Pojo;

import java.util.List;

public class ModesOfTransportModelNew {

    @SerializedName("TOTAL")
    private int tOTAL;

    @SerializedName("RECORDS")
    private List<Get_All_Transport_Expense_Mode_Pojo.RECORDSItem> rECORDS;

    @SerializedName("MESSAGE")
    private String mESSAGE;

    public int getTOTAL(){
        return tOTAL;
    }

    public List<Get_All_Transport_Expense_Mode_Pojo.RECORDSItem> getRECORDS(){
        return rECORDS;
    }

    public String getMESSAGE(){
        return mESSAGE;
    }
    public class RECORDSItem{

        @SerializedName("ID")
        private int iD;

        @SerializedName("Name")
        private String name;

        public int getID(){
            return iD;
        }

        public String getName(){
            return name;
        }
    }
}
