/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.register.mcare.HHRegister;
import org.opensrp.register.mcare.HHRegisterEntry;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;

import static org.opensrp.common.AllConstants.HHRegistrationFields.*;
import static org.opensrp.common.util.DateUtil.getTimestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class HHRegisterService {
	
	private static Logger logger = LoggerFactory.getLogger(HHRegisterService.class);
	private final AllHouseHolds allHouseHolds;
	
	@Autowired
	public HHRegisterService(AllHouseHolds allHouseHolds)
	{
		this.allHouseHolds = allHouseHolds;
	}
	
	/*public HHRegister getHHRegisterForProvider(String providerId)
	{
		ArrayList<HHRegisterEntry> hhRegisterEntries = new ArrayList<>();
        List<HouseHold> hhs = allHouseHolds.findAllHouseHolds();
        
        for (HouseHold hh : hhs) {	
    		
        	HHRegisterEntry hhRegisterEntry = new HHRegisterEntry()
        		.withCASEID(hh.caseId()) 
        		.withINSTANCEID(hh.INSTANCEID())
        		.withPROVIDERID(hh.PROVIDERID())
        		//.withLOCATIONID(hh.LOCATIONID())
        		.withTODAY(hh.TODAY())
        		.withFWNHREGDATE(hh.FWNHREGDATE())
        		.withFWGOBHHID(hh.FWGOBHHID())
        		.withFWJIVHHID(hh.FWJIVHHID())
        		.withFWCOUNTRY(hh.FWCOUNTRY())
                .withFWDIVISION(hh.FWDIVISION())
                .withFWDISTRICT(hh.FWDISTRICT())
                .withFWUPAZILLA(hh.FWUPAZILLA())
                .withFWUNION(hh.FWUNION())
                .withFWWARD(hh.FWWARD())
                .withFWSUBUNIT(hh.FWSUBUNIT())
                .withFWMAUZA_PARA(hh.FWMAUZA_PARA())
        		.withFWNHHHGPS(hh.FWNHHHGPS())
        		.withform_name(hh.form_name())
        		.withFWHOHFNAME(hh.FWHOHFNAME())
        		.withFWHOHLNAME(hh.FWHOHLNAME())
        		.withFWHOHBIRTHDATE(hh.FWHOHBIRTHDATE())
        		.withFWHOHGENDER(hh.FWHOHGENDER())
        		.withFWNHHMBRNUM(hh.FWNHHMBRNUM())
        		.withFWNHHMWRA(hh.FWNHHMWRA())
        		.withELCO(hh.ELCO())
        		.withuser_type(hh.user_type())
        		.withexternal_user_ID(hh.external_user_ID())
        		.withcurrent_formStatus(hh.current_formStatus())
        		.withELCODETAILS(hh.ELCODETAILS())
        		.withmultimediaAttachments(hh.multimediaAttachments())
        		.withDetails(hh.details())
        		.withLOCATIONID(hh.getDetail(LOCATION_NAME))
        		.withTODAY(hh.getDetail(REFERENCE_DATE))
        		.withSTART(hh.getDetail(START_DATE))
        		.withEND(hh.getDetail(END_DATE))
        		.withFWWOMAGE(hh.getELCODetail(FW_WOMAGE))
        		.withFWBIRTHDATE(hh.getELCODetail(FW_BIRTHDATE))
        		.withid(hh.getELCODetail(id))
        		.withFWWOMLNAME(hh.getELCODetail(FW_WOMLNAME))
        		.withFWWOMFNAME(hh.getELCODetail(FW_WOMFNAME))
        		.withJiVitAHHID(hh.getELCODetail(FW_JiVitAHHID))
        		.withFWGENDER(hh.getELCODetail(FW_GENDER))
        		.withFWWOMBID(hh.getELCODetail(FW_WOMBID))
        		.withFWWOMNID(hh.getELCODetail(FW_WOMNID))
        		.withFWHUSNAME(hh.getELCODetail(FW_HUSNAME))
        		.withFWELIGIBLE(hh.getELCODetail(FW_ELIGIBLE))
        		.withFWDISPLAYAGE(hh.getELCODetail(FW_DISPLAY_AGE));
        	            
        	hhRegisterEntries.add(hhRegisterEntry);
        }
        logger.info("HHRegister data");
        return new HHRegister(hhRegisterEntries);
	}	*/

	/*public HHRegister getHHRegister(String type, String startKey, String endKey)
	{
		long start = getTimestamp(startKey);		
		long end = getTimestamp(endKey);
		
		ArrayList<HHRegisterEntry> hhRegisterEntries = new ArrayList<>();
        List<HouseHold> hhs = allHouseHolds.allHHsCreatedBetween2Dates(type,start, end);
        
        for (HouseHold hh : hhs) {	
    		
        	HHRegisterEntry hhRegisterEntry = new HHRegisterEntry()
        		.withCASEID(hh.caseId()) 
        		.withINSTANCEID(hh.INSTANCEID())
        		.withPROVIDERID(hh.PROVIDERID())
        		//.withLOCATIONID(hh.LOCATIONID())
        		.withTODAY(hh.TODAY())
        		.withFWNHREGDATE(hh.FWNHREGDATE())
        		.withFWGOBHHID(hh.FWGOBHHID())
        		.withFWJIVHHID(hh.FWJIVHHID())
        		.withFWCOUNTRY(hh.FWCOUNTRY())
                .withFWDIVISION(hh.FWDIVISION())
                .withFWDISTRICT(hh.FWDISTRICT())
                .withFWUPAZILLA(hh.FWUPAZILLA())
                .withFWUNION(hh.FWUNION())
                .withFWWARD(hh.FWWARD())
                .withFWSUBUNIT(hh.FWSUBUNIT())
                .withFWMAUZA_PARA(hh.FWMAUZA_PARA())
        		.withFWNHHHGPS(hh.FWNHHHGPS())
        		.withform_name(hh.form_name())
        		.withFWHOHFNAME(hh.FWHOHFNAME())
        		.withFWHOHLNAME(hh.FWHOHLNAME())
        		.withFWHOHBIRTHDATE(hh.FWHOHBIRTHDATE())
        		.withFWHOHGENDER(hh.FWHOHGENDER())
        		.withFWNHHMBRNUM(hh.FWNHHMBRNUM())
        		.withFWNHHMWRA(hh.FWNHHMWRA())
        		.withELCO(hh.ELCO())
        		.withuser_type(hh.user_type())
        		.withexternal_user_ID(hh.external_user_ID())
        		.withcurrent_formStatus(hh.current_formStatus())
        		.withELCODETAILS(hh.ELCODETAILS())
        		.withmultimediaAttachments(hh.multimediaAttachments())
        		.withDetails(hh.details())
        		.withLOCATIONID(hh.getDetail(LOCATION_NAME))
        		.withTODAY(hh.getDetail(REFERENCE_DATE))
        		.withSTART(hh.getDetail(START_DATE))
        		.withEND(hh.getDetail(END_DATE))
        		.withFWWOMAGE(hh.getELCODetail(FW_WOMAGE))
        		.withFWBIRTHDATE(hh.getELCODetail(FW_BIRTHDATE))
        		.withid(hh.getELCODetail(id))
        		.withFWWOMLNAME(hh.getELCODetail(FW_WOMLNAME))
        		.withFWWOMFNAME(hh.getELCODetail(FW_WOMFNAME))
        		.withJiVitAHHID(hh.getELCODetail(FW_JiVitAHHID))
        		.withFWGENDER(hh.getELCODetail(FW_GENDER))
        		.withFWWOMBID(hh.getELCODetail(FW_WOMBID))
        		.withFWWOMNID(hh.getELCODetail(FW_WOMNID))
        		.withFWHUSNAME(hh.getELCODetail(FW_HUSNAME))
        		.withFWELIGIBLE(hh.getELCODetail(FW_ELIGIBLE))
        		.withFWDISPLAYAGE(hh.getELCODetail(FW_DISPLAY_AGE));
        	            
        	hhRegisterEntries.add(hhRegisterEntry);
        }
        return new HHRegister(hhRegisterEntries);
	}	*/
	public HouseHold getHouseholdById(String id){
		return allHouseHolds.get(id);
	}
}