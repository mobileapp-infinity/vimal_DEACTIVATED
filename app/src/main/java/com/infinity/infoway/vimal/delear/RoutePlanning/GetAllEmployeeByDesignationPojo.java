package com.infinity.infoway.vimal.delear.RoutePlanning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllEmployeeByDesignationPojo {

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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("comp_id")
        @Expose
        private Integer compId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("create_by")
        @Expose
        private Integer createBy;
        @SerializedName("create_ip")
        @Expose
        private String createIp;
        @SerializedName("create_dnt")
        @Expose
        private String createDnt;
        @SerializedName("modify_by")
        @Expose
        private Integer modifyBy;
        @SerializedName("modify_ip")
        @Expose
        private String modifyIp;
        @SerializedName("modify_dnt")
        @Expose
        private String modifyDnt;
        @SerializedName("emp_user_name")
        @Expose
        private String empUserName;
        @SerializedName("emp_password")
        @Expose
        private Object empPassword;
        @SerializedName("emp_first_name")
        @Expose
        private String empFirstName;
        @SerializedName("emp_middle_name")
        @Expose
        private String empMiddleName;
        @SerializedName("emp_last_name")
        @Expose
        private String empLastName;
        @SerializedName("emp_gender")
        @Expose
        private Integer empGender;
        @SerializedName("emp_code")
        @Expose
        private String empCode;
        @SerializedName("emp_egm_id")
        @Expose
        private Integer empEgmId;
        @SerializedName("emp_des_id")
        @Expose
        private Integer empDesId;
        @SerializedName("emp_dep_id")
        @Expose
        private String empDepId;
        @SerializedName("emp_brm_id")
        @Expose
        private Object empBrmId;
        @SerializedName("emp_joining_Date")
        @Expose
        private String empJoiningDate;
        @SerializedName("emp_birth_Date")
        @Expose
        private String empBirthDate;
        @SerializedName("emp_qualification")
        @Expose
        private Object empQualification;
        @SerializedName("emp_email_Address")
        @Expose
        private String empEmailAddress;
        @SerializedName("emp_contact_No")
        @Expose
        private String empContactNo;
        @SerializedName("emp_blood_Group")
        @Expose
        private String empBloodGroup;
        @SerializedName("emp_address")
        @Expose
        private Object empAddress;
        @SerializedName("emp_address2")
        @Expose
        private Object empAddress2;
        @SerializedName("emp_address3")
        @Expose
        private Object empAddress3;
        @SerializedName("emp_cit_id")
        @Expose
        private Integer empCitId;
        @SerializedName("emp_tal_id")
        @Expose
        private Integer empTalId;
        @SerializedName("emp_dis_id")
        @Expose
        private Integer empDisId;
        @SerializedName("emp_pincode")
        @Expose
        private Object empPincode;
        @SerializedName("emp_sta_id")
        @Expose
        private Integer empStaId;
        @SerializedName("emp_country_id")
        @Expose
        private Integer empCountryId;
        @SerializedName("emp_check_perma_address")
        @Expose
        private Integer empCheckPermaAddress;
        @SerializedName("emp_perma_address")
        @Expose
        private Object empPermaAddress;
        @SerializedName("emp_perma_address2")
        @Expose
        private Object empPermaAddress2;
        @SerializedName("emp_perma_address3")
        @Expose
        private Object empPermaAddress3;
        @SerializedName("emp_perma_cit_id")
        @Expose
        private Integer empPermaCitId;
        @SerializedName("emp_perma_tal_id")
        @Expose
        private Integer empPermaTalId;
        @SerializedName("emp_perma_dis_id")
        @Expose
        private Integer empPermaDisId;
        @SerializedName("emp_perma_pincode")
        @Expose
        private Object empPermaPincode;
        @SerializedName("emp_perma_sta_id")
        @Expose
        private Integer empPermaStaId;
        @SerializedName("emp_perma_country_id")
        @Expose
        private Integer empPermaCountryId;
        @SerializedName("emp_photo")
        @Expose
        private Object empPhoto;
        @SerializedName("emp_signature")
        @Expose
        private String empSignature;
        @SerializedName("emp_original_photo")
        @Expose
        private Object empOriginalPhoto;
        @SerializedName("emp_original_signature")
        @Expose
        private String empOriginalSignature;
        @SerializedName("emp_sign_byte")
        @Expose
        private String empSignByte;
        @SerializedName("emp_parent_id")
        @Expose
        private Integer empParentId;
        @SerializedName("emp_user_type")
        @Expose
        private Object empUserType;
        @SerializedName("emp_operator_type")
        @Expose
        private Object empOperatorType;
        @SerializedName("emp_operator_ref_id")
        @Expose
        private Object empOperatorRefId;
        @SerializedName("emp_is_system")
        @Expose
        private Object empIsSystem;
        @SerializedName("emp_is_deleted")
        @Expose
        private Object empIsDeleted;
        @SerializedName("emp_confirmation_date")
        @Expose
        private String empConfirmationDate;
        @SerializedName("emp_resignation_date")
        @Expose
        private Object empResignationDate;
        @SerializedName("emp_is_on_probation")
        @Expose
        private Integer empIsOnProbation;
        @SerializedName("emp_probation_from_date")
        @Expose
        private Object empProbationFromDate;
        @SerializedName("emp_probation_to_date")
        @Expose
        private Object empProbationToDate;
        @SerializedName("emp_is_on_contract")
        @Expose
        private Integer empIsOnContract;
        @SerializedName("emp_contract_from_date")
        @Expose
        private Object empContractFromDate;
        @SerializedName("emp_contract_to_date")
        @Expose
        private Object empContractToDate;
        @SerializedName("emp_authorised_by")
        @Expose
        private Integer empAuthorisedBy;
        @SerializedName("emp_resignation_request_date")
        @Expose
        private Object empResignationRequestDate;
        @SerializedName("emp_resignation_authorised_by")
        @Expose
        private Object empResignationAuthorisedBy;
        @SerializedName("emp_appointment_letter_no")
        @Expose
        private String empAppointmentLetterNo;
        @SerializedName("emp_grade_id")
        @Expose
        private Object empGradeId;
        @SerializedName("emp_entity_rights")
        @Expose
        private Object empEntityRights;
        @SerializedName("emp_photo_byte")
        @Expose
        private Object empPhotoByte;
        @SerializedName("emp_iam_id")
        @Expose
        private Integer empIamId;
        @SerializedName("emp_personal_email_Address")
        @Expose
        private String empPersonalEmailAddress;
        @SerializedName("emp_weekly_off")
        @Expose
        private Object empWeeklyOff;
        @SerializedName("emp_ot_allow")
        @Expose
        private Integer empOtAllow;
        @SerializedName("emp_in_time")
        @Expose
        private String empInTime;
        @SerializedName("emp_break_out_time")
        @Expose
        private String empBreakOutTime;
        @SerializedName("emp_break_in_time")
        @Expose
        private String empBreakInTime;
        @SerializedName("emp_out_time")
        @Expose
        private String empOutTime;
        @SerializedName("emp_present_calcu_type")
        @Expose
        private Integer empPresentCalcuType;
        @SerializedName("emp_bank_name")
        @Expose
        private Object empBankName;
        @SerializedName("emp_account_no")
        @Expose
        private Object empAccountNo;
        @SerializedName("emp_holder_name")
        @Expose
        private Object empHolderName;
        @SerializedName("emp_is_protected")
        @Expose
        private Integer empIsProtected;
        @SerializedName("emp_flag")
        @Expose
        private Integer empFlag;
        @SerializedName("emp_is_shift_mst_allow")
        @Expose
        private Integer empIsShiftMstAllow;
        @SerializedName("emp_shift_id")
        @Expose
        private Integer empShiftId;
        @SerializedName("emp_in_grace")
        @Expose
        private String empInGrace;
        @SerializedName("emp_break_out_grace")
        @Expose
        private String empBreakOutGrace;
        @SerializedName("emp_break_in_grace")
        @Expose
        private String empBreakInGrace;
        @SerializedName("emp_out_grace")
        @Expose
        private String empOutGrace;
        @SerializedName("emp_salary_base")
        @Expose
        private Integer empSalaryBase;
        @SerializedName("emp_deduct_panalty")
        @Expose
        private Integer empDeductPanalty;
        @SerializedName("emp_allow_accurance")
        @Expose
        private Integer empAllowAccurance;
        @SerializedName("emp_panalty_type")
        @Expose
        private Integer empPanaltyType;
        @SerializedName("emp_amt_per_minute")
        @Expose
        private Integer empAmtPerMinute;
        @SerializedName("emp_tds_exemption")
        @Expose
        private Integer empTdsExemption;
        @SerializedName("emp_gender_type")
        @Expose
        private Integer empGenderType;
        @SerializedName("emp_pr_branch")
        @Expose
        private Integer empPrBranch;
        @SerializedName("emp_max_allowed_min")
        @Expose
        private Integer empMaxAllowedMin;
        @SerializedName("emp_half_day")
        @Expose
        private Integer empHalfDay;
        @SerializedName("emp_full_day")
        @Expose
        private Integer empFullDay;
        @SerializedName("emp_resi_reason")
        @Expose
        private Object empResiReason;
        @SerializedName("emp_resi_date")
        @Expose
        private Object empResiDate;
        @SerializedName("emp_resi_status")
        @Expose
        private Object empResiStatus;
        @SerializedName("emp_leave_template")
        @Expose
        private Integer empLeaveTemplate;
        @SerializedName("emp_bond_per")
        @Expose
        private Double empBondPer;
        @SerializedName("emp_bonus_per")
        @Expose
        private Double empBonusPer;
        @SerializedName("emp_bond_from")
        @Expose
        private String empBondFrom;
        @SerializedName("emp_bond_to")
        @Expose
        private String empBondTo;
        @SerializedName("emp_working_hrs")
        @Expose
        private Double empWorkingHrs;
        @SerializedName("emp_late_penalty_type")
        @Expose
        private Integer empLatePenaltyType;
        @SerializedName("emp_coming_typ")
        @Expose
        private Integer empComingTyp;
        @SerializedName("emp_addi_reporting_person_ids")
        @Expose
        private Object empAddiReportingPersonIds;
        @SerializedName("emp_notice_period")
        @Expose
        private Object empNoticePeriod;
        @SerializedName("emp_termination_period")
        @Expose
        private Object empTerminationPeriod;
        @SerializedName("emp_category")
        @Expose
        private Integer empCategory;
        @SerializedName("emp_marital_status")
        @Expose
        private Integer empMaritalStatus;
        @SerializedName("emp_spouse_name")
        @Expose
        private Object empSpouseName;
        @SerializedName("emp_spouse_birthdate")
        @Expose
        private String empSpouseBirthdate;
        @SerializedName("emp_children1_name")
        @Expose
        private Object empChildren1Name;
        @SerializedName("emp_children1_birthdate")
        @Expose
        private String empChildren1Birthdate;
        @SerializedName("emp_children2_name")
        @Expose
        private Object empChildren2Name;
        @SerializedName("emp_children2_birthdate")
        @Expose
        private String empChildren2Birthdate;
        @SerializedName("emp_Contract_Probation_Period_from_date")
        @Expose
        private String empContractProbationPeriodFromDate;
        @SerializedName("emp_Contract_Probation_Period_to_date")
        @Expose
        private String empContractProbationPeriodToDate;
        @SerializedName("emp_holiday")
        @Expose
        private Integer empHoliday;
        @SerializedName("emp_full_day_hrs")
        @Expose
        private Double empFullDayHrs;
        @SerializedName("emp_half_day_hrs")
        @Expose
        private Double empHalfDayHrs;
        @SerializedName("emp_is_sale_app_use")
        @Expose
        private Integer empIsSaleAppUse;
        @SerializedName("emp_base_currency")
        @Expose
        private Integer empBaseCurrency;
        @SerializedName("emp_grp_id")
        @Expose
        private Integer empGrpId;
        @SerializedName("id1")
        @Expose
        private Integer id1;
        @SerializedName("comp_id1")
        @Expose
        private Integer compId1;
        @SerializedName("status1")
        @Expose
        private Integer status1;
        @SerializedName("create_by1")
        @Expose
        private Integer createBy1;
        @SerializedName("create_ip1")
        @Expose
        private String createIp1;
        @SerializedName("create_dnt1")
        @Expose
        private String createDnt1;
        @SerializedName("modify_by1")
        @Expose
        private Object modifyBy1;
        @SerializedName("modify_ip1")
        @Expose
        private Object modifyIp1;
        @SerializedName("modify_dnt1")
        @Expose
        private Object modifyDnt1;
        @SerializedName("des_name")
        @Expose
        private String desName;
        @SerializedName("des_parent_id")
        @Expose
        private Integer desParentId;
        @SerializedName("des_brm_id")
        @Expose
        private String desBrmId;
        @SerializedName("des_type")
        @Expose
        private Integer desType;
        @SerializedName("des_key")
        @Expose
        private String desKey;
        @SerializedName("des_sort_order")
        @Expose
        private Integer desSortOrder;
        @SerializedName("des_is_exclude")
        @Expose
        private Integer desIsExclude;
        @SerializedName("des_budget")
        @Expose
        private Integer desBudget;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCompId() {
            return compId;
        }

        public void setCompId(Integer compId) {
            this.compId = compId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public String getCreateIp() {
            return createIp;
        }

        public void setCreateIp(String createIp) {
            this.createIp = createIp;
        }

        public String getCreateDnt() {
            return createDnt;
        }

        public void setCreateDnt(String createDnt) {
            this.createDnt = createDnt;
        }

        public Integer getModifyBy() {
            return modifyBy;
        }

        public void setModifyBy(Integer modifyBy) {
            this.modifyBy = modifyBy;
        }

        public String getModifyIp() {
            return modifyIp;
        }

        public void setModifyIp(String modifyIp) {
            this.modifyIp = modifyIp;
        }

        public String getModifyDnt() {
            return modifyDnt;
        }

        public void setModifyDnt(String modifyDnt) {
            this.modifyDnt = modifyDnt;
        }

        public String getEmpUserName() {
            return empUserName;
        }

        public void setEmpUserName(String empUserName) {
            this.empUserName = empUserName;
        }

        public Object getEmpPassword() {
            return empPassword;
        }

        public void setEmpPassword(Object empPassword) {
            this.empPassword = empPassword;
        }

        public String getEmpFirstName() {
            return empFirstName;
        }

        public void setEmpFirstName(String empFirstName) {
            this.empFirstName = empFirstName;
        }

        public String getEmpMiddleName() {
            return empMiddleName;
        }

        public void setEmpMiddleName(String empMiddleName) {
            this.empMiddleName = empMiddleName;
        }

        public String getEmpLastName() {
            return empLastName;
        }

        public void setEmpLastName(String empLastName) {
            this.empLastName = empLastName;
        }

        public Integer getEmpGender() {
            return empGender;
        }

        public void setEmpGender(Integer empGender) {
            this.empGender = empGender;
        }

        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        public Integer getEmpEgmId() {
            return empEgmId;
        }

        public void setEmpEgmId(Integer empEgmId) {
            this.empEgmId = empEgmId;
        }

        public Integer getEmpDesId() {
            return empDesId;
        }

        public void setEmpDesId(Integer empDesId) {
            this.empDesId = empDesId;
        }

        public String getEmpDepId() {
            return empDepId;
        }

        public void setEmpDepId(String empDepId) {
            this.empDepId = empDepId;
        }

        public Object getEmpBrmId() {
            return empBrmId;
        }

        public void setEmpBrmId(Object empBrmId) {
            this.empBrmId = empBrmId;
        }

        public String getEmpJoiningDate() {
            return empJoiningDate;
        }

        public void setEmpJoiningDate(String empJoiningDate) {
            this.empJoiningDate = empJoiningDate;
        }

        public String getEmpBirthDate() {
            return empBirthDate;
        }

        public void setEmpBirthDate(String empBirthDate) {
            this.empBirthDate = empBirthDate;
        }

        public Object getEmpQualification() {
            return empQualification;
        }

        public void setEmpQualification(Object empQualification) {
            this.empQualification = empQualification;
        }

        public String getEmpEmailAddress() {
            return empEmailAddress;
        }

        public void setEmpEmailAddress(String empEmailAddress) {
            this.empEmailAddress = empEmailAddress;
        }

        public String getEmpContactNo() {
            return empContactNo;
        }

        public void setEmpContactNo(String empContactNo) {
            this.empContactNo = empContactNo;
        }

        public String getEmpBloodGroup() {
            return empBloodGroup;
        }

        public void setEmpBloodGroup(String empBloodGroup) {
            this.empBloodGroup = empBloodGroup;
        }

        public Object getEmpAddress() {
            return empAddress;
        }

        public void setEmpAddress(Object empAddress) {
            this.empAddress = empAddress;
        }

        public Object getEmpAddress2() {
            return empAddress2;
        }

        public void setEmpAddress2(Object empAddress2) {
            this.empAddress2 = empAddress2;
        }

        public Object getEmpAddress3() {
            return empAddress3;
        }

        public void setEmpAddress3(Object empAddress3) {
            this.empAddress3 = empAddress3;
        }

        public Integer getEmpCitId() {
            return empCitId;
        }

        public void setEmpCitId(Integer empCitId) {
            this.empCitId = empCitId;
        }

        public Integer getEmpTalId() {
            return empTalId;
        }

        public void setEmpTalId(Integer empTalId) {
            this.empTalId = empTalId;
        }

        public Integer getEmpDisId() {
            return empDisId;
        }

        public void setEmpDisId(Integer empDisId) {
            this.empDisId = empDisId;
        }

        public Object getEmpPincode() {
            return empPincode;
        }

        public void setEmpPincode(Object empPincode) {
            this.empPincode = empPincode;
        }

        public Integer getEmpStaId() {
            return empStaId;
        }

        public void setEmpStaId(Integer empStaId) {
            this.empStaId = empStaId;
        }

        public Integer getEmpCountryId() {
            return empCountryId;
        }

        public void setEmpCountryId(Integer empCountryId) {
            this.empCountryId = empCountryId;
        }

        public Integer getEmpCheckPermaAddress() {
            return empCheckPermaAddress;
        }

        public void setEmpCheckPermaAddress(Integer empCheckPermaAddress) {
            this.empCheckPermaAddress = empCheckPermaAddress;
        }

        public Object getEmpPermaAddress() {
            return empPermaAddress;
        }

        public void setEmpPermaAddress(Object empPermaAddress) {
            this.empPermaAddress = empPermaAddress;
        }

        public Object getEmpPermaAddress2() {
            return empPermaAddress2;
        }

        public void setEmpPermaAddress2(Object empPermaAddress2) {
            this.empPermaAddress2 = empPermaAddress2;
        }

        public Object getEmpPermaAddress3() {
            return empPermaAddress3;
        }

        public void setEmpPermaAddress3(Object empPermaAddress3) {
            this.empPermaAddress3 = empPermaAddress3;
        }

        public Integer getEmpPermaCitId() {
            return empPermaCitId;
        }

        public void setEmpPermaCitId(Integer empPermaCitId) {
            this.empPermaCitId = empPermaCitId;
        }

        public Integer getEmpPermaTalId() {
            return empPermaTalId;
        }

        public void setEmpPermaTalId(Integer empPermaTalId) {
            this.empPermaTalId = empPermaTalId;
        }

        public Integer getEmpPermaDisId() {
            return empPermaDisId;
        }

        public void setEmpPermaDisId(Integer empPermaDisId) {
            this.empPermaDisId = empPermaDisId;
        }

        public Object getEmpPermaPincode() {
            return empPermaPincode;
        }

        public void setEmpPermaPincode(Object empPermaPincode) {
            this.empPermaPincode = empPermaPincode;
        }

        public Integer getEmpPermaStaId() {
            return empPermaStaId;
        }

        public void setEmpPermaStaId(Integer empPermaStaId) {
            this.empPermaStaId = empPermaStaId;
        }

        public Integer getEmpPermaCountryId() {
            return empPermaCountryId;
        }

        public void setEmpPermaCountryId(Integer empPermaCountryId) {
            this.empPermaCountryId = empPermaCountryId;
        }

        public Object getEmpPhoto() {
            return empPhoto;
        }

        public void setEmpPhoto(Object empPhoto) {
            this.empPhoto = empPhoto;
        }

        public String getEmpSignature() {
            return empSignature;
        }

        public void setEmpSignature(String empSignature) {
            this.empSignature = empSignature;
        }

        public Object getEmpOriginalPhoto() {
            return empOriginalPhoto;
        }

        public void setEmpOriginalPhoto(Object empOriginalPhoto) {
            this.empOriginalPhoto = empOriginalPhoto;
        }

        public String getEmpOriginalSignature() {
            return empOriginalSignature;
        }

        public void setEmpOriginalSignature(String empOriginalSignature) {
            this.empOriginalSignature = empOriginalSignature;
        }

        public String getEmpSignByte() {
            return empSignByte;
        }

        public void setEmpSignByte(String empSignByte) {
            this.empSignByte = empSignByte;
        }

        public Integer getEmpParentId() {
            return empParentId;
        }

        public void setEmpParentId(Integer empParentId) {
            this.empParentId = empParentId;
        }

        public Object getEmpUserType() {
            return empUserType;
        }

        public void setEmpUserType(Object empUserType) {
            this.empUserType = empUserType;
        }

        public Object getEmpOperatorType() {
            return empOperatorType;
        }

        public void setEmpOperatorType(Object empOperatorType) {
            this.empOperatorType = empOperatorType;
        }

        public Object getEmpOperatorRefId() {
            return empOperatorRefId;
        }

        public void setEmpOperatorRefId(Object empOperatorRefId) {
            this.empOperatorRefId = empOperatorRefId;
        }

        public Object getEmpIsSystem() {
            return empIsSystem;
        }

        public void setEmpIsSystem(Object empIsSystem) {
            this.empIsSystem = empIsSystem;
        }

        public Object getEmpIsDeleted() {
            return empIsDeleted;
        }

        public void setEmpIsDeleted(Object empIsDeleted) {
            this.empIsDeleted = empIsDeleted;
        }

        public String getEmpConfirmationDate() {
            return empConfirmationDate;
        }

        public void setEmpConfirmationDate(String empConfirmationDate) {
            this.empConfirmationDate = empConfirmationDate;
        }

        public Object getEmpResignationDate() {
            return empResignationDate;
        }

        public void setEmpResignationDate(Object empResignationDate) {
            this.empResignationDate = empResignationDate;
        }

        public Integer getEmpIsOnProbation() {
            return empIsOnProbation;
        }

        public void setEmpIsOnProbation(Integer empIsOnProbation) {
            this.empIsOnProbation = empIsOnProbation;
        }

        public Object getEmpProbationFromDate() {
            return empProbationFromDate;
        }

        public void setEmpProbationFromDate(Object empProbationFromDate) {
            this.empProbationFromDate = empProbationFromDate;
        }

        public Object getEmpProbationToDate() {
            return empProbationToDate;
        }

        public void setEmpProbationToDate(Object empProbationToDate) {
            this.empProbationToDate = empProbationToDate;
        }

        public Integer getEmpIsOnContract() {
            return empIsOnContract;
        }

        public void setEmpIsOnContract(Integer empIsOnContract) {
            this.empIsOnContract = empIsOnContract;
        }

        public Object getEmpContractFromDate() {
            return empContractFromDate;
        }

        public void setEmpContractFromDate(Object empContractFromDate) {
            this.empContractFromDate = empContractFromDate;
        }

        public Object getEmpContractToDate() {
            return empContractToDate;
        }

        public void setEmpContractToDate(Object empContractToDate) {
            this.empContractToDate = empContractToDate;
        }

        public Integer getEmpAuthorisedBy() {
            return empAuthorisedBy;
        }

        public void setEmpAuthorisedBy(Integer empAuthorisedBy) {
            this.empAuthorisedBy = empAuthorisedBy;
        }

        public Object getEmpResignationRequestDate() {
            return empResignationRequestDate;
        }

        public void setEmpResignationRequestDate(Object empResignationRequestDate) {
            this.empResignationRequestDate = empResignationRequestDate;
        }

        public Object getEmpResignationAuthorisedBy() {
            return empResignationAuthorisedBy;
        }

        public void setEmpResignationAuthorisedBy(Object empResignationAuthorisedBy) {
            this.empResignationAuthorisedBy = empResignationAuthorisedBy;
        }

        public String getEmpAppointmentLetterNo() {
            return empAppointmentLetterNo;
        }

        public void setEmpAppointmentLetterNo(String empAppointmentLetterNo) {
            this.empAppointmentLetterNo = empAppointmentLetterNo;
        }

        public Object getEmpGradeId() {
            return empGradeId;
        }

        public void setEmpGradeId(Object empGradeId) {
            this.empGradeId = empGradeId;
        }

        public Object getEmpEntityRights() {
            return empEntityRights;
        }

        public void setEmpEntityRights(Object empEntityRights) {
            this.empEntityRights = empEntityRights;
        }

        public Object getEmpPhotoByte() {
            return empPhotoByte;
        }

        public void setEmpPhotoByte(Object empPhotoByte) {
            this.empPhotoByte = empPhotoByte;
        }

        public Integer getEmpIamId() {
            return empIamId;
        }

        public void setEmpIamId(Integer empIamId) {
            this.empIamId = empIamId;
        }

        public String getEmpPersonalEmailAddress() {
            return empPersonalEmailAddress;
        }

        public void setEmpPersonalEmailAddress(String empPersonalEmailAddress) {
            this.empPersonalEmailAddress = empPersonalEmailAddress;
        }

        public Object getEmpWeeklyOff() {
            return empWeeklyOff;
        }

        public void setEmpWeeklyOff(Object empWeeklyOff) {
            this.empWeeklyOff = empWeeklyOff;
        }

        public Integer getEmpOtAllow() {
            return empOtAllow;
        }

        public void setEmpOtAllow(Integer empOtAllow) {
            this.empOtAllow = empOtAllow;
        }

        public String getEmpInTime() {
            return empInTime;
        }

        public void setEmpInTime(String empInTime) {
            this.empInTime = empInTime;
        }

        public String getEmpBreakOutTime() {
            return empBreakOutTime;
        }

        public void setEmpBreakOutTime(String empBreakOutTime) {
            this.empBreakOutTime = empBreakOutTime;
        }

        public String getEmpBreakInTime() {
            return empBreakInTime;
        }

        public void setEmpBreakInTime(String empBreakInTime) {
            this.empBreakInTime = empBreakInTime;
        }

        public String getEmpOutTime() {
            return empOutTime;
        }

        public void setEmpOutTime(String empOutTime) {
            this.empOutTime = empOutTime;
        }

        public Integer getEmpPresentCalcuType() {
            return empPresentCalcuType;
        }

        public void setEmpPresentCalcuType(Integer empPresentCalcuType) {
            this.empPresentCalcuType = empPresentCalcuType;
        }

        public Object getEmpBankName() {
            return empBankName;
        }

        public void setEmpBankName(Object empBankName) {
            this.empBankName = empBankName;
        }

        public Object getEmpAccountNo() {
            return empAccountNo;
        }

        public void setEmpAccountNo(Object empAccountNo) {
            this.empAccountNo = empAccountNo;
        }

        public Object getEmpHolderName() {
            return empHolderName;
        }

        public void setEmpHolderName(Object empHolderName) {
            this.empHolderName = empHolderName;
        }

        public Integer getEmpIsProtected() {
            return empIsProtected;
        }

        public void setEmpIsProtected(Integer empIsProtected) {
            this.empIsProtected = empIsProtected;
        }

        public Integer getEmpFlag() {
            return empFlag;
        }

        public void setEmpFlag(Integer empFlag) {
            this.empFlag = empFlag;
        }

        public Integer getEmpIsShiftMstAllow() {
            return empIsShiftMstAllow;
        }

        public void setEmpIsShiftMstAllow(Integer empIsShiftMstAllow) {
            this.empIsShiftMstAllow = empIsShiftMstAllow;
        }

        public Integer getEmpShiftId() {
            return empShiftId;
        }

        public void setEmpShiftId(Integer empShiftId) {
            this.empShiftId = empShiftId;
        }

        public String getEmpInGrace() {
            return empInGrace;
        }

        public void setEmpInGrace(String empInGrace) {
            this.empInGrace = empInGrace;
        }

        public String getEmpBreakOutGrace() {
            return empBreakOutGrace;
        }

        public void setEmpBreakOutGrace(String empBreakOutGrace) {
            this.empBreakOutGrace = empBreakOutGrace;
        }

        public String getEmpBreakInGrace() {
            return empBreakInGrace;
        }

        public void setEmpBreakInGrace(String empBreakInGrace) {
            this.empBreakInGrace = empBreakInGrace;
        }

        public String getEmpOutGrace() {
            return empOutGrace;
        }

        public void setEmpOutGrace(String empOutGrace) {
            this.empOutGrace = empOutGrace;
        }

        public Integer getEmpSalaryBase() {
            return empSalaryBase;
        }

        public void setEmpSalaryBase(Integer empSalaryBase) {
            this.empSalaryBase = empSalaryBase;
        }

        public Integer getEmpDeductPanalty() {
            return empDeductPanalty;
        }

        public void setEmpDeductPanalty(Integer empDeductPanalty) {
            this.empDeductPanalty = empDeductPanalty;
        }

        public Integer getEmpAllowAccurance() {
            return empAllowAccurance;
        }

        public void setEmpAllowAccurance(Integer empAllowAccurance) {
            this.empAllowAccurance = empAllowAccurance;
        }

        public Integer getEmpPanaltyType() {
            return empPanaltyType;
        }

        public void setEmpPanaltyType(Integer empPanaltyType) {
            this.empPanaltyType = empPanaltyType;
        }

        public Integer getEmpAmtPerMinute() {
            return empAmtPerMinute;
        }

        public void setEmpAmtPerMinute(Integer empAmtPerMinute) {
            this.empAmtPerMinute = empAmtPerMinute;
        }

        public Integer getEmpTdsExemption() {
            return empTdsExemption;
        }

        public void setEmpTdsExemption(Integer empTdsExemption) {
            this.empTdsExemption = empTdsExemption;
        }

        public Integer getEmpGenderType() {
            return empGenderType;
        }

        public void setEmpGenderType(Integer empGenderType) {
            this.empGenderType = empGenderType;
        }

        public Integer getEmpPrBranch() {
            return empPrBranch;
        }

        public void setEmpPrBranch(Integer empPrBranch) {
            this.empPrBranch = empPrBranch;
        }

        public Integer getEmpMaxAllowedMin() {
            return empMaxAllowedMin;
        }

        public void setEmpMaxAllowedMin(Integer empMaxAllowedMin) {
            this.empMaxAllowedMin = empMaxAllowedMin;
        }

        public Integer getEmpHalfDay() {
            return empHalfDay;
        }

        public void setEmpHalfDay(Integer empHalfDay) {
            this.empHalfDay = empHalfDay;
        }

        public Integer getEmpFullDay() {
            return empFullDay;
        }

        public void setEmpFullDay(Integer empFullDay) {
            this.empFullDay = empFullDay;
        }

        public Object getEmpResiReason() {
            return empResiReason;
        }

        public void setEmpResiReason(Object empResiReason) {
            this.empResiReason = empResiReason;
        }

        public Object getEmpResiDate() {
            return empResiDate;
        }

        public void setEmpResiDate(Object empResiDate) {
            this.empResiDate = empResiDate;
        }

        public Object getEmpResiStatus() {
            return empResiStatus;
        }

        public void setEmpResiStatus(Object empResiStatus) {
            this.empResiStatus = empResiStatus;
        }

        public Integer getEmpLeaveTemplate() {
            return empLeaveTemplate;
        }

        public void setEmpLeaveTemplate(Integer empLeaveTemplate) {
            this.empLeaveTemplate = empLeaveTemplate;
        }

        public Double getEmpBondPer() {
            return empBondPer;
        }

        public void setEmpBondPer(Double empBondPer) {
            this.empBondPer = empBondPer;
        }

        public Double getEmpBonusPer() {
            return empBonusPer;
        }

        public void setEmpBonusPer(Double empBonusPer) {
            this.empBonusPer = empBonusPer;
        }

        public String getEmpBondFrom() {
            return empBondFrom;
        }

        public void setEmpBondFrom(String empBondFrom) {
            this.empBondFrom = empBondFrom;
        }

        public String getEmpBondTo() {
            return empBondTo;
        }

        public void setEmpBondTo(String empBondTo) {
            this.empBondTo = empBondTo;
        }

        public Double getEmpWorkingHrs() {
            return empWorkingHrs;
        }

        public void setEmpWorkingHrs(Double empWorkingHrs) {
            this.empWorkingHrs = empWorkingHrs;
        }

        public Integer getEmpLatePenaltyType() {
            return empLatePenaltyType;
        }

        public void setEmpLatePenaltyType(Integer empLatePenaltyType) {
            this.empLatePenaltyType = empLatePenaltyType;
        }

        public Integer getEmpComingTyp() {
            return empComingTyp;
        }

        public void setEmpComingTyp(Integer empComingTyp) {
            this.empComingTyp = empComingTyp;
        }

        public Object getEmpAddiReportingPersonIds() {
            return empAddiReportingPersonIds;
        }

        public void setEmpAddiReportingPersonIds(Object empAddiReportingPersonIds) {
            this.empAddiReportingPersonIds = empAddiReportingPersonIds;
        }

        public Object getEmpNoticePeriod() {
            return empNoticePeriod;
        }

        public void setEmpNoticePeriod(Object empNoticePeriod) {
            this.empNoticePeriod = empNoticePeriod;
        }

        public Object getEmpTerminationPeriod() {
            return empTerminationPeriod;
        }

        public void setEmpTerminationPeriod(Object empTerminationPeriod) {
            this.empTerminationPeriod = empTerminationPeriod;
        }

        public Integer getEmpCategory() {
            return empCategory;
        }

        public void setEmpCategory(Integer empCategory) {
            this.empCategory = empCategory;
        }

        public Integer getEmpMaritalStatus() {
            return empMaritalStatus;
        }

        public void setEmpMaritalStatus(Integer empMaritalStatus) {
            this.empMaritalStatus = empMaritalStatus;
        }

        public Object getEmpSpouseName() {
            return empSpouseName;
        }

        public void setEmpSpouseName(Object empSpouseName) {
            this.empSpouseName = empSpouseName;
        }

        public String getEmpSpouseBirthdate() {
            return empSpouseBirthdate;
        }

        public void setEmpSpouseBirthdate(String empSpouseBirthdate) {
            this.empSpouseBirthdate = empSpouseBirthdate;
        }

        public Object getEmpChildren1Name() {
            return empChildren1Name;
        }

        public void setEmpChildren1Name(Object empChildren1Name) {
            this.empChildren1Name = empChildren1Name;
        }

        public String getEmpChildren1Birthdate() {
            return empChildren1Birthdate;
        }

        public void setEmpChildren1Birthdate(String empChildren1Birthdate) {
            this.empChildren1Birthdate = empChildren1Birthdate;
        }

        public Object getEmpChildren2Name() {
            return empChildren2Name;
        }

        public void setEmpChildren2Name(Object empChildren2Name) {
            this.empChildren2Name = empChildren2Name;
        }

        public String getEmpChildren2Birthdate() {
            return empChildren2Birthdate;
        }

        public void setEmpChildren2Birthdate(String empChildren2Birthdate) {
            this.empChildren2Birthdate = empChildren2Birthdate;
        }

        public String getEmpContractProbationPeriodFromDate() {
            return empContractProbationPeriodFromDate;
        }

        public void setEmpContractProbationPeriodFromDate(String empContractProbationPeriodFromDate) {
            this.empContractProbationPeriodFromDate = empContractProbationPeriodFromDate;
        }

        public String getEmpContractProbationPeriodToDate() {
            return empContractProbationPeriodToDate;
        }

        public void setEmpContractProbationPeriodToDate(String empContractProbationPeriodToDate) {
            this.empContractProbationPeriodToDate = empContractProbationPeriodToDate;
        }

        public Integer getEmpHoliday() {
            return empHoliday;
        }

        public void setEmpHoliday(Integer empHoliday) {
            this.empHoliday = empHoliday;
        }

        public Double getEmpFullDayHrs() {
            return empFullDayHrs;
        }

        public void setEmpFullDayHrs(Double empFullDayHrs) {
            this.empFullDayHrs = empFullDayHrs;
        }

        public Double getEmpHalfDayHrs() {
            return empHalfDayHrs;
        }

        public void setEmpHalfDayHrs(Double empHalfDayHrs) {
            this.empHalfDayHrs = empHalfDayHrs;
        }

        public Integer getEmpIsSaleAppUse() {
            return empIsSaleAppUse;
        }

        public void setEmpIsSaleAppUse(Integer empIsSaleAppUse) {
            this.empIsSaleAppUse = empIsSaleAppUse;
        }

        public Integer getEmpBaseCurrency() {
            return empBaseCurrency;
        }

        public void setEmpBaseCurrency(Integer empBaseCurrency) {
            this.empBaseCurrency = empBaseCurrency;
        }

        public Integer getEmpGrpId() {
            return empGrpId;
        }

        public void setEmpGrpId(Integer empGrpId) {
            this.empGrpId = empGrpId;
        }

        public Integer getId1() {
            return id1;
        }

        public void setId1(Integer id1) {
            this.id1 = id1;
        }

        public Integer getCompId1() {
            return compId1;
        }

        public void setCompId1(Integer compId1) {
            this.compId1 = compId1;
        }

        public Integer getStatus1() {
            return status1;
        }

        public void setStatus1(Integer status1) {
            this.status1 = status1;
        }

        public Integer getCreateBy1() {
            return createBy1;
        }

        public void setCreateBy1(Integer createBy1) {
            this.createBy1 = createBy1;
        }

        public String getCreateIp1() {
            return createIp1;
        }

        public void setCreateIp1(String createIp1) {
            this.createIp1 = createIp1;
        }

        public String getCreateDnt1() {
            return createDnt1;
        }

        public void setCreateDnt1(String createDnt1) {
            this.createDnt1 = createDnt1;
        }

        public Object getModifyBy1() {
            return modifyBy1;
        }

        public void setModifyBy1(Object modifyBy1) {
            this.modifyBy1 = modifyBy1;
        }

        public Object getModifyIp1() {
            return modifyIp1;
        }

        public void setModifyIp1(Object modifyIp1) {
            this.modifyIp1 = modifyIp1;
        }

        public Object getModifyDnt1() {
            return modifyDnt1;
        }

        public void setModifyDnt1(Object modifyDnt1) {
            this.modifyDnt1 = modifyDnt1;
        }

        public String getDesName() {
            return desName;
        }

        public void setDesName(String desName) {
            this.desName = desName;
        }

        public Integer getDesParentId() {
            return desParentId;
        }

        public void setDesParentId(Integer desParentId) {
            this.desParentId = desParentId;
        }

        public String getDesBrmId() {
            return desBrmId;
        }

        public void setDesBrmId(String desBrmId) {
            this.desBrmId = desBrmId;
        }

        public Integer getDesType() {
            return desType;
        }

        public void setDesType(Integer desType) {
            this.desType = desType;
        }

        public String getDesKey() {
            return desKey;
        }

        public void setDesKey(String desKey) {
            this.desKey = desKey;
        }

        public Integer getDesSortOrder() {
            return desSortOrder;
        }

        public void setDesSortOrder(Integer desSortOrder) {
            this.desSortOrder = desSortOrder;
        }

        public Integer getDesIsExclude() {
            return desIsExclude;
        }

        public void setDesIsExclude(Integer desIsExclude) {
            this.desIsExclude = desIsExclude;
        }

        public Integer getDesBudget() {
            return desBudget;
        }

        public void setDesBudget(Integer desBudget) {
            this.desBudget = desBudget;
        }
    }

}



