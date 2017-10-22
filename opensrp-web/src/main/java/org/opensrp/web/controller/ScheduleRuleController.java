package org.opensrp.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.opensrp.domain.ScheduleRuleDTO;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.service.ScheduleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScheduleRuleController {
	private ScheduleRuleService scheduleRuleService;
	
	@Autowired
	public ScheduleRuleController(ScheduleRuleService scheduleRuleService){
		this.scheduleRuleService = scheduleRuleService;
		
	}
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add-schedule-rule")
	public ResponseEntity<String> addScheduleRule(@RequestBody ScheduleRules scheduleRules) {
		String message = scheduleRuleService.submit(scheduleRules);
		return new ResponseEntity<>(message,OK);
	}
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/edit-schedule-rule")
	public ResponseEntity<String> editScheduleRule(@RequestBody ScheduleRuleDTO scheduleRulesDTO) {
		String message = scheduleRuleService.edit(scheduleRulesDTO);
		return new ResponseEntity<>(message,OK);
	}
}
