package org.opensrp.register.mcare.mapper;

import static ch.lambdaj.collection.LambdaCollections.with;

import java.util.List;

import org.opensrp.dto.register.ANC_RegisterDTO;
import org.opensrp.dto.register.ANC_RegisterEntryDTO;
import org.opensrp.register.mcare.ANCRegister;
import org.opensrp.register.mcare.ANCRegisterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.lambdaj.function.convert.Converter;

@Component
public class ANCRegisterMapper {

	private static Logger logger = LoggerFactory.getLogger(ANCRegisterMapper.class.toString());
	
	public ANC_RegisterDTO mapToDTO(ANCRegister ancRegister)
	{
        List<ANC_RegisterEntryDTO> ancRegisterEntryDTOs =
                with(ancRegister.ancRegisterEntries())
                        .convert(new Converter<ANCRegisterEntry, ANC_RegisterEntryDTO>() {
                            @Override
                            public ANC_RegisterEntryDTO convert(ANCRegisterEntry entry) {
                                return new ANC_RegisterEntryDTO()
	                                .withCaseId(entry.caseId())
	                            	.withTODAY(entry.TODAY())
	                            	.withSTART(entry.START())
	                            	.withRelationalid(entry.relationalid())
	                            	.withPROVIDERID(entry.PROVIDERID())
	                            	.withLOCATIONID(entry.LOCATIONID())
	                            	.withINSTANCEID(entry.INSTANCEID())
	                            	.withmother_wom_lmp(entry.mother_wom_lmp())
	                            	.withJmother_first_name(entry.mother_first_name())
	                            	.withanc1_current_form_status(entry.anc1_current_form_status())
	                            	.withanc1_current_form_status(entry.anc2_current_form_status())
	                            	.withanc1_current_form_status(entry.anc3_current_form_status())
	                            	.withanc1_current_form_status(entry.anc4_current_form_status())
	                            	.setBnf_current_form_status(entry.getBnf_current_form_status())
	                            	.setPnc1_current_form_status(entry.getPnc1_current_form_status())
	                            	.setPnc2_current_form_status(entry.getPnc2_current_form_status())
	                            	.setPnc3_current_form_status(entry.getPnc3_current_form_status())
	                            	.withmother_husname(entry.mother_husname())
	                            	.withmother_gobhhid(entry.mother_gobhhid())
	                            	.withmother_jivhhid(entry.mother_jivhhid())	                            	
	                            	.withmother_wom_nid(entry.mother_wom_nid())
	                            	.withmother_wom_bid(entry.mother_wom_bid())	                            	
	                            	.withmother_wom_age(entry.mother_wom_age()) 
	                            	.setmother_mauza(entry.getmother_mauza())
						        	.setFWWOMUNION(entry.getFWWOMUNION())
						        	.setFWWOMWARD(entry.getFWWOMWARD())
						        	.setFWWOMSUBUNIT(entry.getFWWOMSUBUNIT())
	                            	.withIsClosed(entry.isClosed())
	                            	.withEND(entry.END())
	                            	.withDetails(entry.details())
	                            	.withANCVisitOne(entry.ancVisitOne())
	                            	.withANCVisitTwo(entry.ancVisitTwo())
	                            	.withANCVisitThree(entry.ancVisitThree())
	                            	.withANCVisitFour(entry.ancVisitFour())
	                            	.withBNFVisitDetails(entry.bnfVisitDetails())
                                	.withPNCVisitOne(entry.pncVisitOne())
                                	.withPNCVisitTwo(entry.pncVisitTwo())
                                	.withPNCVisitThree(entry.pncVisitThree());
                            }
                        });
        
        return new ANC_RegisterDTO(ancRegisterEntryDTOs);
    
	}
	
}
