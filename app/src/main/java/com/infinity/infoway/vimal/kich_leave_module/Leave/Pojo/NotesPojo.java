package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class NotesPojo
{


    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Notes : Casual Leave Policy: Employee can apply for 1 CL per month and in emergency they can use additioinal 2 more leaves. But, nos. of applicatioin should not exceed current month + 2. example: In month of May, you can use maximum 7 leaves.
         */

        private String Notes;

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String Notes) {
            this.Notes = Notes;
        }
    }
}
