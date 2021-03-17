package com.infinity.infoway.vimal.delear.RoutePlanning.pojo;

public class SaveRouteModel {

    private int route_id;
    private int sales_person_id;

    public SaveRouteModel(int route_id, int sales_person_id) {
        this.route_id = route_id;
        this.sales_person_id = sales_person_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getSales_person_id() {
        return sales_person_id;
    }

    public void setSales_person_id(int sales_person_id) {
        this.sales_person_id = sales_person_id;
    }
}
