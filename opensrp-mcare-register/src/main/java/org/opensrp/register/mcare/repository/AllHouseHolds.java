package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.HouseHold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllHouseHolds extends MotechBaseRepository<HouseHold> {

	private static Logger logger = LoggerFactory.getLogger(AllHouseHolds.class);

	@Autowired
	public AllHouseHolds(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(HouseHold.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public HouseHold findByCaseId(String caseId) {
		List<HouseHold> houseHolds = queryView("by_caseId", caseId);
		if (houseHolds == null || houseHolds.isEmpty()) {
			return null;
		}
		return houseHolds.get(0);
	}
/*
	@View(name = "all_households", map = "function(doc) { if (doc.type === 'HouseHold') { emit(doc.PROVIDERID, doc.caseId); } }")
	public List<HouseHold> findAllHouseHolds() {
		return db.queryView(createQuery("all_households").includeDocs(true),
				HouseHold.class);
	}*/
	/*@View(name = "get_all_household", map = "function(doc) { if (doc.type === 'HouseHold' && doc.FWNHHHGPS) { emit(doc._id, [doc.FWNHHHGPS.split(' ')[0],doc.FWNHHHGPS.split(' ')[1],doc.ELCO,doc.FWMAUZA_PARA,doc.FWDIVISION,doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION,doc.FWWARD]); } }")
	public List<HouseHold> allHouseHolds() {
		return db.queryView(createQuery("get_all_household").includeDocs(true),
				HouseHold.class);
	}*/

	/*@View(name = "all_open_hhs_for_provider", map = "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID) { emit(doc.PROVIDERID,doc._id ); } }")
	public List<HouseHold> allOpenHHsForProvider(String providerId) {
		return db.queryView(
				createQuery("all_open_hhs_for_provider").key(providerId)
						.includeDocs(true), HouseHold.class);
	}*/

	/*@View(name = "all_hhs_prev_7_days", map = "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID && doc.TODAY) { emit(doc.PROVIDERID, doc.TODAY); } }")
	public List<HouseHold> allHHsVisited7Days(String providerId) {

		LocalDate today = LocalDate.now();
		
		List<HouseHold> houseHolds = db.queryView(
				createQuery("all_hhs_prev_7_days")
						.startKey(today.minusDays(100))
						.endKey(today)
						//.key(providerId)
						.includeDocs(true), HouseHold.class);

		return houseHolds;
	}*/
	
	@View(name = "count_last_four_month", map = "function(doc) { if (doc.type === 'HouseHold' && doc.SUBMISSIONDATE){ var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time) { emit(doc.SUBMISSIONDATE, doc.SUBMISSIONDATE); }}}")
	public ViewResult HouseholdBetweenTwoDatesAsViewResult(Long startTime){
		
		System.out.println(startTime);
		ViewResult vr = db.queryView(
				createQuery("count_last_four_month")
				.startKey(startTime)
				.endKey(System.currentTimeMillis())
				.includeDocs(false));
		return vr;
		
	}
	public int totalHousehold() {       
	     return db.queryView(createQuery("all")).getRows().get(0).getValueAsInt();
	}
	 
	@View(name = "created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'HouseHold' && doc.SUBMISSIONDATE && doc.FWDISTRICT && doc.FWUPAZILLA && doc.FWUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION], doc.SUBMISSIONDATE)} } }")
	public List<HouseHold> allHHsCreatedLastFourMonthsByLocation(String startKey, String endKey){		
		List<HouseHold> households =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), HouseHold.class);
		
		return households;
	}
	
	public ViewResult allHHsCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allHHsCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	@View(name = "created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'HouseHold' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.FWDISTRICT && doc.FWUPAZILLA && doc.FWUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.PROVIDERID,doc.FWDISTRICT,doc.FWUPAZILLA,doc.FWUNION], doc.SUBMISSIONDATE)} } }")
	public List<HouseHold> allHHsCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<HouseHold> households =  db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), HouseHold.class);
			
		return households;
	}
	
	public List<HouseHold> allHHsCreatedLastFourMonthsByLocationAnother(String District, String Upazilla, String Union){
		ComplexKey complexKey = ComplexKey.of(District, Upazilla, ComplexKey.emptyObject());		
		List<HouseHold> households =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.key(complexKey)
				.includeDocs(true), HouseHold.class);
		
		return households;
	}
	
	/*@View(name = "created_in_between_2_dates", map = "function(doc) { if(doc.type === 'HouseHold' && doc.type && doc.SUBMISSIONDATE) { emit( [doc.type, doc.SUBMISSIONDATE], null); } }")
	public List<HouseHold> allHHsCreatedBetween2Dates(String type, long startKey, long endKey){
		ComplexKey start = ComplexKey.of(type,startKey);
		ComplexKey end = ComplexKey.of(type,endKey);
		List<HouseHold> hhs =  db.queryView(
				createQuery("created_in_between_2_dates")
				.startKey(start)
				.endKey(end)
				.includeDocs(true), HouseHold.class);
		//System.out.println(hhs.toString());	
		return hhs;
	}*/
	
	/*// map reduce query
    @View(name = "householdCount", map = "function(doc) { if (doc.type === 'HouseHold') { emit(doc.id); } }",reduce="_count")
    public int countHouseHolds() {       
    	ViewResult result =  db.queryView(createQuery("householdCount")); 
        int  count = 0;
        if(!result.getRows().isEmpty()){
            count = result.getRows().get(0).getValueAsInt();
        }
        return count;
    }
    */
    @View(name = "created_newhh_in_between_2_dates", map = "function(doc) { if(doc.type === 'HouseHold' && doc.type && doc.SUBMISSIONDATE) { " +
    		"if(doc.ELCODETAILS.length>0){for(var key in doc.ELCODETAILS) { if(doc.ELCODETAILS[key].form_name === 'FWNewHH') { "+
            "emit([doc.type, doc.SUBMISSIONDATE,doc.user_type], [doc.ELCO,doc.PROVIDERID,doc.TODAY,doc.START,doc.END,doc.FWNHREGDATE,doc.FWGOBHHID,doc.FWJIVHHID,doc.FWUNION,doc.FWWARD,doc.FWSUBUNIT,doc.FWMAUZA_PARA,doc.FWHOHFNAME,doc.FWHOHBIRTHDATE,doc.FWHOHGENDER,doc.FWNHHMBRNUM,doc.FWNHHMWRA,doc.details.received_time,doc.INSTANCEID,doc.caseId,doc.external_user_ID,doc.ELCODETAILS[key].TODAY,doc.ELCODETAILS[key].FWWOMFNAME,doc.ELCODETAILS[key].FWBIRTHDATE,doc.ELCODETAILS[key].FWWOMAGE,doc.ELCODETAILS[key].FWCWOMSTRMEN,doc.ELCODETAILS[key].FWCWOMHUSLIV,doc.ELCODETAILS[key].FWCWOMHUSALV,doc.ELCODETAILS[key].FWCWOMHUSSTR,doc.ELCODETAILS[key].FWELIGIBLE,doc.ELCODETAILS[key].FWWOMANYID,doc.ELCODETAILS[key].FWWOMNID,doc.ELCODETAILS[key].FWWOMBID,doc.ELCODETAILS[key].FWHUSNAME,doc.ELCODETAILS[key].FWWOMGPS,doc.ELCO]); " +
            "}}}else{ " +            
            "emit( [doc.type, doc.SUBMISSIONDATE,doc.user_type], [doc.ELCO,doc.PROVIDERID,doc.TODAY,doc.START,doc.END,doc.FWNHREGDATE,doc.FWGOBHHID,doc.FWJIVHHID,doc.FWUNION,doc.FWWARD,doc.FWSUBUNIT,doc.FWMAUZA_PARA,doc.FWHOHFNAME,doc.FWHOHBIRTHDATE,doc.FWHOHGENDER,doc.FWNHHMBRNUM,doc.FWNHHMWRA,doc.details.received_time,doc.INSTANCEID,doc.caseId,doc.external_user_ID,doc.ELCO]); " +            
            "}" +
            "} }")
    public ViewResult allHHsCreatedBetween2Date(String type, long startKey, long endKey,String userType){
        ComplexKey start = ComplexKey.of(type,startKey,userType);
        ComplexKey end = ComplexKey.of(type,endKey,userType);
        ViewResult vr=  db.queryView(
                createQuery("created_newhh_in_between_2_dates")
                .startKey(start)
                .endKey(end)
                .includeDocs(false));           
        return vr;
        
    }
    
    @View(name = "created_census_in_between_2_dates", map = "function(doc) { if(doc.type === 'HouseHold' && doc.type && doc.SUBMISSIONDATE) { " +
            "if(doc.ELCODETAILS.length>0){for(var key in doc.ELCODETAILS) { if(doc.ELCODETAILS[key].form_name === 'Census') { "+
            "emit([doc.type, doc.SUBMISSIONDATE,doc.user_type], [doc.ELCO,doc.current_formStatus,doc.PROVIDERID,doc.ELCODETAILS[key].TODAY,doc.ELCODETAILS[key].start,doc.ELCODETAILS[key].end,doc.details.received_time,doc.external_user_ID,doc.INSTANCEID,doc.caseId,doc.ELCODETAILS[key].FWCENDATE,doc.ELCODETAILS[key].FWCENSTAT,doc.ELCODETAILS[key].GOBHHID,doc.ELCODETAILS[key].JiVitAHHID,doc.ELCODETAILS[key].FWWOMUNION,doc.ELCODETAILS[key].FWWOMWARD,doc.ELCODETAILS[key].FWWOMSUBUNIT,doc.ELCODETAILS[key].FWWOMMAUZA_PARA,doc.ELCODETAILS[key].ELCO,doc.ELCODETAILS[key].existing_ELCO,doc.ELCODETAILS[key].new_ELCO,doc.ELCODETAILS[key].FWCENDATE,doc.ELCODETAILS[key].FWWOMFNAME,doc.ELCODETAILS[key].FWBIRTHDATE,doc.ELCODETAILS[key].FWWOMAGE,doc.ELCODETAILS[key].FWCWOMSTRMEN,doc.ELCODETAILS[key].FWCWOMHUSLIV,doc.ELCODETAILS[key].FWCWOMHUSALV,doc.ELCODETAILS[key].FWCWOMHUSSTR,doc.ELCODETAILS[key].FWELIGIBLE,doc.ELCODETAILS[key].FWWOMANYID,doc.ELCODETAILS[key].FWWOMNID,doc.ELCODETAILS[key].FWWOMBID,doc.ELCODETAILS[key].FWHUSNAME,doc.ELCODETAILS[key].FWWOMGPS,doc.ELCO]); " +
            "}}}}}")
    public ViewResult allCensusCreatedBetween2Date(String type, long startKey, long endKey,String userType){
        ComplexKey start = ComplexKey.of(type,startKey,userType);
        ComplexKey end = ComplexKey.of(type,endKey,userType);
        ViewResult vr=  db.queryView(
                createQuery("created_census_in_between_2_dates")
                .startKey(start)
                .endKey(end)
                .includeDocs(false));        
        return vr;
        
    }
    
    @View(name = "house_by_client_version", map = "function(doc) { if (doc.type === 'HouseHold') { emit([doc.type, doc.clientVersion], null); } }")
    public List<HouseHold> findByTypeAndTimeStamp(String type, long timeStamp) {
        ComplexKey startKey = ComplexKey.of(type, timeStamp + 1);
        ComplexKey endKey = ComplexKey.of(type, Long.MAX_VALUE);
        return db.queryView(createQuery("house_by_client_version").descending(true).key("clientVersion").startKey(startKey).endKey(endKey).includeDocs(true), HouseHold.class);
    }
    
    @View(name = "household_by_mouzaPara", map = "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID && doc.FWMAUZA_PARA) { emit(doc.FWMAUZA_PARA,null ); } }")
    public List<HouseHold> getByMouzaPara(String mouzaPara){
    	return db.queryView(
				createQuery("household_by_mouzaPara").key(mouzaPara)
						.includeDocs(true), HouseHold.class);
    }
}
