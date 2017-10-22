/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static org.opensrp.common.util.DateUtil.getTimestamp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.register.mcare.ANCRegister;
import org.opensrp.register.mcare.ANCRegisterEntry;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllMothers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class ANCRegisterService {

	private final AllMothers allMothers;
	
	@Autowired
	public ANCRegisterService(AllMothers allMothers)
	{
		this.allMothers = allMothers;
	}

	public ANCRegister getANCRegisterForProvider(String providerId)
	{
		ArrayList<ANCRegisterEntry> ancRegisterEntries = new ArrayList<>();
        List<Mother> mothers = allMothers.allOpenMothers();
        
        for (Mother anc : mothers) {
        	ANCRegisterEntry ancRegisterEntry = new ANCRegisterEntry()
        	.withCaseId(anc.caseId())
        	.withTODAY(anc.TODAY())
        	.withSTART(anc.START())
        	.withRelationalid(anc.relationalid())
        	.withPROVIDERID(anc.PROVIDERID())
        	.withLOCATIONID(anc.LOCATIONID())
        	.withINSTANCEID(anc.INSTANCEID())
        	.withmother_wom_lmp(anc.mother_wom_lmp())
        	.withJmother_first_name(anc.mother_first_name())
        	.withanc1_current_form_status(anc.anc1_current_form_status())
	        .withanc2_current_form_status(anc.anc2_current_form_status())
	        .withanc3_current_form_status(anc.anc3_current_form_status())
	        .withanc4_current_form_status(anc.anc4_current_form_status())
	        .setBnf_current_form_status(anc.getBnf_current_form_status())
	        .setPnc1_current_form_status(anc.getPnc1_current_form_status())
	        .setPnc2_current_form_status(anc.getPnc2_current_form_status())
	        .setPnc3_current_form_status(anc.getPnc3_current_form_status())	       
	        .withmother_husname(anc.mother_husname())
        	.withmother_gobhhid(anc.mother_gobhhid())
        	.withmother_jivhhid(anc.mother_jivhhid())
        	.withmother_wom_nid(anc.mother_wom_nid())
        	.withmother_wom_bid(anc.mother_wom_bid())
        	.withmother_wom_age(anc.mother_wom_age())
        	.setmother_mauza(anc.getMother_mauza())
        	.setFWWOMUNION(anc.getFWWOMUNION())
        	.setFWWOMWARD(anc.getFWWOMWARD())
        	.setFWWOMSUBUNIT(anc.getFWWOMSUBUNIT())
        	.withIsClosed(anc.isClosed())
        	.withEND(anc.END())        	
        	.withDetails(anc.details())
        	.withANCVisitOne(anc.ancVisitOne())
        	.withANCVisitTwo(anc.ancVisitTwo())
        	.withANCVisitThree(anc.ancVisitThree())
        	.withANCVisitFour(anc.ancVisitFour())
        	.withBNFVisitDetails(anc.bnfVisitDetails())
        	.withPNCVisitOne(anc.pncVisitOne())
        	.withPNCVisitTwo(anc.pncVisitTwo())
        	.withPNCVisitThree(anc.pncVisitThree());
            
        	ancRegisterEntries.add(ancRegisterEntry);
        }
        return new ANCRegister(ancRegisterEntries);
	}
	
	public ANCRegister getANCRegister(String type, String startKey, String endKey)
	{
		long start = getTimestamp(startKey);		
		long end = getTimestamp(endKey);
		
		ArrayList<ANCRegisterEntry> ancRegisterEntries = new ArrayList<>();
        List<Mother> mothers = allMothers.allMothersCreatedBetween2Dates(type,start, end);
        
        for (Mother anc : mothers) {
        	ANCRegisterEntry ancRegisterEntry = new ANCRegisterEntry()
        	.withCaseId(anc.caseId())
        	.withTODAY(anc.TODAY())
        	.withSTART(anc.START())
        	.withRelationalid(anc.relationalid())
        	.withPROVIDERID(anc.PROVIDERID())
        	.withLOCATIONID(anc.LOCATIONID())
        	.withINSTANCEID(anc.INSTANCEID())
        	.withmother_wom_lmp(anc.mother_wom_lmp())
        	.withJmother_first_name(anc.mother_first_name())
        	.withanc1_current_form_status(anc.anc1_current_form_status())
	        .withanc2_current_form_status(anc.anc2_current_form_status())
	        .withanc3_current_form_status(anc.anc3_current_form_status())
	        .withanc4_current_form_status(anc.anc4_current_form_status())
	        .setBnf_current_form_status(anc.getBnf_current_form_status())
	        .setPnc1_current_form_status(anc.getPnc1_current_form_status())
	        .setPnc2_current_form_status(anc.getPnc2_current_form_status())
	        .setPnc3_current_form_status(anc.getPnc3_current_form_status())	       
	        .withmother_husname(anc.mother_husname())
        	.withmother_gobhhid(anc.mother_gobhhid())
        	.withmother_jivhhid(anc.mother_jivhhid())
        	.withmother_wom_nid(anc.mother_wom_nid())
        	.withmother_wom_bid(anc.mother_wom_bid())
        	.withmother_wom_age(anc.mother_wom_age())
        	.setmother_mauza(anc.getMother_mauza())
        	.setFWWOMUNION(anc.getFWWOMUNION())
        	.setFWWOMWARD(anc.getFWWOMWARD())
        	.setFWWOMSUBUNIT(anc.getFWWOMSUBUNIT())
        	.withIsClosed(anc.isClosed())
        	.withEND(anc.END())        	
        	.withDetails(anc.details())
        	.withANCVisitOne(anc.ancVisitOne())
        	.withANCVisitTwo(anc.ancVisitTwo())
        	.withANCVisitThree(anc.ancVisitThree())
        	.withANCVisitFour(anc.ancVisitFour())
        	.withBNFVisitDetails(anc.bnfVisitDetails())
        	.withPNCVisitOne(anc.pncVisitOne())
        	.withPNCVisitTwo(anc.pncVisitTwo())
        	.withPNCVisitThree(anc.pncVisitThree());
            
        	ancRegisterEntries.add(ancRegisterEntry);
        }
        return new ANCRegister(ancRegisterEntries);
	}

}
