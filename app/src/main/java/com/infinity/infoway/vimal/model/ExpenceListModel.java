package com.infinity.infoway.vimal.model;

/**
 * Created by nareshp on 3/21/2018.
 */

public class ExpenceListModel {
    int id;
    String em_expense_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEm_expense_name() {
        return em_expense_name;
    }

    public void setEm_expense_name(String em_expense_name) {
        this.em_expense_name = em_expense_name;
    }

    @Override
    public String toString() {

        return  em_expense_name;
    }
}
