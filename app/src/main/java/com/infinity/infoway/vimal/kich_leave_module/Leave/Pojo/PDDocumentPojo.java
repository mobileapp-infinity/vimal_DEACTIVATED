package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class PDDocumentPojo
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
         * Title : Document - 1
         * Download_Document : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/pd/1/111/Document/111_0_72_714_Failures _ Stepping stone for success.pdf
         */

        private String Title;
        private String Download_Document;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDownload_Document() {
            return Download_Document;
        }

        public void setDownload_Document(String Download_Document) {
            this.Download_Document = Download_Document;
        }
    }
}
