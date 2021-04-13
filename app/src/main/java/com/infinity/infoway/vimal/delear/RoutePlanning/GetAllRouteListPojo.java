package com.infinity.infoway.vimal.delear.RoutePlanning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetAllRouteListPojo {


    @SerializedName("TOTAL")
    @Expose
    private Integer tOTAL;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("RECORDS")
    @Expose
    private List<RECORD> rECORDS = null;

    public Integer getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(Integer tOTAL) {
        this.tOTAL = tOTAL;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public List<RECORD> getRECORDS() {
        return rECORDS;
    }

    public void setRECORDS(List<RECORD> rECORDS) {
        this.rECORDS = rECORDS;
    }

    public class RECORD {

        @SerializedName("route_name")
        @Expose
        private String routeName;
        @SerializedName("route_id")
        @Expose
        private Integer routeId;

        public String getRouteName() {
            return routeName;
        }

        public void setRouteName(String routeName) {
            this.routeName = routeName;
        }

        public Integer getRouteId() {
            return routeId;
        }

        public void setRouteId(Integer routeId) {
            this.routeId = routeId;
        }
        private String employeeName;
        private ArrayList<String> empList;
        private ArrayList<Integer> empListId;
        private String rvpm_vehicle_no;
        private String schedule;

        public ArrayList<String> getEmpList() {
            return empList;
        }

        public void setEmpList(ArrayList<String> empList) {
            this.empList = empList;
        }

        public String getEmployeeName() {
            return employeeName;
        }


        public ArrayList<Integer> getEmpListId() {
            return empListId;
        }

        public void setEmpListId(ArrayList<Integer> empListId) {
            this.empListId = empListId;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getRvpm_vehicle_no() {
            return rvpm_vehicle_no;
        }

        public void setRvpm_vehicle_no(String rvpm_vehicle_no) {
            this.rvpm_vehicle_no = rvpm_vehicle_no;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }
    }


}



