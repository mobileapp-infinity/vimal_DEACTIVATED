package com.infinity.infoway.vimal.util.common;


import com.infinity.infoway.vimal.config.Config;

public class URLS {

    /**
     * COMMON MODULE
     */
    public static String Get_State_list = Config.MAIN_URL + "Get_State_list?";
    public static String Get_All_City = Config.MAIN_URL + "Get_All_City?";
    public static String Get_All_Config_City_Emp_Wise = Config.MAIN_URL + "Get_All_Config_City_Emp_Wise?";
    public static String get_retailers_list_by_dealer_id = Config.MAIN_URL + "get_retailers_list_by_dealer_id?";
    public static String GetAll_Category = Config.MAIN_URL + "GetAll_Category?";
    public static String Product_List = Config.MAIN_URL + "Product_List?";
    public static String Get_All_Distributor = Config.MAIN_URL + "Get_All_Distributor?";

    public static String list_of_all_employees = Config.MAIN_URL + "list_of_all_employees?";

    /**
     * Module Expense
     */
    public static String view_expense_list = Config.MAIN_URL + "view_expense_list?";
    public static String Get_expense_list_for_approval = Config.MAIN_URL + "Get_expense_list_for_approval?";
    public static String Expense_Approval = Config.MAIN_URL + "Expense_Approval?";
    public static String fetch_expense_names = Config.MAIN_URL + "fetch_expense_names?";
    public static String GetModeOfTransportList = Config.MAIN_URL + "GetModeOfTransportList?";
    public static String Get_All_Transport_Expense_Mode = Config.MAIN_URL + "Get_All_Transport_Expense_Mode?";/*02-03-2021 pragna for updated expense rules */
    public static String Get_All_Food_Expense_Mode = Config.MAIN_URL + "Get_All_Food_Expense_Mode?";

    public static String Base_URl_FOR_LEAVE_MODULE = Config.LEAVE_MODULE_LIVE_URL;//added by remish to maintain all base url in one file


//    public static  int TIME_TILL_DISABLE_BTN = 2000;

    public static String get_multi_compny_permission_user = Base_URl_FOR_LEAVE_MODULE + "get_multi_compny_permission_user?";

    //********pagination data(only 20 rows per page)******
    public static String RowsPerPage = "10";

    public static String get_user_compny_permission_list = Base_URl_FOR_LEAVE_MODULE + "get_user_compny_permission_list?";


    // ************dash-board detail API *********
    public static String Get_Dashboard_detail = Base_URl_FOR_LEAVE_MODULE + "Get_Dashboard_detail?";


    //******* check login of user *****************
    public static String LoginCheck = Base_URl_FOR_LEAVE_MODULE + "LoginCheck?";

    //********* display leave balance of user ***************
    public static String Get_User_LeaveBalance = Base_URl_FOR_LEAVE_MODULE + "Get_User_LeaveBalance?";

    //************* display employee punch detail *********
    public static String Get_employee_punch_detail = Base_URl_FOR_LEAVE_MODULE + "Get_employee_punch_detail?";

    //************* display leave listing *********
    public static String Get_leave_appliation_list = Base_URl_FOR_LEAVE_MODULE + "Get_leave_appliation_list?";


    //*********** Bind type , reason and note *************
    public static String Get_leave_type_and_reason_and_note = Base_URl_FOR_LEAVE_MODULE + "Get_leave_type_and_reason_and_note?";


    // **************** Miss Punch request listing ***********************
    public static String Get_miss_punch_request_list = Base_URl_FOR_LEAVE_MODULE + "Get_miss_punch_request_list?";


    // ****************** Miss Punch Approval listing *******************
    public static String Get_miss_pucn_approve_list = Base_URl_FOR_LEAVE_MODULE + "Get_miss_pucn_approve_list?";


    // ****************** leave approval listing ************************
    public static String Get_leave_approve_list = Base_URl_FOR_LEAVE_MODULE + "Get_leave_approve_list?";

    //************ leave detail of edit and update *******************
    public static String Get_leave_detail = Base_URl_FOR_LEAVE_MODULE + "Get_leave_detail?";

    //************** approve leave application ************
    public static String employee_leave_application_approval = Base_URl_FOR_LEAVE_MODULE + "employee_leave_application_approval?";

    public static String employee_leave_application_approval_mail = Base_URl_FOR_LEAVE_MODULE + "employee_leave_application_approval_mail?";

    public static String employee_leave_application_reject = Base_URl_FOR_LEAVE_MODULE + "employee_leave_application_reject?";

    public static String employee_leave_application_reject_mail = Base_URl_FOR_LEAVE_MODULE + "employee_leave_application_reject_mail?";

    public static String employee_leave_application_mst_delete = Base_URl_FOR_LEAVE_MODULE + "employee_leave_application_mst_delete?";

    public static String Apply_Cancel_Leave_application = Base_URl_FOR_LEAVE_MODULE + "Apply_Cancel_Leave_application?";

    public static String employee_cancel_leave_application_approve_mail = Base_URl_FOR_LEAVE_MODULE + "employee_cancel_leave_application_approve_mail?";

    public static String Get_apply_cancel_leave_appliation_list = Base_URl_FOR_LEAVE_MODULE + "Get_apply_cancel_leave_appliation_list?";

    public static String employee_cancel_leave_application_approve = Base_URl_FOR_LEAVE_MODULE + "employee_cancel_leave_application_approve?";

    public static String Get_cancel_leave_appliation_approve_list = Base_URl_FOR_LEAVE_MODULE + "Get_cancel_leave_appliation_approve_list?";

    public static String Apply_Cancel_Leave_application_mail = Base_URl_FOR_LEAVE_MODULE + "Apply_Cancel_Leave_application_mail?";

    public static String employee_cancel_leave_application_reject = Base_URl_FOR_LEAVE_MODULE + "employee_cancel_leave_application_reject ?";

    public static String employee_cancel_leave_application_reject_mail = Base_URl_FOR_LEAVE_MODULE + "employee_cancel_leave_application_reject_mail?";

    public static String Employee_miss_punch_request_reject = Base_URl_FOR_LEAVE_MODULE + "Employee_miss_punch_request_reject?";

    public static String Employee_miss_punch_request_mst_update = Base_URl_FOR_LEAVE_MODULE + "Employee_miss_punch_request_mst_update?";

    public static String Employee_miss_punch_request_approved = Base_URl_FOR_LEAVE_MODULE + "Employee_miss_punch_request_approved?";

    public static String Employee_miss_punch_request_mst_delete = Base_URl_FOR_LEAVE_MODULE + "Employee_miss_punch_request_mst_delete?";

    public static String Get_miss_punch_detail = Base_URl_FOR_LEAVE_MODULE + "Get_miss_punch_detail?";

    public static String Get_Employee_Leave_Days = Base_URl_FOR_LEAVE_MODULE + "Get_Employee_Leave_Days?";

    public static String Get_Employee_Leave_balance = Base_URl_FOR_LEAVE_MODULE + "Get_Employee_Leave_balance?";

    public static String Employee_leave_application_insert = Base_URl_FOR_LEAVE_MODULE + "Employee_leave_application_insert?";

    public static String Get_employee_inout_time = Base_URl_FOR_LEAVE_MODULE + "Get_employee_inout_time?";

    public static String Get_employee_miss_puch_date_wise_inout_time = Base_URl_FOR_LEAVE_MODULE + "Get_employee_miss_puch_date_wise_inout_time?";

    public static String Employee_miss_punch_request_mst_insert = Base_URl_FOR_LEAVE_MODULE + "Employee_miss_punch_request_mst_insert?";

    //*******************for attendance detail *********************
    public static String Get_employee_attendance_report_detail = Base_URl_FOR_LEAVE_MODULE + "Get_employee_attendance_report_detail?";

    //************************* summery of attendance ***************************
    public static String Get_employee_attendance_report_summary = Base_URl_FOR_LEAVE_MODULE + "Get_employee_attendance_report_summary?";

    public static String Employee_Forgot_password = Base_URl_FOR_LEAVE_MODULE + "Employee_Forgot_password?";

    public static String Employee_Change_password = Base_URl_FOR_LEAVE_MODULE + "Employee_Change_password?";

    public static String Get_employee_salary_slip = Base_URl_FOR_LEAVE_MODULE + "Get_employee_salary_slip?";

    public static String Get_Employee_salary_slip_download = Base_URl_FOR_LEAVE_MODULE + "Get_Employee_salary_slip_download?";

    public static String compensatory_leave_approve_list = Base_URl_FOR_LEAVE_MODULE + "compensatory_leave_approve_list?";

    public static String compensatory_leave_approve_detail = Base_URl_FOR_LEAVE_MODULE + "compensatory_leave_approve_detail?";

    public static String compensatory_leave_approve = Base_URl_FOR_LEAVE_MODULE + "compensatory_leave_approve?";

    public static String Insert_mobile_tocken = Base_URl_FOR_LEAVE_MODULE + "Insert_mobile_tocken?";

    public static String Get_Today_in_out_time = Base_URl_FOR_LEAVE_MODULE + "Get_Today_in_out_time?";

//    public static String Get_app_version = Base_URl_FOR_LEAVE_MODULE + "Get_app_version?";

    public static String Get_miss_punch_hours_calculation = Base_URl_FOR_LEAVE_MODULE + "Get_miss_punch_hours_calculation?";

    public static String Get_Other_Statistics = Base_URl_FOR_LEAVE_MODULE + "Get_Other_Statistics?";


    /*pending approvals API */

    public static String Get_publication_conference_list = Base_URl_FOR_LEAVE_MODULE + "Get_publication_conference_list?";

    public static String Get_publication_in_journal_list = Base_URl_FOR_LEAVE_MODULE + "Get_publication_in_journal_list?";

    public static String Get_Book_Chapter_Publication_list = Base_URl_FOR_LEAVE_MODULE + "Get_Book_Chapter_Publication_list?";

    public static String Get_Patent_awarded_list = Base_URl_FOR_LEAVE_MODULE + "Get_Patent_awarded_list?";

    public static String Get_PG_Scholars_Guided_list = Base_URl_FOR_LEAVE_MODULE + "Get_PG_Scholars_Guided_list?";

    public static String Get_PhD_Scholars_Guided_list = Base_URl_FOR_LEAVE_MODULE + "Get_PhD_Scholars_Guided_list?";

    public static String Get_CPD_Application_list = Base_URl_FOR_LEAVE_MODULE + "Get_CPD_Application_list?";

    public static String Get_Consultancy_list = Base_URl_FOR_LEAVE_MODULE + "Get_Consultancy_list?";

    public static String Get_Grant_Received_list = Base_URl_FOR_LEAVE_MODULE + "Get_Grant_Received_list?";

    public static String Get_Seed_Money_list = Base_URl_FOR_LEAVE_MODULE + "Get_Seed_Money_list?";

    public static String Get_FDP_Attended_list = Base_URl_FOR_LEAVE_MODULE + "Get_FDP_Attended_list?";

    public static String Get_FDP_Attended_list_Document = Base_URl_FOR_LEAVE_MODULE + "Get_FDP_Attended_list_Document?";

    public static String Get_PD_Application_list = Base_URl_FOR_LEAVE_MODULE + "Get_PD_Application_list?";

    public static String Get_PD_Application_document_list = Base_URl_FOR_LEAVE_MODULE + "Get_PD_Application_document_list?";

    /*approve rejects API for approvals 12 dec 2019 nirali */

    public static String Get_publication_conference_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_publication_conference_Approved_or_Reject?";

    public static String Get_publication_in_journal_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_publication_in_journal_Approved_or_Reject?";

    public static String Get_Book_Chapter_Publication_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_Book_Chapter_Publication_Approved_or_Reject?";

    public static String Get_Patent_awarded_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_Patent_awarded_Approved_or_Reject?";

    public static String Get_PG_Scholars_Guided_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_PG_Scholars_Guided_Approved_or_Reject?";

    public static String Get_PhD_Scholars_Guided_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_PhD_Scholars_Guided_Approved_or_Reject?";

    public static String Get_CPD_Application_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_CPD_Application_Approved_or_Reject?";

    public static String Get_Consultancy_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_Consultancy_Approved_or_Reject?";

    public static String Get_Grant_Received_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_Grant_Received_Approved_or_Reject?";

    public static String Get_Seed_Money_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_Seed_Money_Approved_or_Reject?";

    public static String Get_FDP_Attended_Approved_or_Reject = Base_URl_FOR_LEAVE_MODULE + "Get_FDP_Attended_Approved_or_Reject?";

    public static String Get_open_approve_pd_window_list = Base_URl_FOR_LEAVE_MODULE + "Get_open_approve_pd_window_list?";

    public static String Get_PD_application_approve_or_reject = Base_URl_FOR_LEAVE_MODULE + "Get_PD_application_approve_or_reject?";

    public static String PD_Approved_mail = Base_URl_FOR_LEAVE_MODULE + "PD_Approved_mail?";

    public static String Get_employee_pending_approvals = Base_URl_FOR_LEAVE_MODULE + "Get_employee_pending_approvals?";

    public static String Get_app_version = Base_URl_FOR_LEAVE_MODULE + "Get_app_version?";

    //-remish varsani
    public static String Get_From_and_To_Date_For_Leave_Application = Base_URl_FOR_LEAVE_MODULE + "Get_From_and_To_Date_For_Leave_Application?";

}
