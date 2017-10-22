package org.opensrp.scheduler.service;

import org.opensrp.domain.ScheduleRuleDTO;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.repository.ScheduleRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleRuleService {
	
	private ScheduleRuleRepository scheduleRuleRepository;
	
	@Autowired
	public ScheduleRuleService(ScheduleRuleRepository scheduleRuleRepository){
		this.scheduleRuleRepository = scheduleRuleRepository;
	}

	public String submit(ScheduleRules scheduleRules){		
		try{
			ScheduleRules scheduleRule = scheduleRuleRepository.findByName(scheduleRules.getName());
			if(!scheduleRule.getName().equalsIgnoreCase("")){
				return "2";
			}else{
				scheduleRuleRepository.add(scheduleRules);
				return "1";
			}
		}catch(Exception e){			
			return "0";
		}
	}
	public String edit(ScheduleRuleDTO scheduleRulesDTO){		
		try{
			ScheduleRules scheduleRule = scheduleRuleRepository.findByID(scheduleRulesDTO.getRuleID());
			scheduleRule.setName(scheduleRulesDTO.getName());		
			scheduleRule.setRule(scheduleRulesDTO.getRule());		
			scheduleRule.setRevision(scheduleRule.getRevision());
			scheduleRuleRepository.update(scheduleRule);
			return "1";
		}catch(Exception e){
			return "0";
		}		
	}
}
