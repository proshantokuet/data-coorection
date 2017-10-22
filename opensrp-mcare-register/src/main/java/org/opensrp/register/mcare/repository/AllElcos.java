package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Elco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllElcos extends MotechBaseRepository<Elco> {

	@Autowired
	public AllElcos(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Elco.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Elco findByCaseId(String caseId) {
		List<Elco> elcos = queryView("by_caseId", caseId);
		if (elcos == null || elcos.isEmpty()) {
			return null;
		}
		return elcos.get(0);
	}

	public boolean exists(String caseId) {
		return findByCaseId(caseId) != null;
	}
	public void close(String caseId) {
		Elco elco = findByCaseId(caseId);
		update(elco.setIsClosed(true));
	}
	
	
	@View(name = "created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.PROVIDERID,doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allElcosCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	@View(name = "created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time){ emit([doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allElcosCreatedLastFourMonthsByLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
		
		return elcos;
	}	
	
	@View(name = "elco_count_last_four_month", map = "function(doc) { if (doc.type === 'Elco' && doc.SUBMISSIONDATE) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time ) {emit(doc.SUBMISSIONDATE, doc.SUBMISSIONDATE); }} }")
	public ViewResult elcoBetweenTwoDatesAsViewResult(Long startTime){
		Long endTime = System.currentTimeMillis();
		ViewResult vr = db.queryView(
				createQuery("elco_count_last_four_month")
				.startKey(startTime)
				.endKey(endTime)
				.includeDocs(false));
		return vr;
		
	}
	@View(name = "pw_count_last_four_month", map = "function(doc) { if (doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.PSRFDETAILS != '' && doc.details.FWPSRPREGSTS == 1)  {emit(doc.SUBMISSIONDATE, doc.SUBMISSIONDATE); }}")
	public ViewResult pregnantElcoBetweenTwoDatesAsViewResult(Long startTime){
		Long endTime = System.currentTimeMillis();
		return  db.queryView(
				createQuery("pw_count_last_four_month")
				.startKey(startTime)
				.endKey(endTime)
				.includeDocs(true));
	}
	
	
	public ViewResult allElcosCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allElcosCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	@View(name = "mother_created_in_last_4_months_by_provider_and_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.PROVIDERID && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time && doc.PSRFDETAILS != '' && doc.details.FWPSRPREGSTS == 1){ emit([doc.PROVIDERID,doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allMothersCreatedLastFourMonthsByProviderAndLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("mother_created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	@View(name = "mother_created_in_last_4_months_by_location", map = "function(doc) { if(doc.type === 'Elco' && doc.SUBMISSIONDATE && doc.FWWOMDISTRICT && doc.FWWOMUPAZILLA && doc.FWWOMUNION) { var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3, 0); var time = y.getTime(); if(doc.SUBMISSIONDATE > time && doc.PSRFDETAILS != '' && doc.details.FWPSRPREGSTS == 1){ emit([doc.FWWOMDISTRICT,doc.FWWOMUPAZILLA,doc.FWWOMUNION], doc.SUBMISSIONDATE)} } }")
	public List<Elco> allMothersCreatedLastFourMonthsByLocation(String startKey, String endKey){
		List<Elco> elcos =  db.queryView(
				createQuery("mother_created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(true), Elco.class);
			
		return elcos;
	}
	
	
	public ViewResult allMothersCreatedLastFourMonthsByLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("mother_created_in_last_4_months_by_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	public ViewResult allMothersCreatedLastFourMonthsByProviderAndLocationViewResult(String startKey, String endKey){
		
		ViewResult vr = db.queryView(
				createQuery("mother_created_in_last_4_months_by_provider_and_location")
				.rawStartKey(startKey)
				.rawEndKey(endKey)
				.includeDocs(false));
		
		return vr;
	}
	
	
	@View(name = "created_elco_in_between_2_dates", map = "function(doc) { if(doc.type === 'Elco' && doc.type && doc.SUBMISSIONDATE) { " +
    		"if(doc.PSRFDETAILS.length>0){for(var key in doc.PSRFDETAILS) { "+
    		"emit([doc.type, doc.SUBMISSIONDATE], [doc.ELCO,doc.GOBHHID,doc.JiVitAHHID,doc.FWWOMUNION,doc.FWWOMWARD,doc.FWWOMSUBUNIT,doc.FWWOMMAUZA_PARA,doc.FWWOMRETYPENID,doc.FWWOMRETYPEBID,doc.FWWOMAGE,doc.FWWOMFNAME,doc.FWHUSNAME,doc.details.external_user_ID,doc.PROVIDERID,doc.PSRFDETAILS[key].start,doc.PSRFDETAILS[key].end,doc.PSRFDETAILS[key].today,doc.PSRFDETAILS[key].current_formStatus,doc.PSRFDETAILS[key].start,doc.PSRFDETAILS[key].FWPSRDATE,doc.PSRFDETAILS[key].FWPSRSTS,doc.PSRFDETAILS[key].FWPSRLMP,doc.PSRFDETAILS[key].FWPSRPREGSTS,doc.PSRFDETAILS[key].FWPSRHUSPREGWTD,doc.PSRFDETAILS[key].FWPSREVRPREG,doc.PSRFDETAILS[key].FWPSRTOTBIRTH,doc.PSRFDETAILS[key].FWPSRNBDTH,doc.PSRFDETAILS[key].FWPSRPRSB,doc.PSRFDETAILS[key].FWPSRPRMC,doc.PSRFDETAILS[key].FWPSRPREGTWYRS,doc.PSRFDETAILS[key].FWPSRPRVPREGCOMP,doc.PSRFDETAILS[key].FWPSRPRCHECKS,doc.PSRFDETAILS[key].FWPSRANM,doc.PSRFDETAILS[key].FWPSRHBP,doc.PSRFDETAILS[key].FWPSRDBT,doc.PSRFDETAILS[key].FWPSRTHY,doc.PSRFDETAILS[key].FWPSRVDGMEM,doc.PSRFDETAILS[key].FWPSRWOMEDU,doc.PSRFDETAILS[key].FWPSRHHLAT,doc.PSRFDETAILS[key].FWPSRHHRICE,doc.PSRFDETAILS[key].FWPSRPHONE,doc.PSRFDETAILS[key].FWPSRPHONENUM,doc.PSRFDETAILS[key].FWPSRMUAC,doc.PSRFDETAILS[key].FWVG,doc.PSRFDETAILS[key].FWHRP,doc.PSRFDETAILS[key].FWHR_PSR,doc.PSRFDETAILS[key].FWFLAGVALUE,doc.PSRFDETAILS[key].FWSORTVALUE,doc.details.FWPSRPREGWTD,doc.details.received_time,doc.INSTANCEID,doc.caseId,doc.ELCO]); }}" +
    		"}}")
    
	public ViewResult allElcosCreatedBetween2Date(String type, long startKey, long endKey){
        ComplexKey start = ComplexKey.of(type,startKey);
        ComplexKey end = ComplexKey.of(type,endKey);
        ViewResult vr=  db.queryView(
                createQuery("created_elco_in_between_2_dates")
                .startKey(start)
                .endKey(end)
                .includeDocs(false));           
        return vr;
        
    }
	
    public int totalElco() {       
       return db.queryView(createQuery("all")).getRows().get(0).getValueAsInt();
    }
    
	@View(name = "created_miscensus_in_between_2_dates", map = "function(doc) { if(doc.type === 'Elco' && doc.type && doc.SUBMISSIONDATE && doc.details.MisToday && doc.details.MisToday != null && doc.details.MisToday != '' ) { " +
    		"emit([doc.type, doc.SUBMISSIONDATE], [doc.ELCO,doc.PROVIDERID,doc.FWWOMMAUZA_PARA,doc.details.MisToday,doc.GOBHHID,doc.JiVitAHHID,doc.FWWOMUNION,doc.FWWOMWARD,doc.FWWOMSUBUNIT,doc.FWWOMMAUZA_PARA,doc.FWWOMRETYPENID,doc.FWWOMRETYPEBID,doc.FWWOMAGE,doc.FWWOMFNAME,doc.FWHUSNAME,doc.details.external_user_ID,doc.PROVIDERID,doc.details.mis_census_current_formStatus,doc.details.MisStart,doc.details.MisStart,doc.details.MisEnd,doc.details.FWMISCENSUSDATE,doc.details.FWCOUPLENUM,doc.details.FWTETSTAT,doc.details.FWMARRYDATE,doc.details.FWCHILDALIVEB,doc.details.FWCHILDALIVEG,doc.details.received_time,doc.INSTANCEID,doc.caseId,doc.ELCO]);" +
    		"}}")
    
	public ViewResult allMisCensusCreatedBetween2Date(String type, long startKey, long endKey){
        ComplexKey start = ComplexKey.of(type,startKey);
        ComplexKey end = ComplexKey.of(type,endKey);
        ViewResult vr=  db.queryView(
                createQuery("created_miscensus_in_between_2_dates")
                .startKey(start)
                .endKey(end)
                .includeDocs(false));         
        return vr;
        
    }

	@View(name = "created_miselco_in_between_2_dates", map = "function(doc) { if(doc.type === 'Elco' && doc.type && doc.SUBMISSIONDATE && doc.MISDETAILS.length>0) { " +
    		"for(var key in doc.MISDETAILS) { "+
    		"emit([doc.type, doc.SUBMISSIONDATE], [doc.ELCO,doc.PROVIDERID,doc.GOBHHID,doc.JiVitAHHID,doc.FWWOMUNION,doc.FWWOMWARD,doc.FWWOMSUBUNIT,doc.FWWOMMAUZA_PARA,doc.FWWOMRETYPENID,doc.FWWOMRETYPEBID,doc.FWWOMAGE,doc.FWWOMFNAME,doc.FWHUSNAME,doc.details.external_user_ID,doc.PROVIDERID,doc.MISDETAILS[key].today,doc.MISDETAILS[key].start,doc.MISDETAILS[key].end,doc.MISDETAILS[key].mis_elco_current_formStatus,doc.MISDETAILS[key].start,doc.MISDETAILS[key].FWMISELCODATE,doc.MISDETAILS[key].FWPMISBIRTHCTRL,doc.MISDETAILS[key].FWMISBCSOURCE,doc.details.received_time,doc.INSTANCEID,doc.caseId,doc.ELCO]); }}" +
    		"}")
    
	public ViewResult allMisElcoCreatedBetween2Date(String type, long startKey, long endKey){
        ComplexKey start = ComplexKey.of(type,startKey);
        ComplexKey end = ComplexKey.of(type,endKey);
        ViewResult vr=  db.queryView(
                createQuery("created_miselco_in_between_2_dates")
                .startKey(start)
                .endKey(end)
                .includeDocs(false));           
        return vr;
        
    }
	

	 @View(name = "elco_by_relationalId", map = "function(doc) { if (doc.type === 'Elco' && doc.details.relationalid) { emit(doc.details.relationalid,null ); } }")
	 public List<Elco> getByRelationalId(String relationalId){		
		 List<Elco> elcos =  db.queryView(
					createQuery("elco_by_relationalId").key(relationalId)
							.includeDocs(true), Elco.class);
		 if (elcos == null || elcos.isEmpty()) {
				return null;
			}
		return elcos;
	 }
}
