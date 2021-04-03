package com.infinity.infoway.vimal.delear.RoutePlanning.model;

public class SaveRouteModel {

    private int route_id;
    private int sales_person_id;
    private boolean isChanged;
  //  private String effectiveDate;

    public SaveRouteModel(int route_id, int sales_person_id, boolean isChanged) {
        this.route_id = route_id;
        this.sales_person_id = sales_person_id;
        this.isChanged = isChanged;
        //this.effectiveDate = effectiveDate;
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

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
/*
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }*/
}
