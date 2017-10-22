package org.opensrp.web.controller;

import static ch.lambdaj.collection.LambdaCollections.with;

import java.util.List;

import org.json.JSONException;
import org.opensrp.connector.openmrs.service.OpenmrsReportingService;
import org.opensrp.dto.Action;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.register.mcare.HHRegister;
import org.opensrp.register.mcare.service.reporting.HHReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.opensrp.register.mcare.mapper.ELCORegisterMapper;
import org.opensrp.register.mcare.mapper.HHRegisterMapper;


import ch.lambdaj.function.convert.Converter;

import com.google.gson.Gson;

@Controller
public class ReportController {

	private OpenmrsReportingService reportService;
	private HHReportService hhReportService;
	private ELCORegisterMapper ecRegisterMapper;
	private HHRegisterMapper hhRegisterMapper;
	
	
	@Autowired
	public ReportController(
			OpenmrsReportingService reportService,
			HHReportService hhReportService,
			ELCORegisterMapper ecRegisterMapper,
			HHRegisterMapper hhRegisterMapper) {
		this.reportService = reportService;
		this.hhReportService = hhReportService;
		this.ecRegisterMapper = ecRegisterMapper;
		this.hhRegisterMapper = hhRegisterMapper;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-definitions")
    public ResponseEntity<String> reportDefinitions() throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportDefinitions()),HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/report-data")
    public ResponseEntity<String> reportDefinitions(@RequestParam("uuid") String uuid) throws JSONException {
		return new ResponseEntity<>(new Gson().toJson(reportService.getReportData(uuid)),HttpStatus.OK);
    }
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/report/report-hh-7-days")
    public ResponseEntity<HHRegisterDTO> reportHHForPrev7Days(@RequestParam("provider-id") String providerId) {
		 HHRegister hhRegister = hhReportService.getHHDataVisitedPrev7Days(providerId);
	     return new ResponseEntity<>(hhRegisterMapper.mapToDTO(hhRegister), HttpStatus.OK);
    }*/
/*
    @RequestMapping(method = RequestMethod.GET, value = "/report/actions")
    @ResponseBody
    public List<Action> getNewActionForANM(@RequestParam("anmIdentifier") String anmIdentifier, @RequestParam("timeStamp") Long timeStamp){
        List<org.opensrp.scheduler.Action> actions = actionService.getNewAlertsForANM(anmIdentifier, timeStamp);
        return with(actions).convert(new Converter<org.opensrp.scheduler.Action, Action>() {
            @Override
            public Action convert(org.opensrp.scheduler.Action action) {
                return ActionConvertor.from(action);
            }
        });
    }*/

}
