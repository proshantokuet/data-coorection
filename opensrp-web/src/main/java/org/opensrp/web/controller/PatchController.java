package org.opensrp.web.controller;

import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PatchController {
	private AllMothers allMothers;
	private AllChilds allChilds;
	
	@Autowired
	public PatchController(AllMothers allMothers,AllChilds allChilds){
		this.allMothers = allMothers;
		this.allChilds = allChilds;
	}
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/update-child")
	public void childUpdate(){
		List<Child> clilds = allChilds.getAll();
		int i =0;
		for (Child child : clilds) {
			try{
			Mother mother = allMothers.findByCaseId(child.details().get("relationalid"));
			if(child.TODAY()!=null){
			child.withClientVersion(DateTimeUtil.getTimestampOfADate(child.TODAY()));
			}else{
				child.withClientVersion(0);
			}
			try{
				child.withDistrict(mother.FWWOMDISTRICT());
			}catch(Exception e){
				child.withDistrict("");
			}
			try{
				child.withUpazilla(mother.FWWOMUPAZILLA());
			}catch(Exception e){
				child.withUpazilla("");
			}
			
			try{
				child.withUnion(mother.getFWWOMUNION());
			}catch(Exception e){
				child.withUnion("");
			}
			try{
				child.withUnit(mother.getFWWOMSUBUNIT());
			}catch(Exception e){
				child.withUnit("");
			}
			
			try{
				child.withMouzaPara(mother.getMother_mauza());
			}catch(Exception e){
				child.withMouzaPara("");
			}
			
			allChilds.update(child);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/update-woman")
	public void womanUpdate(){
		List<Mother> mothers = allMothers.getAll();
		int i =0;
		for (Mother mother : mothers) {
			try{
			
			if(mother.TODAY()!=null){
				mother.withClientVersion(DateTimeUtil.getTimestampOfADate(mother.TODAY()));
			}else{
				mother.withClientVersion(0);
			}
			if(!mother.ancVisitOne().isEmpty()){
				mother.ancVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitOne().get("today")).toString());
				
				
			}
			
			if(!mother.ancVisitTwo().isEmpty()){
				mother.ancVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitTwo().get("today")).toString());
				
				
			}
			
			if(!mother.ancVisitThree().isEmpty()){
				mother.ancVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitThree().get("today")).toString());
				
				
			}

			if(!mother.ancVisitFour().isEmpty()){
				mother.ancVisitFour().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitFour().get("today")).toString());
				
				
			}
			
			if(!mother.pncVisitOne().isEmpty()){
				mother.pncVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitOne().get("today")).toString());
				
				
			}
			
			if(!mother.pncVisitTwo().isEmpty()){
				mother.pncVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitTwo().get("today")).toString());
				
				
			}
			if(!mother.pncVisitThree().isEmpty()){
				mother.pncVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitThree().get("today")).toString());
				
				
			}
			
			
			allMothers.update(mother);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/update-child-encc")
	public void childENCCUpdate(){
		List<Child> childs = allChilds.getAll();
		int i =0;
		for (Child child : childs) {
			try{
			
			if(child.TODAY()!=null){
				child.withClientVersion(DateTimeUtil.getTimestampOfADate(child.TODAY()));
			}else{
				child.withClientVersion(0);
			}
			if(!child.enccVisitOne().isEmpty()){
				child.enccVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitOne().get("today")).toString());
				
				
			}
			
			if(!child.enccVisitTwo().isEmpty()){
				child.enccVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitTwo().get("today")).toString());
				
				
			}
			
			if(!child.enccVisitThree().isEmpty()){
				child.enccVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitThree().get("today")).toString());
				
				
			}

			
			
			
			
			allChilds.update(child);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}

}
