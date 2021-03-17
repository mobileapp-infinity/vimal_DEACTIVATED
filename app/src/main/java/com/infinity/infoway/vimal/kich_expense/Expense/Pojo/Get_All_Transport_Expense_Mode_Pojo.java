package com.infinity.infoway.vimal.kich_expense.Expense.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Get_All_Transport_Expense_Mode_Pojo {

	@SerializedName("TOTAL")
	private int tOTAL;

	@SerializedName("RECORDS")
	private List<RECORDSItem> rECORDS;

	@SerializedName("MESSAGE")
	private String mESSAGE;

	public int getTOTAL(){
		return tOTAL;
	}

	public List<RECORDSItem> getRECORDS(){
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


