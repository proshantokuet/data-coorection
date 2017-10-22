package org.opensrp.register.mcare;

public class OpenSRPScheduleConstants {

	public static class OpenSRPEvent{
		public static final String FORM_SUBMISSION = "FORM_SUBMISSION";
	}
    public static final String PROVIDER_REPORT_SCHEDULE_SUBJECT = "PROVIDER-REPORT-SCHEDULE";
    public static final String FORM_SCHEDULE_SUBJECT = "FORM-SCHEDULE";
    public static final String MCTS_REPORT_SCHEDULE_SUBJECT = "MCTS-REPORT-SCHEDULE";
    public static final String ANM_REPORT_SCHEDULE_SUBJECT = "DRISHTI-ANM-REPORT-FETCH-SCHEDULE";

    public static class ChildScheduleConstants {
        public static final String CHILD_SCHEDULE_BCG = "BCG";

        public static final String CHILD_SCHEDULE_DPT_BOOSTER1 = "DPT Booster 1";
        public static final String CHILD_SCHEDULE_DPT_BOOSTER2 = "DPT Booster 2";

        public static final String CHILD_SCHEDULE_MEASLES = "Measles Vaccination";

        public static final String CHILD_SCHEDULE_MEASLES_BOOSTER = "Measles Booster";

        public static final String CHILD_SCHEDULE_OPV_0_AND_1 = "OPV_0_AND_1";
        public static final String CHILD_SCHEDULE_OPV_2 = "OPV 2";
        public static final String CHILD_SCHEDULE_OPV_3 = "OPV 3";
        public static final String CHILD_SCHEDULE_OPV_BOOSTER = "OPV BOOSTER";

        public static final String CHILD_SCHEDULE_PENTAVALENT_1 = "PENTAVALENT 1";
        public static final String CHILD_SCHEDULE_PENTAVALENT_2 = "PENTAVALENT 2";
        public static final String CHILD_SCHEDULE_PENTAVALENT_3 = "PENTAVALENT 3";
        
        public static final String SCHEDULE_ENCC = "Essential Newborn Care Checklist";
        public static final String SCHEDULE_ENCC_1 = "enccrv_1";
        public static final String SCHEDULE_ENCC_2 = "enccrv_2";
        public static final String SCHEDULE_ENCC_3 = "enccrv_3";
        
    }

    public static class MotherScheduleConstants {
        public static final String SCHEDULE_ANC = "Ante Natal Care Reminder Visit";
        public static final String SCHEDULE_PNC = "Post Natal Care Reminder Visit";
        public static final String SCHEDULE_ANC_MILESTONE_PREFIX = "ANC";
        public static final String SCHEDULE_ANC_1 = "ancrv_1";
        public static final String SCHEDULE_ANC_2 = "ancrv_2";
        public static final String SCHEDULE_ANC_3 = "ancrv_3";
        public static final String SCHEDULE_ANC_4 = "ancrv_4";
        public static final String SCHEDULE_BNF = "BirthNotificationPregnancyStatusFollowUp";       
        public static final String SCHEDULE_PNC_1 = "pncrv_1";
        public static final String SCHEDULE_PNC_2 = "pncrv_2";
        public static final String SCHEDULE_PNC_3 = "pncrv_3";
        public static final String SCHEDULE_EDD = "Expected Date Of Delivery";
        public static final String SCHEDULE_LAB = "Lab Reminders";
        public static final String SCHEDULE_TT_1 = "TT 1";
        public static final String SCHEDULE_TT_2 = "TT 2";
        public static final String SCHEDULE_AUTO_CLOSE_PNC = "Auto Close PNC";
        public static final String SCHEDULE_IFA_1 = "IFA 1";
        public static final String SCHEDULE_IFA_2 = "IFA 2";
        public static final String SCHEDULE_IFA_3 = "IFA 3";
        public static final String SCHEDULE_HB_TEST_1 = "Hb Test 1";
        public static final String SCHEDULE_HB_TEST_2 = "Hb Test 2";
        public static final String SCHEDULE_HB_FOLLOWUP_TEST = "Hb Followup Test";
        public static final String SCHEDULE_DELIVERY_PLAN = "Delivery Plan";
    }

    public static class ECSchedulesConstants {
        public static final String EC_SCHEDULE_DMPA_INJECTABLE_REFILL = "DMPA Injectable Refill";
        public static final String EC_SCHEDULE_DMPA_INJECTABLE_REFILL_MILESTONE = "DMPA Injectable Refill";
        public static final String EC_SCHEDULE_OCP_REFILL = "OCP Refill";
        public static final String EC_SCHEDULE_OCP_REFILL_MILESTONE = "OCP Refill";
        public static final String EC_SCHEDULE_CONDOM_REFILL = "Condom Refill";
        public static final String EC_SCHEDULE_CONDOM_REFILL_MILESTONE = "Condom Refill";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP = "Female sterilization Followup";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP_MILESTONE_1 = "Female sterilization Followup 1";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP_MILESTONE_2 = "Female sterilization Followup 2";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP_MILESTONE_3 = "Female sterilization Followup 3";
        public static final String EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP = "Male sterilization Followup";
        public static final String EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP_MILESTONE_1 = "Male sterilization Followup 1";
        public static final String EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP_MILESTONE_2 = "Male sterilization Followup 2";
        public static final String EC_SCHEDULE_FP_FOLLOWUP = "FP Followup";
        public static final String EC_SCHEDULE_FP_FOLLOWUP_MILESTONE = "FP Followup";
        public static final String EC_SCHEDULE_IUD_FOLLOWUP = "IUD Followup";
        public static final String EC_SCHEDULE_IUD_FOLLOWUP_MILESTONE_1 = "IUD Followup 1";
        public static final String EC_SCHEDULE_IUD_FOLLOWUP_MILESTONE_2 = "IUD Followup 2";
        public static final String EC_SCHEDULE_FP_REFERRAL_FOLLOWUP = "FP Referral Followup";
        public static final String EC_SCHEDULE_FP_REFERRAL_FOLLOWUP_MILESTONE = "FP Referral Followup";
    }
    public static class HHSchedulesConstants {
    	public static final String HH_SCHEDULE_CENSUS = "FW CENSUS";
    }
    public static class ELCOSchedulesConstants {
    	public static final String ELCO_SCHEDULE_PSRF = "ELCO PSRF";
    	public static final String MIS_ELCO = "mis_elco";
    }
    public static class ELCOSchedulesConstantsImediate {
    	public static final String IMD_ELCO_SCHEDULE_PSRF = "IMEDIATE ELCO PSRF";
    }
    public static class DateTimeDuration{
    	public static final Integer duration = 1344; // hour
    	public static final Integer bnf_duration = 6264;//hour
    	public static final Integer bnf_due_duration = 168;//hour
    	public static final Integer anc1 = 50; // day
    	public static final Integer anc2Start = 163;
    	public static final Integer anc2End = 168;
    	public static final Integer anc3Start = 219;    	
    	public static final Integer anc3End = 224;
    	public static final Integer anc4Start = 247;
    	public static final Integer anc4End = 252;
    	public static final Integer bnf = 261;
    }
   
}