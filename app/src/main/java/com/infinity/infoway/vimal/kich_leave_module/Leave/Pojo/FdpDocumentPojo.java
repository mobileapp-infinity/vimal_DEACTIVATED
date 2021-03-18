package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class FdpDocumentPojo
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
         * id : 56
         * file_name : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/FDP/1/71/Document/71_714_R Workshop certificate.pdf
         */

        private String id;
        private String file_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }
    }
}
