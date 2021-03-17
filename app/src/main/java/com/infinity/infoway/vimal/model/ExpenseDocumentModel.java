package com.infinity.infoway.vimal.model;

/**
 * Created by nareshp on 4/5/2018.
 */

public class ExpenseDocumentModel {

    int id;
    String comp_id;
    String status;
    int edu_em_id;
    String edu_expense_name;
    String edu_description;
    String edu_amount;
    String edu_date;
    String edu_doc_photo_original_filename;
    String edu_doc_photo_filename;
    long edu_doc_photo_file_size_kb;
    int edu_user_id;
    String edu_user_mobile_no;
    String edu_doc_photo_file_as_bytes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEdu_em_id() {
        return edu_em_id;
    }

    public void setEdu_em_id(int edu_em_id) {
        this.edu_em_id = edu_em_id;
    }

    public String getEdu_expense_name() {
        return edu_expense_name;
    }

    public void setEdu_expense_name(String edu_expense_name) {
        this.edu_expense_name = edu_expense_name;
    }

    public String getEdu_description() {
        return edu_description;
    }

    public void setEdu_description(String edu_description) {
        this.edu_description = edu_description;
    }

    public String getEdu_amount() {
        return edu_amount;
    }

    public void setEdu_amount(String edu_amount) {
        this.edu_amount = edu_amount;
    }

    public String getEdu_date() {
        return edu_date;
    }

    public void setEdu_date(String edu_date) {
        this.edu_date = edu_date;
    }

    public String getEdu_doc_photo_original_filename() {
        return edu_doc_photo_original_filename;
    }

    public void setEdu_doc_photo_original_filename(String edu_doc_photo_original_filename) {
        this.edu_doc_photo_original_filename = edu_doc_photo_original_filename;
    }

    public String getEdu_doc_photo_filename() {
        return edu_doc_photo_filename;
    }

    public void setEdu_doc_photo_filename(String edu_doc_photo_filename) {
        this.edu_doc_photo_filename = edu_doc_photo_filename;
    }

    public long getEdu_doc_photo_file_size_kb() {
        return edu_doc_photo_file_size_kb;
    }

    public void setEdu_doc_photo_file_size_kb(long edu_doc_photo_file_size_kb) {
        this.edu_doc_photo_file_size_kb = edu_doc_photo_file_size_kb;
    }

    public int getEdu_user_id() {
        return edu_user_id;
    }

    public void setEdu_user_id(int edu_user_id) {
        this.edu_user_id = edu_user_id;
    }

    public String getEdu_user_mobile_no() {
        return edu_user_mobile_no;
    }

    public void setEdu_user_mobile_no(String edu_user_mobile_no) {
        this.edu_user_mobile_no = edu_user_mobile_no;
    }

    public String getEdu_doc_photo_file_as_bytes() {
        return edu_doc_photo_file_as_bytes;
    }

    public void setEdu_doc_photo_file_as_bytes(String edu_doc_photo_file_as_bytes) {
        this.edu_doc_photo_file_as_bytes = edu_doc_photo_file_as_bytes;
    }
}
