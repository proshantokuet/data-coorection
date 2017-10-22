/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.register.mcare.ELCORegister;
import org.opensrp.register.mcare.ELCORegisterEntry;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.*;
import static org.opensrp.common.util.DateUtil.getTimestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class ELCORegisterService {
	
	private final AllElcos allElcos;
	
	@Autowired
	public ELCORegisterService(AllElcos allElcos)
	{
		this.allElcos = allElcos;
	}
	
	/*public ELCORegister getELCORegisterForProvider(String providerId)
	{
		ArrayList<ELCORegisterEntry> elcoRegisterEntries = new ArrayList<>();
        List<Elco> elcos = allElcos.allOpenELCOs();
        
        for (Elco ec : elcos) {
        
            ELCORegisterEntry ecRegisterEntry = new ELCORegisterEntry()
            		.withCASEID(ec.caseId())
            		.withINSTANCEID(ec.INSTANCEID())
            		.withPROVIDERID(ec.PROVIDERID())
            		.withLOCATIONID(ec.LOCATIONID())
            		.withTODAY(ec.TODAY())
            		//.withSTART(ec.START())
            		//.withEND(ec.END())
            		.withGOBHHID(ec.GOBHHID())
            		.withJiVitAHHID(ec.JiVitAHHID())
            		.withexisting_ELCO(ec.existing_ELCO())
            		.withnew_ELCO(ec.new_ELCO())
            		.withELCO(ec.ELCO())
            		.withWomanREGDATE(ec.WomanREGDATE())
            		.withFWCWOMHUSALV(ec.FWCWOMHUSALV())
            		.withFWCWOMHUSLIV(ec.FWCWOMHUSLIV())
            		.withFWCWOMHUSSTR(ec.FWCWOMHUSSTR())
            		.withFWCWOMSTRMEN(ec.FWCWOMSTRMEN())
            		.withFWCENDATE(ec.FWCENDATE())
            		.withFWCENSTAT(ec.FWCENSTAT())
            		.withFWWOMFNAME(ec.FWWOMFNAME())
            		.withFWWOMLNAME(ec.FWWOMLNAME())
            		.withFWWOMANYID(ec.FWWOMANYID())
            		.withFWWOMNID(ec.FWWOMNID())
            		.withFWWOMRETYPENID(ec.FWWOMRETYPENID())
            		.withFWWOMRETYPENID_CONCEPT(ec.FWWOMRETYPENID_CONCEPT())
            		.withFWWOMBID(ec.FWWOMBID())
            		.withFWWOMRETYPEBID(ec.FWWOMRETYPEBID())
            		.withFWWOMRETYPEBID_CONCEPT(ec.FWWOMRETYPEBID_CONCEPT())
            		.withFWHUSNAME(ec.FWHUSNAME())
            		.withFWBIRTHDATE(ec.FWBIRTHDATE())
            		.withFWGENDER(ec.FWGENDER())
            		.withFWWOMAGE(ec.FWWOMAGE())
            		.withFWELIGIBLE(ec.FWELIGIBLE())
            		.withFWELIGIBLE2(ec.FWELIGIBLE2())
            		.withFWWOMCOUNTRY(ec.FWWOMCOUNTRY())
                    .withFWWOMDIVISION(ec.FWWOMDIVISION())
                    .withFWWOMDISTRICT(ec.FWWOMDISTRICT())
                    .withFWWOMUPAZILLA(ec.FWWOMUPAZILLA())
                    .withFWWOMUNION(ec.FWWOMUNION())
                    .withFWWOMWARD(ec.FWWOMWARD())
                    .withFWWOMSUBUNIT(ec.FWWOMSUBUNIT())
                    .withFWWOMMAUZA_PARA(ec.FWWOMMAUZA_PARA())
                    .withFWWOMGOBHHID(ec.FWWOMGOBHHID())
                    .withFWWOMGPS(ec.FWWOMGPS())
                    .withform_name(ec.form_name())
            		.withDetails(ec.details())
            		.withmultimediaAttachments(ec.multimediaAttachments())
            		.withPSRFDETAILS(ec.PSRFDETAILS())
            		.withMISDETAILS(ec.MISDETAILS())
            		.withSTART(ec.getDetail(START_DATE))
            		.withEND(ec.getDetail(END_DATE))
            		.withFWDISPLAYAGE(ec.getDetail(FW_DISPLAY_AGE))
            		.withFWWOMSTRMEN(ec.getDetail(FW_CWOMSTRMEN))
            		.withFWWOMHUSSTR(ec.getDetail(FW_CWOMHUSSTR))
            		.withFWWOMHUSALV(ec.getDetail(FW_CWOMHUSALV))
            		.withFWWOMHUSLIV(ec.getDetail(FW_CWOMHUSLIV));     
            
            elcoRegisterEntries.add(ecRegisterEntry);
        }
        return new ELCORegister(elcoRegisterEntries);
	}*/

	/*public ELCORegister getELCORegister(String type, String startKey, String endKey)
	{
		long start = getTimestamp(startKey);		
		long end = getTimestamp(endKey);
		
		ArrayList<ELCORegisterEntry> elcoRegisterEntries = new ArrayList<>();
        List<Elco> elcos = allElcos.allElcosCreatedBetween2Dates(type,start, end);
        
        for (Elco ec : elcos) {
 
            ELCORegisterEntry ecRegisterEntry = new ELCORegisterEntry()
            		.withCASEID(ec.caseId())
            		.withINSTANCEID(ec.INSTANCEID())
            		.withPROVIDERID(ec.PROVIDERID())
            		.withLOCATIONID(ec.LOCATIONID())
            		.withTODAY(ec.TODAY())
            		//.withSTART(ec.START())
            		//.withEND(ec.END())
            		.withGOBHHID(ec.GOBHHID())
            		.withJiVitAHHID(ec.JiVitAHHID())
            		.withexisting_ELCO(ec.existing_ELCO())
            		.withnew_ELCO(ec.new_ELCO())
            		.withELCO(ec.ELCO())
            		.withWomanREGDATE(ec.WomanREGDATE())
            		.withFWCWOMHUSALV(ec.FWCWOMHUSALV())
            		.withFWCWOMHUSLIV(ec.FWCWOMHUSLIV())
            		.withFWCWOMHUSSTR(ec.FWCWOMHUSSTR())
            		.withFWCWOMSTRMEN(ec.FWCWOMSTRMEN())
            		.withFWCENDATE(ec.FWCENDATE())
            		.withFWCENSTAT(ec.FWCENSTAT())
            		.withFWWOMFNAME(ec.FWWOMFNAME())
            		.withFWWOMLNAME(ec.FWWOMLNAME())
            		.withFWWOMANYID(ec.FWWOMANYID())
            		.withFWWOMNID(ec.FWWOMNID())
            		.withFWWOMRETYPENID(ec.FWWOMRETYPENID())
            		.withFWWOMRETYPENID_CONCEPT(ec.FWWOMRETYPENID_CONCEPT())
            		.withFWWOMBID(ec.FWWOMBID())
            		.withFWWOMRETYPEBID(ec.FWWOMRETYPEBID())
            		.withFWWOMRETYPEBID_CONCEPT(ec.FWWOMRETYPEBID_CONCEPT())
            		.withFWHUSNAME(ec.FWHUSNAME())
            		.withFWBIRTHDATE(ec.FWBIRTHDATE())
            		.withFWGENDER(ec.FWGENDER())
            		.withFWWOMAGE(ec.FWWOMAGE())
            		.withFWELIGIBLE(ec.FWELIGIBLE())
            		.withFWELIGIBLE2(ec.FWELIGIBLE2())
            		.withFWWOMCOUNTRY(ec.FWWOMCOUNTRY())
                    .withFWWOMDIVISION(ec.FWWOMDIVISION())
                    .withFWWOMDISTRICT(ec.FWWOMDISTRICT())
                    .withFWWOMUPAZILLA(ec.FWWOMUPAZILLA())
                    .withFWWOMUNION(ec.FWWOMUNION())
                    .withFWWOMWARD(ec.FWWOMWARD())
                    .withFWWOMSUBUNIT(ec.FWWOMSUBUNIT())
                    .withFWWOMMAUZA_PARA(ec.FWWOMMAUZA_PARA())
                    .withFWWOMGOBHHID(ec.FWWOMGOBHHID())
                    .withFWWOMGPS(ec.FWWOMGPS())
                    .withform_name(ec.form_name())
            		.withDetails(ec.details())
            		.withmultimediaAttachments(ec.multimediaAttachments())
            		.withPSRFDETAILS(ec.PSRFDETAILS())
            		.withMISDETAILS(ec.MISDETAILS())
            		.withSTART(ec.getDetail(START_DATE))
            		.withEND(ec.getDetail(END_DATE))
            		.withFWDISPLAYAGE(ec.getDetail(FW_DISPLAY_AGE))
            		.withFWWOMSTRMEN(ec.getDetail(FW_CWOMSTRMEN))
            		.withFWWOMHUSSTR(ec.getDetail(FW_CWOMHUSSTR))
            		.withFWWOMHUSALV(ec.getDetail(FW_CWOMHUSALV))
            		.withFWWOMHUSLIV(ec.getDetail(FW_CWOMHUSLIV));     
            
            elcoRegisterEntries.add(ecRegisterEntry);
        }
        return new ELCORegister(elcoRegisterEntries);
	}*/
	
	public Elco getElcoById(String id){
		return allElcos.get(id);
	}
}