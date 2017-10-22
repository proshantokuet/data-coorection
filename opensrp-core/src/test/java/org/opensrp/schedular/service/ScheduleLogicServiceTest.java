/*package org.opensrp.schedular.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.scheduler.Defination;
import org.opensrp.scheduler.Rule;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.repository.ScheduleRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp.xml")
public class ScheduleLogicServiceTest {
	
	@Autowired
    AllActions allActions;
	@Autowired
	ScheduleRuleRepository scheduleRuleRepository;

	@Before
    public void setUp() throws Exception {
        initMocks(this);
       
    }
	
	@Test
	public void shouldSaveData(){		
		
		List<Defination> definations1 = new ArrayList<Defination>();
		List<Defination> definations2 = new ArrayList<Defination>();
		List<Rule> rules = new ArrayList<Rule>();
		Defination definition2 = new Defination("elco", "FWWOMFNAME");
		Defination definition1 = new Defination("submission", "1");
		
		definations1.add(definition1);
		definations2.add(definition2);
		
		Rule rule1 = new Rule(definations1, "Household", "Cencus");
		
		
		Rule rule2 = new Rule(definations2, "Household", "psrf_form");
		
		
		
		rules.add(rule1);
		rules.add(rule2);
		
		try{
			assertEquals("HouseHold Form", scheduleRuleRepository.findByName("HouseHold Form").getName());
		}catch(Exception e){
			scheduleRuleRepository.submit(new ScheduleRules("HouseHold Form", "proshanto", rules));
		}
		
		//assertEquals(1, sr.allRule().size());
		 System.out.println(scheduleRuleRepository.toString());
		
		
		
	}
	@Test
	public void shouldGetScheduleRuleByName(){
		assertEquals("HouseHold Form", scheduleRuleRepository.findByName("HouseHold Form").getName());
		
	}
	@Test
	public void shouldGetScheduleRuleForPSRFInHH(){
		ScheduleRules scheduleRule = scheduleRuleRepository.findByName("HouseHold Form");	
		String fieldName = "";
		if(scheduleRule != null){			
			for (int i = 0; i < scheduleRule.getRule().size(); i++) {				
				if(scheduleRule.getRule().get(i).getEndFormName().equalsIgnoreCase("psrf_form")){
					for (int j = 0; j < scheduleRule.getRule().get(i).getDefination().size(); j++) {
						if(scheduleRule.getRule().get(i).getDefination().get(j).getName().equalsIgnoreCase("elco")){
							fieldName= scheduleRule.getRule().get(i).getDefination().get(j).getValue();
						}
					}
				}
				
			}
		}
		assertEquals("FWWOMFNAME", fieldName);
	}
	@Test
	public void shouldGgetScheduleRuleForCensus(){
		ScheduleRules scheduleRule = scheduleRuleRepository.findByName("HouseHold Form");	
		String fieldName = "";
		if(scheduleRule != null){			
			for (int i = 0; i < scheduleRule.getRule().size(); i++) {				
				if(scheduleRule.getRule().get(i).getEndFormName().equalsIgnoreCase("Cencus")){
					for (int j = 0; j < scheduleRule.getRule().get(i).getDefination().size(); j++) {
						if(scheduleRule.getRule().get(i).getDefination().get(j).getName().equalsIgnoreCase("submission")){
							fieldName= scheduleRule.getRule().get(i).getDefination().get(j).getValue();
						}
					}
				}
				
			}
		}
		assertEquals("1", fieldName);
	}
}
*/