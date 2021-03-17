package com.infinity.infoway.vimal.util.common;

import com.infinity.kich.Config.Config;

public class URLS {

    public static int TIME_TILL_DISABLE_BTN = 2000;

    /**
     * MODULE DEALER SALES ORDER
     */

    public static String get_dealer_sales_order_list = Config.MAIN_URL + "get_dealer_sales_order_list?";
    public static String Get_Project_List = Config.MAIN_URL + "Get_Project_List?";
    public static String GetTrasporter = Config.MAIN_URL + "GetTrasporter?";

    /**
     * MODULE BRANDING
     */
    public static String get_brand_list = Config.MAIN_URL + "get_brand_list?";
    public static String get_vendor_list = Config.MAIN_URL + "get_vendor_list?";
    public static String save_brand_master = Config.MAIN_URL + "save_brand_master?";


    /**
     * new Api added On 6-2-2021 by harsh
     **/
    public static String Get_All_Brand_Appointmet_For = Config.MAIN_URL + "Get_All_Brand_Appointmet_For?";
    public static String Get_All_Brand_Order_Segments = Config.MAIN_URL + "Get_All_Brand_Order_Segments?";
/**new Api added On 6-2-2021 by harsh**/

    /**
     * MODULE display
     */

    public static String get_display_list = Config.MAIN_URL + "get_display_list?";
    public static String save_display_mst = Config.MAIN_URL + "save_display_mst?";
    public static String save_multiple_files = Config.MAIN_URL + "save_multiple_files?";


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
     * MODULE STOCKINQUIRY
     */
    public static String Get_Virtual_Stock_Of_Item = Config.MAIN_URL + "Get_Virtual_Stock_Of_Item?";
    public static String get_stock_inquiry_list = Config.MAIN_URL + "get_stock_inquiry_list?";
    /**
     * MODULE SPECIAL INQUIRY
     */
    public static String get_special_inquiry_list = Config.MAIN_URL + "get_special_inquiry_list?";

    public static String save_special_inquiry = Config.MAIN_URL + "save_special_inquiry?";


    /**
     * module EVENTS
     */
    public static String get_list_of_events = Config.MAIN_URL + "get_list_of_events?";
    public static String get_list_of_event_types = Config.MAIN_URL + "get_list_of_event_types?";
    public static String get_stationary_details_from_event_type = Config.MAIN_URL + "get_stationary_details_from_event_type?";
    public static String get_list_of_display_branding = Config.MAIN_URL + "get_list_of_display_branding?";
    public static String get_list_of_gifts = Config.MAIN_URL + "get_list_of_gifts?";

    /**
     * MODULE  PUBLIC EVENTS
     */
    public static String get_list_of_public_events = Config.MAIN_URL + "get_list_of_public_events?";
    public static String get_list_of_public_event_types = Config.MAIN_URL + "get_list_of_public_event_types?";


    /**
     * MODULE LOGIN
     */
    public static String Check_Login = Config.MAIN_URL + "Check_Login?";
    public static String getFCMRegistrationID = Config.MAIN_URL + "getFCMRegistrationID?";
    public static String GetCompanyListByUserName = Config.MAIN_URL + "GetCompanyListByUserName?";
    public static String forgot_Password = Config.MAIN_URL + "forgot_Password?";


    /**
     * Module Tour Planning
     */
//public static String get_list_of_all_tour_planning_by_user_id = Config.MAIN_URL + "get_list_of_all_tour_planning_by_user_id?";
    public static String get_list_of_tour_planning_by_date_and_user_id = Config.MAIN_URL + "get_list_of_tour_planning_by_date_and_user_id?";
    public static String save_tour_planning = Config.MAIN_URL + "save_tour_planning?";


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


    /**
     * MODULE STATIONARY
     */
    public static String get_list_of_stationary = Config.MAIN_URL + "get_list_of_stationary?";

    /**
     * Module Task
     */
    public static String get_list_of_all_tasks_entered_by_user_id = Config.MAIN_URL + "get_list_of_all_tasks_entered_by_user_id?";
    public static String Get_all_type_of_work = Config.MAIN_URL + "Get_all_type_of_work?";
    public static String get_list_of_child_employees = Config.MAIN_URL + "get_list_of_child_employees?";
    public static String get_list_of_all_tasks_for_employee_by_employees_user_id_and_date = Config.MAIN_URL + "get_list_of_all_tasks_for_employee_by_employees_user_id_and_date?";


    /**
     * 08-09-2020 pragna Activity Module
     */
    public static String get_activity_type_dynamic_controlles = Config.MAIN_URL + "get_activity_type_dynamic_controlles?";
    /**
     * 27-10-2020 pragna for distributor changes in to activity module
     */
    public static String Get_All_DistributorForActivity = Config.MAIN_URL + "Get_All_DistributorForActivity?";
    public static String Get_State_list_emp_wise = Config.MAIN_URL + "Get_State_list_emp_wise?";


    /**
     * 2-03-2021 pragna for activity listing
     */
//    public static String Get_All_Activity_Detail = Config.MAIN_URL + "Get_All_Activity_Detail?";

   // public static String Get_expense_list_for_approval = Config.MAIN_URL + "Get_expense_list_for_approval?";
  //  public static String Expense_Approval = Config.MAIN_URL + "Expense_Approval?";
    public static String Activity_Approval = Config.MAIN_URL + "Activity_Approval?";
    public static String Get_All_Activity_Detail = Config.MAIN_URL + "Get_All_Activity_Detail?";
    /**
     * 3-03-2021 harsh for activity approval
     */
   // public static String Activity_Approval = Config.MAIN_URL + "Activity_Approval?";

    /*02-01-2021 pragna*/
    public static String Get_Activity_Details_For_Task = Config.MAIN_URL + "Get_Activity_Details_For_Task?";

    /**
     * 07-07-2020 pragna for leave module
     */


    // ****** testing url for demo *********
//    public static String Base_URl = "http://demo1.iipl.info/ierphr.asmx/";


//    public static String Base_URl_FOR_LEAVE_MODULE = "http://iipl.iipl.info/ierphr.asmx/";
    // public static String Base_URl_FOR_LEAVE_MODULE = "http://iipl.iipl.info/ierphr.asmx/";
    //http://27.54.165.244/API/SFKich/
//    public static String Base_URl_FOR_LEAVE_MODULE = "http://bmef.icrp.in/administrative/IerpHR.asmx/";
    //http://rku.ierp.co.in/ierphr.asmx
//    public static String Base_URl_FOR_LEAVE_MODULE = "http://27.54.165.244/administrative/IerpHR.asmx/";noooo
//    public static String Base_URl_FOR_LEAVE_MODULE = "http://27.54.165.244/ierphr.asmx/";
    public static String Base_URl_FOR_LEAVE_MODULE = Config.MAIN_URL_LEAVE;//added by remish to maintain all base url in one file


    //********** live rk url **********
//    public static String Base_URl = "http://rku.ierp.co.in/ierphr.asmx/";

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


    //Added by remish to save cold coll form
    public static String Insert_activity_type_dynamic_values = Config.MAIN_URL + "Insert_activity_type_dynamic_values?";

    /**
     * 25-09-2020 pragna for dashboard grid
     */
    public static String Get_Job_Card_Detail = Config.MAIN_URL + "Get_Job_Card_Detail?";
    public static String Get_Work_Order_Detail = Config.MAIN_URL + "Get_Work_Order_Detail?";
    public static String Get_Stock_Details = Config.MAIN_URL + "Get_Stock_Details?";
    public static String Get_Item_Outsource_Details = Config.MAIN_URL + "Get_Item_Outsource_Details?";
    public static String Get_GRN_Status_For_Raw_Material = Config.MAIN_URL + "Get_GRN_Status_For_Raw_Material?";


    public static String get_statastic_detail = Config.MAIN_URL + "get_statastic_detail?";


    //-remish varsani
    public static String Get_From_and_To_Date_For_Leave_Application = Base_URl_FOR_LEAVE_MODULE + "Get_From_and_To_Date_For_Leave_Application?";

}
