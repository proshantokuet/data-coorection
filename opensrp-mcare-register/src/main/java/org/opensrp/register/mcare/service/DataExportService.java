package org.opensrp.register.mcare.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.ektorp.ViewResult;
import org.opensrp.common.AllConstants.ExportConstant;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Exports;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllExports;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.ScheduleLog;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.repository.AllReportActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataExportService {

	@Autowired
	private AllExports allExports;
	private AllHouseHolds allHouseHolds;
	private AllElcos allElcos;
	private String multimediaDirPath;
	private AllActions allActions;
	private AllMothers allMothers;
	private AllChilds allChilds;
	private AllReportActions allReportActions;

	public DataExportService() {

	}

	@Autowired
	public DataExportService(
			@Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName,
			AllHouseHolds allHouseHolds, AllElcos allElcos,
			AllActions allActions, AllMothers allMothers, AllChilds allChilds,
			AllReportActions allReportActions) {
		this.multimediaDirPath = multimediaDirName;
		this.allHouseHolds = allHouseHolds;
		this.allElcos = allElcos;
		this.allActions = allActions;
		this.allMothers = allMothers;
		this.allChilds = allChilds;
		this.allReportActions = allReportActions;
	}

	public String exports(HttpServletResponse response, String formName,
			String start_date, String end_date, String user) {

		String reportName = formName + "_" + System.currentTimeMillis()
				+ ".csv";
		Exports export = new Exports();
		export.setFormName(formName);
		export.setUser(user);
		Date date = new Date();
		String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		export.setCreated_date(modifiedDate);
		;
		export.setReportName(reportName);

		export.setTimeStamp(System.currentTimeMillis());

		if (formName.equalsIgnoreCase("NEW_HOUSEHOLD_FORM")) {
			createHHCSV(response, formName, start_date, end_date, reportName);
		} else if (formName.equalsIgnoreCase("CENSUS_FORM")) {
			createCensusCSV(response, formName, start_date, end_date,
					reportName);
		} else if (formName.equalsIgnoreCase("PSRF_FORM")) {
			createPsrfCSV(response, formName, start_date, end_date, reportName);
		} else if (formName.equalsIgnoreCase("MIS_CENSUS_FORM")) {
			createMisCensusCSV(response, formName, start_date, end_date,
					reportName);
		} else if (formName.equalsIgnoreCase("MIS_ELCO_FORM")) {
			createMisElcoCSV(response, formName, start_date, end_date,
					reportName);
		} else if (formName.equalsIgnoreCase("ANCSUBMIT")) {
			System.err.println("Come .............");
			String schedule = "Ante Natal Care Reminder Visit";
			boolean isAcitve = false;
			createANCRV(response, schedule, start_date, end_date, reportName,
					isAcitve);
		} else if (formName.equalsIgnoreCase("ANCNOTSUBMIT")) {
			String schedule = "Ante Natal Care Reminder Visit";
			boolean isAcitve = true;
			createANCRVNotSubmit(response, schedule, start_date, end_date,
					reportName, isAcitve);
		} else if (formName.equalsIgnoreCase("PNCSUBMIT")) {
			String schedule = "Post Natal Care Reminder Visit";
			boolean isAcitve = false;
			createPNC(response, schedule, start_date, end_date, reportName,
					isAcitve);
		} else if (formName.equalsIgnoreCase("PNCNOTSUBMIT")) {
			String schedule = "Post Natal Care Reminder Visit";
			boolean isAcitve = true;
			createPNCNotSubmit(response, schedule, start_date, end_date,
					reportName, isAcitve);
		} else if (formName.equalsIgnoreCase("ENCCSUBMIT")) {
			createENCCCSV(response, start_date, end_date, reportName);
		} else if (formName.equalsIgnoreCase("ENCCNOTSUBMIT")) {
			String schedule = "Essential Newborn Care Checklist";
			boolean isAcitve = true;
			createENCCNotSubmit(response, schedule, start_date, end_date,
					reportName, isAcitve);
		} else {

		}
		;
		allExports.add(export);

		return reportName;
	}

	public void createENCCNotSubmit(HttpServletResponse response,
			String schedule, String start_date, String end_date,
			String reportName, boolean isAcitve) {
		List<Action> actions = allActions.findByScheduleIsActivenAndTimeStamp(
				schedule, isAcitve, convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Beneficiary Name");
			writer.append(',');// 1
			writer.append("Identifier");
			writer.append(',');// 1
			writer.append("Schedule Status");
			writer.append(',');// 4
			writer.append("FWA Name");
			writer.append(',');// 5
			writer.append("Upazilla");
			writer.append(',');// 6
			writer.append("Union");
			writer.append(',');// 7
			writer.append("Unit");
			writer.append(',');// 8
			writer.append("Mouza Para");
			writer.append(',');// 9
			writer.append("Name of ENCC Visit");
			writer.append(',');// 9
			writer.append("Expired Date");
			writer.append('\n'); // 22

			for (Action action : actions) {
				Child child = allChilds.findByCaseId(action.caseId());
				String visitCode = action.data().get("visitCode");
				if (child != null) {

					try {
						if (child.details().get("FWBNFCHILDNAME") != null) {
							writer.append(child.details().get("FWBNFCHILDNAME"));
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						writer.append("");
						writer.append(',');
						writer.append(action.data().get("alertStatus"));
						writer.append(',');
						writer.append(child.PROVIDERID());
						writer.append(',');
						if (child.getUpazilla() != null) {
							writer.append(child.getUpazilla());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						if (child.getUnion() != null) {
							writer.append(child.getUnion());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (child.getUnit() != null) {
							writer.append(child.getUnit());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						if (child.getMouzaPara() != null) {
							writer.append(child.getMouzaPara());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						writer.append(visitCode);
						writer.append(',');
						writer.append(action.data().get("expiryDate"));

						writer.append('\n');
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (child != null){
				for (int i = 1; i < getLimit(visitCode); i++) {// check previous
																// encc
					Map<String, String> encc = getENCCNumber(child, i);
					ScheduleLog scheduleLog = allReportActions
							.findByyCaseIdByname(child.caseId(), action.data()
									.get("scheduleName"));

					if ((child != null)
							&& encc.isEmpty()
							&& isScheduleCreated(scheduleLog,
									getScheduleMilestone(i))) {

						try {
							if (child.details().get("FWBNFCHILDNAME") != null) {
								writer.append(child.details().get(
										"FWBNFCHILDNAME"));
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}
							writer.append("");
							writer.append(',');
							writer.append("expired");
							writer.append(',');
							writer.append(child.PROVIDERID());
							writer.append(',');
							if (child.getUpazilla() != null) {
								writer.append(child.getUpazilla());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}
							if (child.getUnion() != null) {
								writer.append(child.getUnion());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							if (child.getUnit() != null) {
								writer.append(child.getUnit());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}
							if (child.getMouzaPara() != null) {
								writer.append(child.getMouzaPara());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							writer.append("enccrv_" + i);
							writer.append(',');
							writer.append(action.data().get("expiryDate"));
							writer.append('\n');
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {

					}

				}
				}

			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {

		}

	}

	public boolean isScheduleCreated(ScheduleLog scheduleLog, String milestone) {
		if(scheduleLog!=null){
		List<Map<String, String>> data = scheduleLog.data();
		
		for (Map<String, String> map : data) {
			if (map.get("visitCode").equalsIgnoreCase(milestone)) {
				
				return true;
			}
		}

		return false;
		}else{
			return false;
		}

	}

	public int getLimit(String visitCode) {
		String[] visitCodeSplit = visitCode.trim().split("_");
		int ancNumber = Integer.parseInt(visitCodeSplit[1]);
		return ancNumber;

	}

	public void createENCCCSV(HttpServletResponse response, String start_date,
			String end_date, String reportName) {
		long startTime = convertDateToTimestampMills(start_date);
		List<Child> childs = allChilds.allChildsCreatedBetween2Dates("Child",
				convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));
		
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Beneficiary Name");
			writer.append(',');// 1
			writer.append("Identifier");
			writer.append(',');// 1
			writer.append("Schedule Status");
			writer.append(',');// 4
			writer.append("FWA Name");
			writer.append(',');// 5
			writer.append("Upazilla");
			writer.append(',');// 6
			writer.append("Union");
			writer.append(',');// 7
			writer.append("Unit");
			writer.append(',');// 8
			writer.append("Mouza Para");
			writer.append(',');// 9
			writer.append("Name of ENCC Visit");
			writer.append(',');// 10
			writer.append("Visit date");
			writer.append(',');// 11
			writer.append("Time Started");
			writer.append(',');// 12
			writer.append("Time Ended");
			writer.append('\n');

			for (Child child : childs) {

				for (int i = 0; i < 3; i++) {
					Map<String,String> encc = getENCCNumber(child, i);
					if (!getENCCNumber(child, i).isEmpty()
							&& child.PROVIDERID()!=null) {
						Long submitTime = Long.parseLong(encc.get("clientVersion"));
						
					 if( isInRange(submitTime,startTime)){
						writer.append(child.details().get("FWBNFCHILDNAME"));
						writer.append(',');
						writer.append("");
						writer.append(',');
						writer.append(getENCCNumber(child, i).get(
								getENCCFormStattusKeyName(i)));
						writer.append(',');
						writer.append(child.PROVIDERID());
						writer.append(',');
						if (child.getUpazilla() != null) {
							writer.append(child.getUpazilla());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						if (child.getUnion() != null) {
							writer.append(child.getUnion());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						if (child.getUnit() != null) {
							writer.append(child.getUnit());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						if (child.getMouzaPara() != null) {
							writer.append(child.getMouzaPara());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
						writer.append(getScheduleMilestone(i));
						writer.append(',');
						writer.append(getENCCNumber(child, i).get("today"));
						writer.append(',');
						writer.append(getENCCNumber(child, i).get("start"));
						writer.append(',');
						writer.append(getENCCNumber(child, i).get("end"));
						writer.append('\n');

					} else {

					}
				}
				}
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			
		}

	}

	public String getScheduleMilestone(int i) {
		String milestone = "";
		if (i == 0) {
			milestone = "enccrv_1";
		} else if (i == 1) {
			milestone = "enccrv_2";
		} else if (i == 2) {
			milestone = "enccrv_3";
		} else {

		}
		return milestone;
	}

	public String getENCCFormStattusKeyName(int i) {
		String keyName = "";
		if (i == 0) {
			keyName = "encc1_current_formStatus";
		} else if (i == 1) {
			keyName = "encc2_current_formStatus";
		} else if (i == 2) {
			keyName = "encc3_current_formStatus";
		} else {

		}
		return keyName;
	}

	public Map<String, String> getENCCNumber(Child child, int i) {
		Map<String, String> encc = new HashMap<>();
		if (i == 0) {
			encc = child.enccVisitOne();
		} else if (i == 1) {
			encc = child.enccVisitTwo();
		} else if (i == 2) {
			encc = child.enccVisitThree();
		} else {

		}
		return encc;

	}

	public void createPNCNotSubmit(HttpServletResponse response,
			String schedule, String start_date, String end_date,
			String reportName, boolean isAcitve) {
		List<Action> actions = allActions.findByScheduleIsActivenAndTimeStamp(
				schedule, isAcitve, convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Beneficiary Name");
			writer.append(',');// 1
			writer.append("Identifier");
			writer.append(',');// 1
			writer.append("Schedule Status");
			writer.append(',');// 4
			writer.append("FWA Name");
			writer.append(',');// 5
			writer.append("Upazilla");
			writer.append(',');// 6
			writer.append("Union");
			writer.append(',');// 7
			writer.append("Unit");
			writer.append(',');// 8
			writer.append("Mouza Para");
			writer.append(',');// 9
			writer.append("Name of ENCC Visit");
			writer.append(',');// 9
			writer.append("Expired Date");
			writer.append('\n'); // 22

			for (Action action : actions) {
				Mother mother = allMothers.findByCaseId(action.caseId());
				String visitCode = action.data().get("visitCode");

				if (mother.PROVIDERID() != null ) {// should have pnc
															// visit
					try {

						if (mother.mother_first_name() != null) {
							writer.append(mother.mother_first_name());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.mother_wom_nid() != null
								|| mother.mother_wom_bid() != null) {
								String id = mother.mother_wom_nid();
								if(id.isEmpty()){
									id = mother.mother_wom_bid();
								}
								writer.append("''"
								+ String.valueOf(id.toString())+ "''");
								writer.append(',');
								} else {
								writer.append("");
								writer.append(',');
								
								}

						writer.append(action.data().get("alertStatus"));
						writer.append(',');
						writer.append(mother.PROVIDERID());
						writer.append(',');
						if (mother.FWWOMUPAZILLA() != null) {
							writer.append(mother.FWWOMUPAZILLA());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMUNION() != null) {
							writer.append(mother.getFWWOMUNION());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMSUBUNIT() != null) {
							writer.append(mother.getFWWOMSUBUNIT());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getMother_mauza() != null) {
							writer.append(mother.getMother_mauza());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						writer.append(visitCode);
						writer.append(',');
						writer.append(action.data().get("expiryDate"));
						writer.append('\n');
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					
				}
				
				for (int i = 1; i < getLimit(visitCode); i++) {
					Map<String, String> encc = getPNCNumber(mother, i);
					ScheduleLog scheduleLog = allReportActions
							.findByyCaseIdByname(mother.caseId(), action.data()
									.get("scheduleName"));
					

					if (mother != null && mother.PROVIDERID() != null
							&& encc.isEmpty()
							&& isScheduleCreated(scheduleLog,
									getPNCScheduleMilestone(i))) {	
						// visit
						try {
						
						if (mother.mother_first_name() != null) {
						writer.append(mother.mother_first_name());
						writer.append(',');
						} else {
						writer.append("");
						writer.append(',');
						}
						
						if (mother.mother_wom_nid() != null
						|| mother.mother_wom_bid() != null) {
						String id = mother.mother_wom_nid();
						if(id.isEmpty()){
							id = mother.mother_wom_bid();
						}
						writer.append("''"
						+ String.valueOf(id.toString())+ "''");
						writer.append(',');
						} else {
						writer.append("");
						writer.append(',');
						
						}
						
						writer.append("expired");
						writer.append(',');
						writer.append(mother.PROVIDERID());
						writer.append(',');
						if (mother.FWWOMUPAZILLA() != null) {
						writer.append(mother.FWWOMUPAZILLA());
						writer.append(',');
						} else {
						writer.append("");
						writer.append(',');
						}
						
						if (mother.getFWWOMUNION() != null) {
						writer.append(mother.getFWWOMUNION());
						writer.append(',');
						} else {
						writer.append("");
						writer.append(',');
						}
						
						if (mother.getFWWOMSUBUNIT() != null) {
						writer.append(mother.getFWWOMSUBUNIT());
						writer.append(',');
						} else {
						writer.append("");
						writer.append(',');
						}
						
						if (mother.getMother_mauza() != null) {
						writer.append(mother.getMother_mauza());
						writer.append(',');
						} else {
						writer.append("");
						writer.append(',');
						}
						
						writer.append(getPNCScheduleMilestone(i));
						writer.append(',');
						writer.append(action.data().get("expiryDate"));
						writer.append('\n');
						} catch (Exception e) {
						e.printStackTrace();
						}
						}else{
						
						}

				}

			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {

		}

	}

	public String getPNCScheduleMilestone(int i) {
		String milestone = "";
		if (i == 0) {
			milestone = "pncrv_1";
		} else if (i == 1) {
			milestone = "pncrv_2";
		} else if (i == 2) {
			milestone = "pncrv_3";
		} else {

		}
		return milestone;
	}
	public Map<String, String> getPNCNumber(Mother mother, int i) {
		Map<String, String> pnc = new HashMap<>();
		if (i == 0) {
			pnc = mother.pncVisitOne();
		} else if (i == 1) {
			pnc = mother.pncVisitTwo();
		} else if (i == 2) {
			pnc = mother.pncVisitThree();
		} else {
			
		}
		return pnc;

	}
	public void createPNC(HttpServletResponse response, String schedule,
			String start_date, String end_date, String reportName,
			boolean isAcitve) {
		long startTime = convertDateToTimestampMills(start_date);
		List<Mother> mothers = allMothers.allMothersCreatedBetween2Dates("Mother",
				startTime,
				convertDateToTimestampMills(end_date));
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Beneficiary Name");
			writer.append(',');// 1
			writer.append("Identifier");
			writer.append(',');// 1
			writer.append("Schedule Status");
			writer.append(',');// 4
			writer.append("FWA Name");
			writer.append(',');// 5
			writer.append("Upazilla");
			writer.append(',');// 6
			writer.append("Union");
			writer.append(',');// 7
			writer.append("Unit");
			writer.append(',');// 8
			writer.append("Mouza Para");
			writer.append(',');// 9
			writer.append("Name of PNC Visit");
			writer.append(',');// 10
			writer.append("Visit date");
			writer.append(',');// 11
			writer.append("Time Started");
			writer.append(',');// 12
			writer.append("Time Ended");
			writer.append('\n'); // 22

			for (Mother mother : mothers) {
				
				for (int i = 0; i < 3; i++) {
					Map<String,String> pncvisit = getPNCNumber(mother,i);				
				if (!pncvisit.isEmpty()	&& mother.PROVIDERID() != null ) {					
					Long submitTime = Long.parseLong(pncvisit.get("clientVersion"));					
				   if( isInRange(submitTime,startTime)){
						if (mother.mother_first_name() != null) {
							writer.append(mother.mother_first_name());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.mother_wom_nid() != null
								|| mother.mother_wom_bid() != null) {
								String id = mother.mother_wom_nid();
								if(id.isEmpty()){
									id = mother.mother_wom_bid();
							}
								writer.append("''"
								+ String.valueOf(id.toString())+ "''");
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');							
						}

						writer.append(pncvisit.get(getPNCFormStattusKeyName(i)));
						writer.append(',');
						writer.append(mother.PROVIDERID());
						writer.append(',');
						if (mother.FWWOMUPAZILLA() != null) {
							writer.append(mother.FWWOMUPAZILLA());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMUNION() != null) {
							writer.append(mother.getFWWOMUNION());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMSUBUNIT() != null) {
							writer.append(mother.getFWWOMSUBUNIT());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getMother_mauza() != null) {
							writer.append(mother.getMother_mauza());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						writer.append(getPNCScheduleMilestone(i));
						writer.append(',');

						writer.append(pncvisit.get("today"));
						writer.append(',');
						writer.append(pncvisit.get("start"));
						writer.append(',');
						writer.append(pncvisit.get("end"));
						writer.append('\n');
				}					
					
				}
				
				}
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			
		}

	}
	
	public String getPNCFormStattusKeyName(int i) {
		String keyName = "";
		if (i == 0) {
			keyName = "pnc1_current_formStatus";
		} else if (i == 1) {
			keyName = "pnc2_current_formStatus";
		} else if (i == 2) {
			keyName = "PNC3_current_formStatus";
		
		} else {
			
		}
		return keyName;
	}

	public void createANCRVNotSubmit(HttpServletResponse response,
			String schedule, String start_date, String end_date,
			String reportName, boolean isAcitve) {
		List<Action> actions = allActions.findByScheduleIsActivenAndTimeStamp(
				schedule, isAcitve, convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Beneficiary Name");
			writer.append(',');// 1
			writer.append("Identifier");
			writer.append(',');// 1
			writer.append("Schedule Status");
			writer.append(',');// 4
			writer.append("FWA Name");
			writer.append(',');// 5
			writer.append("Upazilla");
			writer.append(',');// 6
			writer.append("Union");
			writer.append(',');// 7
			writer.append("Unit");
			writer.append(',');// 8
			writer.append("Mouza Para");
			writer.append(',');// 9
			writer.append("Name of ANC Visit");
			writer.append(',');// 9
			writer.append("Expired Date");
			writer.append('\n'); // 22

			for (Action action : actions) {
				Mother mother = allMothers.findByCaseId(action.caseId());
				String visitCode = action.data().get("visitCode");

				if (mother != null && mother.PROVIDERID() != null) {// should
																	// have anc
					// visit
					
					
					try {

						if (mother.mother_first_name() != null) {
							writer.append(mother.mother_first_name());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.mother_wom_nid() != null
								|| mother.mother_wom_bid() != null) {
								String id = mother.mother_wom_nid();
								if(id.isEmpty()){
									id = mother.mother_wom_bid();
								}
								writer.append("''"
								+ String.valueOf(id.toString())+ "''");
								writer.append(',');
								} else {
								writer.append("");
								writer.append(',');
								
								}

						writer.append(action.data().get("alertStatus"));
						writer.append(',');
						writer.append(mother.PROVIDERID());
						writer.append(',');
						if (mother.FWWOMUPAZILLA() != null) {
							writer.append(mother.FWWOMUPAZILLA());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMUNION() != null) {
							writer.append(mother.getFWWOMUNION());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMSUBUNIT() != null) {
							writer.append(mother.getFWWOMSUBUNIT());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getMother_mauza() != null) {
							writer.append(mother.getMother_mauza());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						writer.append(visitCode);
						writer.append(',');
						writer.append(action.data().get("expiryDate"));
						writer.append('\n');
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

				}

				for (int i = 1; i < getLimit(visitCode); i++) {
					Map<String, String> anc = getANCNumber(mother, i);
					ScheduleLog scheduleLog = allReportActions
							.findByyCaseIdByname(mother.caseId(), action.data()
									.get("scheduleName"));

					
						if (mother != null && mother.PROVIDERID() != null
								&& anc.isEmpty()
								&& isScheduleCreated(scheduleLog,
										getANCScheduleMilestone(i))) {											// have
																		
						
						try {

							if (mother.mother_first_name() != null) {
								writer.append(mother.mother_first_name());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							if (mother.mother_wom_nid() != null
									|| mother.mother_wom_bid() != null) {
									String id = mother.mother_wom_nid();
									if(id.isEmpty()){
										id = mother.mother_wom_bid();
									}
									writer.append("''"
									+ String.valueOf(id.toString())+ "''");
									writer.append(',');
									} else {
									writer.append("");
									writer.append(',');
									
									}

							writer.append("expired");
							writer.append(',');
							writer.append(mother.PROVIDERID());
							writer.append(',');
							if (mother.FWWOMUPAZILLA() != null) {
								writer.append(mother.FWWOMUPAZILLA());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							if (mother.getFWWOMUNION() != null) {
								writer.append(mother.getFWWOMUNION());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							if (mother.getFWWOMSUBUNIT() != null) {
								writer.append(mother.getFWWOMSUBUNIT());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							if (mother.getMother_mauza() != null) {
								writer.append(mother.getMother_mauza());
								writer.append(',');
							} else {
								writer.append("");
								writer.append(',');
							}

							writer.append(getANCScheduleMilestone(i));
							writer.append(',');
							writer.append(action.data().get("expiryDate"));
							writer.append('\n');
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {

					}

				}
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			System.out.println("Loop time end:" + System.currentTimeMillis());
		}

	}

	public String getANCScheduleMilestone(int i) {
		String milestone = "";
		if (i == 0) {
			milestone = "ancrv_1";
		} else if (i == 1) {
			milestone = "ancrv_2";
		} else if (i == 2) {
			milestone = "ancrv_3";
		} else if( i== 3) {
			milestone = "ancrv_4";
		}else {
			
		}
		return milestone;
	}
	public Map<String, String> getANCNumber(Mother mother, int i) {
		Map<String, String> anc = new HashMap<>();
		if (i == 0) {
			anc = mother.ancVisitOne();
		} else if (i == 1) {
			anc = mother.ancVisitTwo();
		} else if (i == 2) {
			anc = mother.ancVisitThree();
		} else if(i == 3){
			anc = mother.ancVisitFour();
		}
		return anc;

	}

	public void createANCRV(HttpServletResponse response, String schedule,
			String start_date, String end_date, String reportName,
			boolean isAcitve) {
		long startTime = convertDateToTimestampMills(start_date);
		List<Mother> mothers = allMothers.allMothersCreatedBetween2Dates("Mother",
				startTime,
				convertDateToTimestampMills(end_date));
		System.err.println("Mother:"+mothers.size());
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);
		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Beneficiary Name");
			writer.append(',');// 1
			writer.append("Identifier");
			writer.append(',');// 1
			writer.append("Schedule Status");
			writer.append(',');// 4
			writer.append("FWA Name");
			writer.append(',');// 5
			writer.append("Upazilla");
			writer.append(',');// 6
			writer.append("Union");
			writer.append(',');// 7
			writer.append("Unit");
			writer.append(',');// 8
			writer.append("Mouza Para");
			writer.append(',');// 9
			writer.append("Name of ANC Visit");
			writer.append(',');// 10
			writer.append("Visit date");
			writer.append(',');// 11
			writer.append("Time Started");
			writer.append(',');// 12
			writer.append("Time Ended");
			writer.append('\n'); // 22

			for (Mother mother : mothers) {
				
				for (int i = 0; i < 4; i++) {
					Map<String,String> anc = getANCNumber(mother,i);
					
					if (!anc.isEmpty()	&& mother.PROVIDERID() !=null  ) {		
						Long submitTime = Long.parseLong(anc.get("clientVersion"));
						System.err.println("submitTime:"+submitTime);
					if( isInRange(submitTime,startTime)){

						if (mother.mother_first_name() != null) {
							writer.append(mother.mother_first_name());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.mother_wom_nid() != null
								|| mother.mother_wom_bid() != null) {
								String id = mother.mother_wom_nid();
								if(id.isEmpty()){
									id = mother.mother_wom_bid();
								}
								writer.append("''"
								+ String.valueOf(id.toString())+ "''");
								writer.append(',');
								} else {
								writer.append("");
								writer.append(',');
								
								}

						writer.append(anc.get(getANCFormStattusKeyName(i)));
						writer.append(',');
						writer.append(mother.PROVIDERID());
						writer.append(',');
						if (mother.FWWOMUPAZILLA() != null) {
							writer.append(mother.FWWOMUPAZILLA());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMUNION() != null) {
							writer.append(mother.getFWWOMUNION());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getFWWOMSUBUNIT() != null) {
							writer.append(mother.getFWWOMSUBUNIT());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						if (mother.getMother_mauza() != null) {
							writer.append(mother.getMother_mauza());
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}

						writer.append(getANCScheduleMilestone(i));
						writer.append(',');

						writer.append(anc.get("today"));
						writer.append(',');
						writer.append(anc.get("start"));
						writer.append(',');
						writer.append(anc.get("end"));
						writer.append('\n');
					
				}
			}
			}
			}	
			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			System.out.println("Loop time end:" + System.currentTimeMillis());
		}

	}
	
	public boolean isInRange(long submitTime, long startTime){
		System.err.println("submitTime:"+submitTime+" startTime"+startTime);
		if(startTime <=submitTime){
			System.err.println("OKKKK");
			return true;
		}else{
			System.err.println("Mot OKK");
		return false;
		}
	}
		public String getANCFormStattusKeyName(int i) {
			String keyName = "";
			if (i == 0) {
				keyName = "anc1_current_formStatus";
			} else if (i == 1) {
				keyName = "ANC2_current_formStatus";
			} else if (i == 2) {
				keyName = "ANC3_current_formStatus";
			} else if (i == 3) {
				keyName = "ANC4_current_formStatus";
			} else {
				
			}
			return keyName;
		}
		
	public List<Exports> getExportsByUser(String user) {
		return allExports.getExportsByUser(user);
	}

	public void createHHCSV(HttpServletResponse response, String formName,
			String start_date, String end_date, String reportName) {
		System.out.println("Path:..............:" + multimediaDirPath);
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);

		ViewResult hhs = allHouseHolds.allHHsCreatedBetween2Date("HouseHold",
				convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date), "FWA");

		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Serial No.");
			writer.append(',');// 1
			writer.append("FWA WORKER ID");
			writer.append(',');
			writer.append("today_newhh_FW");
			writer.append(',');
			writer.append("start_newhh_FW");
			writer.append(',');
			writer.append("end_newhh_FW");
			writer.append(',');
			writer.append("FWNHREGDATE");
			writer.append(',');
			writer.append("FWGOBHHID");
			writer.append(',');
			writer.append("FWJIVHHID");
			writer.append(',');
			writer.append("FWUNION");
			writer.append(',');
			writer.append("FWWARD");
			writer.append(',');
			writer.append("FWSUBUNIT");
			writer.append(','); // 10
			writer.append("FWMAUZA_PARA");
			writer.append(',');
			writer.append("FWHOHFNAME");
			writer.append(',');
			writer.append("FWHOHBIRTHDATE");
			writer.append(',');
			writer.append("FWHOHGENDER");
			writer.append(',');
			writer.append("FWNHHMBRNUMB");
			writer.append(',');
			writer.append("FWNHHMWRA");
			writer.append(',');
			writer.append("ELCO");
			writer.append(',');
			writer.append("REGDATE");
			writer.append(',');
			writer.append("FWWOMFNAME");
			writer.append(',');
			writer.append("FWBIRTHDATE");
			writer.append(',');
			writer.append("FWWOMAGE");
			writer.append(','); // 20
			writer.append("FWCWOMSTRMEN");
			writer.append(',');
			writer.append("FWCWOMHUSLIV");
			writer.append(',');
			writer.append("FWCWOMHUSALV");
			writer.append(',');
			writer.append("FWCWOMHUSSTR");
			writer.append(',');
			writer.append("FWELIGIBLE");
			writer.append(',');
			writer.append("FWWOMANYID");
			writer.append(',');
			writer.append("FWWOMNID");
			writer.append(',');
			writer.append("FWWOMBID");
			writer.append(',');
			writer.append("FWHUSNAME");
			writer.append(',');
			writer.append("Latitude");
			writer.append(','); // 30
			writer.append("Longitude");
			writer.append(',');

			writer.append("RECEIVED TIME AT SERVER");
			writer.append('\n'); // 36

			int count = hhs.getSize();
			for (int i = 0; i < count; i++) {
				if (hhs.getRows().get(i).getValue().equalsIgnoreCase("")) {
					System.err.println("Error.........................");
				}
				writer.append(Integer.toString(i + 1));
				writer.append(',');
				String[] ConvertRowValueStringToArray = hhs.getRows().get(i)
						.getValue().split(",");
				int elco = 0;

				String ElcoCountAsString = ConvertRowValueStringToArray[0]
						.replace("\"", "").replace("[", "");

				if (ElcoCountAsString != null && !ElcoCountAsString.isEmpty()
						&& !ElcoCountAsString.equalsIgnoreCase(""))
					elco = Integer.parseInt(ElcoCountAsString);

				for (int increment = 1; increment <= 16; increment++) {
					if (ConvertRowValueStringToArray[increment] != null
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[increment]
									.isEmpty()
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[increment]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				writer.append(Integer.toString(elco));
				writer.append(',');
				/**
				 * Eclo exists condition.
				 * */
				if (elco > 0) {
					for (int increment = 21; increment <= 30; increment++) {
						if (ConvertRowValueStringToArray[increment] != null
								&& !ConvertRowValueStringToArray[increment]
										.equalsIgnoreCase("null")
								&& !ConvertRowValueStringToArray[increment]
										.isEmpty()
								&& !ConvertRowValueStringToArray[increment]
										.equalsIgnoreCase("")) {
							writer.append(ConvertRowValueStringToArray[increment]);
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
					}

					/**
					 * For NID & BRID
					 * */
					if (ConvertRowValueStringToArray[31] != null
							&& !ConvertRowValueStringToArray[31]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[31].equals("\"\"")
							&& !ConvertRowValueStringToArray[31].isEmpty()
							&& !ConvertRowValueStringToArray[31]
									.equalsIgnoreCase("")) {
						writer.append(' ' + String
								.valueOf(ConvertRowValueStringToArray[31]
										.toString()));
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
					if (ConvertRowValueStringToArray[32] != null
							&& !ConvertRowValueStringToArray[32]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[32].equals("\"\"")
							&& !ConvertRowValueStringToArray[32].isEmpty()
							&& !ConvertRowValueStringToArray[32]
									.equalsIgnoreCase("")) {
						writer.append(' ' + String
								.valueOf(ConvertRowValueStringToArray[32]
										.toString()));
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
					/**
					 * Server time
					 * */
					writer.append(ConvertRowValueStringToArray[33]);
					/**
					 * GPS calculation
					 * */
					String gps = ConvertRowValueStringToArray[34];
					if (gps != null && !gps.isEmpty()
							&& !gps.equalsIgnoreCase("")) {
						String[] location = gps.split(" ");
						if (location.length > 1) {
							writer.append(',');
							writer.append(location[0].replace("\"", ""));
							writer.append(',');
							writer.append(location[1].replace("\"", ""));
						} else {
							writer.append(',');
							writer.append("");
							writer.append(',');
							writer.append("");
						}
					} else {
						writer.append(',');
						writer.append("");
						writer.append(',');
						writer.append("");
					}
				} else {
					/**
					 * Eclo does not exists condition.
					 * */
					for (int increment = 1; increment <= 14; increment++) {
						writer.append(',');
						writer.append("");
					}
				}

				writer.append(',');
				writer.append(ConvertRowValueStringToArray[17]);

				writer.append('\n');
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public void createCensusCSV(HttpServletResponse response, String formName,
			String start_date, String end_date, String reportName) {
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);

		ViewResult hhs = allHouseHolds.allCensusCreatedBetween2Date(
				"HouseHold", convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date), "FWA");

		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Serial No.");
			writer.append(',');// 1
			writer.append("Form Status at Submission");
			writer.append(','); // 1
			writer.append("SCHEDULED_DATE_Census_FW");
			writer.append(','); // 2
			writer.append("today_census_FW");
			writer.append(','); // 3
			writer.append("start_census_FW");
			writer.append(','); // 4
			writer.append("end_census_FW");
			writer.append(','); // 5
			writer.append("FWCENDATE");
			writer.append(','); // 6
			writer.append("FWCENSTAT");
			writer.append(','); // 7
			writer.append("FWGOBHHID");
			writer.append(','); // 8
			writer.append("FWJIVHHID");
			writer.append(','); // 9
			writer.append("FWUNION");
			writer.append(','); // 10
			writer.append("FWWARD");
			writer.append(','); // 11
			writer.append("FWSUBUNIT");
			writer.append(','); // 12
			writer.append("FWMAUZA_PARA");
			writer.append(','); // 13
			writer.append("ELCO");
			writer.append(','); // 14
			writer.append("existing_ELCO");
			writer.append(','); // 15
			writer.append("new_ELCO");
			writer.append(','); // 16
			writer.append("CENDATE");
			writer.append(','); // 17
			writer.append("FWWOMFNAME");
			writer.append(','); // 18
			writer.append("FWBIRTHDATE");
			writer.append(','); // 19
			writer.append("FWWOMAGE");
			writer.append(','); // 20
			writer.append("FWCWOMSTRMEN");
			writer.append(','); // 21
			writer.append("FWCWOMHUSLIV");
			writer.append(','); // 22
			writer.append("FWCWOMHUSALV");
			writer.append(','); // 23
			writer.append("FWCWOMHUSSTR");
			writer.append(','); // 24
			writer.append("FWELIGIBLE");
			writer.append(','); // 25
			writer.append("FWWOMANYID");
			writer.append(','); // 26
			writer.append("FWWOMNID");
			writer.append(','); // 27
			writer.append("FWWOMBID");
			writer.append(','); // 28
			writer.append("FWHUSNAME");
			writer.append(','); // 29
			writer.append("RECEIVED TIME AT SERVER");
			writer.append(','); // 30

			writer.append("FWA WORKER ID");

			writer.append('\n'); // 34

			int count = hhs.getSize();
			for (int i = 0; i < count; i++) {
				if (hhs.getRows().get(i).getValue().equalsIgnoreCase("")) {
					System.err.println("Error.........................");
				}
				writer.append(Integer.toString(i + 1));
				writer.append(',');
				String[] ConvertRowValueStringToArray = hhs.getRows().get(i)
						.getValue().split(",");
				int elco = 0;
				String temp = ConvertRowValueStringToArray[0].replace("\"", "")
						.replace("[", "");
				if (temp != null && !temp.isEmpty()
						&& !temp.equalsIgnoreCase(""))
					elco = Integer.parseInt(temp);

				writer.append(ConvertRowValueStringToArray[1]);// 1
				writer.append(',');
				writer.append(ConvertRowValueStringToArray[4]);// 2
				writer.append(',');
				writer.append(ConvertRowValueStringToArray[3]);// 3
				writer.append(',');
				writer.append(ConvertRowValueStringToArray[4]);// 4
				writer.append(',');
				writer.append(ConvertRowValueStringToArray[5]);// 5
				writer.append(',');

				for (int increment = 10; increment <= 30; increment++) {
					if (ConvertRowValueStringToArray[increment] != null
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[increment]
									.isEmpty()
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[increment]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				/**
				 * For NID & BRID
				 * */

				if (ConvertRowValueStringToArray[31] != null
						&& !ConvertRowValueStringToArray[31]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[31].equals("\"\"")
						&& !ConvertRowValueStringToArray[31].isEmpty()
						&& !ConvertRowValueStringToArray[31]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String
							.valueOf(ConvertRowValueStringToArray[31]
									.toString()));
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}
				if (ConvertRowValueStringToArray[32] != null
						&& !ConvertRowValueStringToArray[32]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[32].equals("\"\"")
						&& !ConvertRowValueStringToArray[32].isEmpty()
						&& !ConvertRowValueStringToArray[32]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String.valueOf(
							ConvertRowValueStringToArray[32]).toString());
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}
				if (ConvertRowValueStringToArray[33] != null
						&& !ConvertRowValueStringToArray[33]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[33].equals("\"\"")
						&& !ConvertRowValueStringToArray[33].isEmpty()
						&& !ConvertRowValueStringToArray[33]
								.equalsIgnoreCase("")) {
					writer.append(((ConvertRowValueStringToArray[33]
							.equalsIgnoreCase("null")) ? " "
							: ConvertRowValueStringToArray[33]));
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}
				writer.append(ConvertRowValueStringToArray[6]);
				writer.append(',');
				writer.append(ConvertRowValueStringToArray[2]);

				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createPsrfCSV(HttpServletResponse response, String formName,
			String start_date, String end_date, String reportName) {

		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);

		ViewResult ecs = allElcos.allElcosCreatedBetween2Date("Elco",
				convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));

		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Serial No.");
			writer.append(',');// 1
			writer.append("FWGOBHHID");
			writer.append(',');// 4
			writer.append("FWJIVHHID");
			writer.append(',');// 5
			writer.append("FWUNION");
			writer.append(',');// 6
			writer.append("FWWARD");
			writer.append(',');// 7
			writer.append("FWSUBUNIT");
			writer.append(',');// 8
			writer.append("FWMAUZA_PARA");
			writer.append(',');// 9
			writer.append("wom_nid");
			writer.append(',');// 10
			writer.append("wob_bid");
			writer.append(',');// 11
			writer.append("wom_age");
			writer.append(',');// 12
			writer.append("first_name");
			writer.append(',');// 13
			writer.append("husname");
			writer.append(',');// 15
			writer.append("FWA Worker ID");
			writer.append(','); // 16
			writer.append("start");
			writer.append(',');// 1
			writer.append("end");
			writer.append(',');// 2
			writer.append("today");
			writer.append(',');// 3
			writer.append("Form Status at Submission");
			writer.append(',');// 17
			writer.append("SCHEDULED_DATE_PSRF_FWA");
			writer.append(',');// 18
			writer.append("FWPSRDATE");
			writer.append(',');// 19
			writer.append("FWPSRSTS");
			writer.append(',');// 20
			writer.append("FWPSRLMP");
			writer.append(',');// 21
			writer.append("FWPSRPREGSTS");
			writer.append(',');// 22
			writer.append("FWPSRHUSPREGWTD");
			writer.append(',');// 24
			writer.append("FWPSREVRPREG");
			writer.append(',');// 25
			writer.append("FWPSRTOTBIRTH");
			writer.append(',');// 26
			writer.append("FWPSRNBDTH");
			writer.append(',');// 27
			writer.append("FWPSRPRSB");
			writer.append(',');// 28
			writer.append("FWPSRPRMC");
			writer.append(',');// 29
			writer.append("FWPSRPREGTWYRS");
			writer.append(',');// 30
			writer.append("FWPSRPRVPREGCOMP");
			writer.append(',');// 31
			writer.append("FWPSRPRCHECKS");
			writer.append(',');// 32
			writer.append("FWPSRANM");
			writer.append(',');// 33
			writer.append("FWPSRHBP");
			writer.append(',');// 34
			writer.append("FWPSRDBT");
			writer.append(',');// 35
			writer.append("FWPSRTHY");
			writer.append(',');// 36
			writer.append("FWPSRVDGMEM");
			writer.append(',');// 37
			writer.append("FWPSRWOMEDU");
			writer.append(',');// 38
			writer.append("FWPSRHHLAT");
			writer.append(',');// 39
			writer.append("FWPSRHHRICE");
			writer.append(',');// 40
			writer.append("FWPSRPHONE");
			writer.append(',');// 41
			writer.append("FWPSRPHONENUM");
			writer.append(',');// 42
			writer.append("FWPSRMUAC");
			writer.append(',');// 43
			writer.append("FWVG");
			writer.append(',');// 44
			writer.append("FWHRP");
			writer.append(','); // 45
			writer.append("FWHR_PSR");
			writer.append(',');// 46
			writer.append("FWFLAGVALUE");
			writer.append(',');// 47
			writer.append("FWSORTVALUE");
			writer.append(',');// 48
			writer.append("FWPSRPREGWTD");
			writer.append(',');// 23
			writer.append("RECEIVED_TIME_AT_SERVER");
			writer.append('\n'); // 51

			int count = ecs.getSize();
			for (int i = 0; i < count; i++) {
				if (ecs.getRows().get(i).getValue().equalsIgnoreCase("")) {
					System.err.println("Error.........................");
				}
				writer.append(Integer.toString(i + 1));
				writer.append(',');
				String[] ConvertRowValueStringToArray = ecs.getRows().get(i)
						.getValue().split(",");

				for (int increment = 1; increment <= 6; increment++) {
					if (ConvertRowValueStringToArray[increment] != null
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[increment]
									.isEmpty()
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[increment]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				/**
				 * For NID & BRID
				 * */
				if (ConvertRowValueStringToArray[7] != null
						&& !ConvertRowValueStringToArray[7]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[7].equals("\"\"")
						&& !ConvertRowValueStringToArray[7].isEmpty()
						&& !ConvertRowValueStringToArray[7]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String
							.valueOf(ConvertRowValueStringToArray[7].toString()));
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}
				if (ConvertRowValueStringToArray[8] != null
						&& !ConvertRowValueStringToArray[8]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[8].equals("\"\"")
						&& !ConvertRowValueStringToArray[8].isEmpty()
						&& !ConvertRowValueStringToArray[8]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String.valueOf(
							ConvertRowValueStringToArray[8]).toString());
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}

				for (int increment = 9; increment <= 11; increment++) {
					if (ConvertRowValueStringToArray[increment] != null
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[increment]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[increment]
									.isEmpty()
							&& !ConvertRowValueStringToArray[increment]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[increment]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				// 12 is external user id which currently not showing....
				if (ConvertRowValueStringToArray[13] != null
						&& !ConvertRowValueStringToArray[13]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[13].equals("\"NaN\"")
						&& !ConvertRowValueStringToArray[13].equals("\"\"")
						&& !ConvertRowValueStringToArray[13].isEmpty()
						&& !ConvertRowValueStringToArray[13]
								.equalsIgnoreCase("")) {
					writer.append(ConvertRowValueStringToArray[13]);
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}

				if (ConvertRowValueStringToArray.length > 30) {
					for (int increment = 14; increment <= 49; increment++) {
						System.out.println(increment + " : "
								+ ConvertRowValueStringToArray[increment]);
						if (ConvertRowValueStringToArray[increment] != null
								&& !ConvertRowValueStringToArray[increment]
										.equalsIgnoreCase("null")
								&& !ConvertRowValueStringToArray[increment]
										.equals("\"NaN\"")
								&& !ConvertRowValueStringToArray[increment]
										.equals("\"\"")
								&& !ConvertRowValueStringToArray[increment]
										.isEmpty()
								&& !ConvertRowValueStringToArray[increment]
										.equalsIgnoreCase("")) {
							writer.append(ConvertRowValueStringToArray[increment]);
							writer.append(',');
						} else {
							writer.append("");
							writer.append(',');
						}
					}
					writer.append("");
				} else {
					for (int increment = 1; increment <= 32; increment++) {
						writer.append("");
						writer.append(',');
					}
					writer.append(ConvertRowValueStringToArray[14]);
					writer.append(',');
					writer.append(ConvertRowValueStringToArray[15]);

				}
				writer.append('\n');
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public void createMisCensusCSV(HttpServletResponse response,
			String formName, String start_date, String end_date,
			String reportName) {
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);

		ViewResult ecs = allElcos.allMisCensusCreatedBetween2Date("Elco",
				convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));

		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Serial No.");
			writer.append(',');// 1
			writer.append("FWA Worker ID");
			writer.append(',');// 1
			writer.append("existing_location");
			writer.append(',');// 2
			writer.append("today");
			writer.append(',');// 3
			writer.append("FWGOBHHID");
			writer.append(',');// 4
			writer.append("FWJIVHHID");
			writer.append(',');// 5
			writer.append("FWUNION");
			writer.append(',');// 6
			writer.append("FWWARD");
			writer.append(',');// 7
			writer.append("FWSUBUNIT");
			writer.append(',');// 8
			writer.append("FWMAUZA_PARA");
			writer.append(',');// 9
			writer.append("FWWOMNID");
			writer.append(',');// 10
			writer.append("FWWOMBID");
			writer.append(',');// 11
			writer.append("wom_age");
			writer.append(',');// 12
			writer.append("first_name");
			writer.append(',');// 13
			writer.append("husname");
			writer.append(',');// 15
			writer.append("FWA Worker ID");
			writer.append(','); // 16
			writer.append("Form Status at Submission");
			writer.append(',');// 17
			writer.append("SCHEDULED_DATE");
			writer.append(',');// 18
			writer.append("start");
			writer.append(',');// 19
			writer.append("end");
			writer.append(',');// 20
			writer.append("FWMISCENSUSDATE");
			writer.append(',');// 21
			writer.append("FWCOUPLENUM");
			writer.append(',');// 22
			writer.append("FWTETSTAT");
			writer.append(',');// 23
			writer.append("FWMARRYDATE");
			writer.append(',');// 24
			writer.append("FWCHILDALIVEB");
			writer.append(',');// 26
			writer.append("FWCHILDALIVEG");
			writer.append(',');// 27
			writer.append("RECEIVED_TIME_AT_SERVER");

			writer.append('\n'); // 30
			int count = ecs.getSize();
			for (int i = 0; i < count; i++) {
				if (ecs.getRows().get(i).getValue().equalsIgnoreCase("")) {
					System.err.println("Error.........................");
				}
				writer.append(Integer.toString(i + 1));
				writer.append(',');
				String[] ConvertRowValueStringToArray = ecs.getRows().get(i)
						.getValue().split(",");
				for (int counter = 1; counter <= 9; counter++) {
					if (ConvertRowValueStringToArray[counter] != null
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[counter].isEmpty()
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[counter]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				/**
				 * For NID & BRID
				 * */
				if (ConvertRowValueStringToArray[10] != null
						&& !ConvertRowValueStringToArray[10]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[10].equals("\"\"")
						&& !ConvertRowValueStringToArray[10].isEmpty()
						&& !ConvertRowValueStringToArray[10]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String
							.valueOf(ConvertRowValueStringToArray[10]
									.toString()));
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}
				if (ConvertRowValueStringToArray[11] != null
						&& !ConvertRowValueStringToArray[11]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[11].equals("\"\"")
						&& !ConvertRowValueStringToArray[11].isEmpty()
						&& !ConvertRowValueStringToArray[11]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String.valueOf(
							ConvertRowValueStringToArray[11]).toString());
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}

				for (int counter = 12; counter <= 14; counter++) {
					if (ConvertRowValueStringToArray[counter] != null
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[counter].isEmpty()
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[counter]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				for (int counter = 16; counter <= 26; counter++) {
					if (ConvertRowValueStringToArray[counter] != null
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[counter].isEmpty()
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[counter]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				writer.append(ConvertRowValueStringToArray[27]);
				writer.append('\n');
			}
			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public void createMisElcoCSV(HttpServletResponse response, String formName,
			String start_date, String end_date, String reportName) {

		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; " + "filename="
				+ reportName);

		ViewResult ecs = allElcos.allMisElcoCreatedBetween2Date("Elco",
				convertDateToTimestampMills(start_date),
				convertDateToTimestampMills(end_date));

		FileWriter writer;
		try {
			writer = new FileWriter(multimediaDirPath + "/export/" + reportName);
			writer.append("Serial No.");
			writer.append(',');// 1
			writer.append("FWA Worker ID");
			writer.append(',');// 1
			writer.append("FWGOBHHID");
			writer.append(',');// 4
			writer.append("FWJIVHHID");
			writer.append(',');// 5
			writer.append("FWUNION");
			writer.append(',');// 6
			writer.append("FWWARD");
			writer.append(',');// 7
			writer.append("FWSUBUNIT");
			writer.append(',');// 8
			writer.append("FWMAUZA_PARA");
			writer.append(',');// 9
			writer.append("FWWOMNID");
			writer.append(',');// 10
			writer.append("FWWOMBID");
			writer.append(',');// 11
			writer.append("wom_age");
			writer.append(',');// 12
			writer.append("first_name");
			writer.append(',');// 13
			writer.append("husname");
			writer.append(','); // 16
			writer.append("today");
			writer.append(',');// 17
			writer.append("start");
			writer.append(',');// 18
			writer.append("end");
			writer.append(',');// 19
			writer.append("Form Status at Submission");
			writer.append(',');// 2
			writer.append("SCHEDULED_DATE");
			writer.append(',');// 3
			writer.append("FWMISELCODATE");
			writer.append(',');// 20
			writer.append("FWPMISBIRTHCTRL");
			writer.append(',');// 21
			writer.append("FWMISBCSOURCE");
			writer.append(',');// 22
			writer.append("RECEIVED_TIME_AT_SERVER");
			writer.append('\n'); // 22

			int count = ecs.getSize();
			for (int i = 0; i < count; i++) {
				if (ecs.getRows().get(i).getValue().equalsIgnoreCase("")) {
					System.err.println("Error.........................");
				}

				writer.append(Integer.toString(i + 1));
				writer.append(',');
				String[] ConvertRowValueStringToArray = ecs.getRows().get(i)
						.getValue().split(",");
				for (int counter = 1; counter <= 7; counter++) {
					if (ConvertRowValueStringToArray[counter] != null
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[counter].isEmpty()
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[counter]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				/**
				 * For NID & BRID
				 * */
				if (ConvertRowValueStringToArray[8] != null
						&& !ConvertRowValueStringToArray[8]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[8].equals("\"\"")
						&& !ConvertRowValueStringToArray[8].isEmpty()
						&& !ConvertRowValueStringToArray[8]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String
							.valueOf(ConvertRowValueStringToArray[8].toString()));
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}
				if (ConvertRowValueStringToArray[9] != null
						&& !ConvertRowValueStringToArray[9]
								.equalsIgnoreCase("null")
						&& !ConvertRowValueStringToArray[9].equals("\"\"")
						&& !ConvertRowValueStringToArray[9].isEmpty()
						&& !ConvertRowValueStringToArray[9]
								.equalsIgnoreCase("")) {
					writer.append(' ' + String.valueOf(
							ConvertRowValueStringToArray[9]).toString());
					writer.append(',');
				} else {
					writer.append("");
					writer.append(',');
				}

				for (int counter = 10; counter <= 12; counter++) {
					if (ConvertRowValueStringToArray[counter] != null
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[counter].isEmpty()
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[counter]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}
				for (int counter = 15; counter <= 22; counter++) {
					if (ConvertRowValueStringToArray[counter] != null
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"NaN\"")
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("null")
							&& !ConvertRowValueStringToArray[counter]
									.equals("\"\"")
							&& !ConvertRowValueStringToArray[counter].isEmpty()
							&& !ConvertRowValueStringToArray[counter]
									.equalsIgnoreCase("")) {
						writer.append(ConvertRowValueStringToArray[counter]);
						writer.append(',');
					} else {
						writer.append("");
						writer.append(',');
					}
				}

				writer.append(ConvertRowValueStringToArray[23]);
				writer.append('\n');
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Loop time end:" + System.currentTimeMillis());
		}
	}

	public void deleteExport(String id) {
		Exports export = allExports.getExportsById(id);
		allExports.delete(export);
	}

	private long convertDateToTimestampMills(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date day = null;
		try {
			day = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day.getTime();
	}

}
