package org.opensrp.web.controller;

import java.io.IOException;
import java.util.List;

import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.CountServiceDTOForChart;
import org.opensrp.dto.FormCountDTO;
import org.opensrp.dto.register.ANC_RegisterDTO;
import org.opensrp.dto.register.Child_RegisterDTO;
import org.opensrp.dto.register.ELCORegisterDTO;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.register.mcare.ANCRegister;
import org.opensrp.register.mcare.ChildRegister;
import org.opensrp.register.mcare.ELCORegister;
import org.opensrp.register.mcare.HHRegister;
import org.opensrp.register.mcare.mapper.ANCRegisterMapper;
import org.opensrp.register.mcare.mapper.ChildRegisterMapper;
import org.opensrp.register.mcare.mapper.ELCORegisterMapper;
import org.opensrp.register.mcare.mapper.HHRegisterMapper;
import org.opensrp.register.mcare.service.ANCRegisterService;
import org.opensrp.register.mcare.service.BNFService;
import org.opensrp.register.mcare.service.ChildRegisterService;
import org.opensrp.register.mcare.service.ELCORegisterService;
import org.opensrp.register.mcare.service.HHRegisterService;
import org.opensrp.register.mcare.service.MultimediaRegisterService;
import org.opensrp.rest.register.dto.CommonDTO;
import org.opensrp.rest.register.dto.ElcoDTO;
import org.opensrp.rest.register.dto.HouseholdDTO;
import org.opensrp.rest.register.dto.MotherDTO;
import org.opensrp.rest.repository.LuceneMotherRepository;
import org.opensrp.rest.services.LuceneElcoService;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneMotherService;
import org.opensrp.service.DataCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;


@Controller
public class RegisterController {

	private ELCORegisterService ecRegisterService;
	private HHRegisterService hhRegisterService;
	private ANCRegisterService ancRegisterService;
	private ChildRegisterService childRegisterService;
	private ELCORegisterMapper ecRegisterMapper;
	private HHRegisterMapper hhRegisterMapper;
	private ANCRegisterMapper ancRegisterMapper;
	private ChildRegisterMapper childRegisterMapper;
	private MultimediaRegisterService multimediaRegisterService;
	private DataCountService dataCountService;

	@Autowired
	private LuceneHouseHoldService luceneHouseHoldService;
	@Autowired
	private LuceneElcoService luceneElcoService;
	@Autowired
	private LuceneMotherService luceneMotherService;
	@Autowired
	private BNFService bNFService;
	
	@Autowired
	public RegisterController(ELCORegisterService ecRegisterService, ELCORegisterMapper ecRegisterMapper, HHRegisterService hhRegisterService,
			HHRegisterMapper hhRegisterMapper, ANCRegisterService ancRegisterService, ANCRegisterMapper ancRegisterMapper,
			ChildRegisterService childRegisterService, ChildRegisterMapper childRegisterMapper, MultimediaRegisterService multimediaRegisterService,
			DataCountService dataCountService) {
		this.ecRegisterService = ecRegisterService;
		this.hhRegisterService = hhRegisterService;
		this.ecRegisterMapper = ecRegisterMapper;
		this.hhRegisterMapper = hhRegisterMapper;
		this.ancRegisterService = ancRegisterService;
		this.ancRegisterMapper = ancRegisterMapper;
		this.childRegisterService = childRegisterService;
		this.childRegisterMapper = childRegisterMapper;
		this.multimediaRegisterService = multimediaRegisterService;
		this.dataCountService = dataCountService;
	}

	

	@RequestMapping(method = RequestMethod.GET, value = "/registers/anc")
	@ResponseBody
	public ResponseEntity<ANC_RegisterDTO> ancRegister(@RequestParam("start-date") String startdate, @RequestParam("end-date") String enddate) {
		ANCRegister ancRegister = ancRegisterService.getANCRegister("Mother", startdate, enddate);
		return new ResponseEntity<>(ancRegisterMapper.mapToDTO(ancRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/ancs")
	@ResponseBody
	public ResponseEntity<ANC_RegisterDTO> ancsRegister(@RequestParam("anm-id") String anmIdentifier) {
		ANCRegister ancRegister = ancRegisterService.getANCRegisterForProvider(anmIdentifier);
		return new ResponseEntity<>(ancRegisterMapper.mapToDTO(ancRegister), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/child")
	@ResponseBody
	public ResponseEntity<Child_RegisterDTO> childRegister(@RequestParam("start-date") String startdate, @RequestParam("end-date") String enddate) {
		ChildRegister childRegister = childRegisterService.getChildRegister("Child", startdate, enddate);
		return new ResponseEntity<>(childRegisterMapper.mapToDTO(childRegister), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registers/childs")
	@ResponseBody
	public ResponseEntity<Child_RegisterDTO> childsRegister(@RequestParam("anm-id") String anmIdentifier) {
		ChildRegister childRegister = childRegisterService.getChildRegisterForProvider(anmIdentifier);
		return new ResponseEntity<>(childRegisterMapper.mapToDTO(childRegister), HttpStatus.OK);

	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTO>>  getHouseHoldInformation(@RequestParam("anm-id") String provider,@RequestParam("start-month") String startMonth,@RequestParam("end-month") String endMonth,
    		@RequestParam("start-week") String startWeek,@RequestParam("end-week") String endtWeek,@RequestParam("type") String type){
    	return new ResponseEntity<>(dataCountService.getHHCountInformation(provider,startMonth,endMonth,startWeek,endtWeek,type), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/form-count")
    @ResponseBody
    public ResponseEntity<FormCountDTO>  getFormCountInformation(@RequestParam("anm-id") String provider){
    	return new ResponseEntity<>(dataCountService.getFormCount(provider), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/hh-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getHouseHoldInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
    														@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){    
    	return new ResponseEntity<>(dataCountService.getHHCountInformationForLastFourMonthAsWeekWise(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/hh-data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getHouseHoldInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
    														@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){    
    	return new ResponseEntity<>(dataCountService.getHHCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/elco-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getElcoInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
									@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getElcoCountInformationForLastFourMonthAsWeekWise(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/elco-data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getElcoInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
									@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getElcoCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/pw-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getPWInformation(@RequestParam("provider") String provider, @RequestParam("district") String district,
			@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getPregnantWomenCountInformationForLastFourMonthAsWeekWise(), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/registers/pw-data-count")
    @ResponseBody
    public ResponseEntity<List<CountServiceDTOForChart>>  getPWInformationForChart(@RequestParam("provider") String provider, @RequestParam("district") String district,
			@RequestParam("upazilla") String upazilla, @RequestParam("union") String union){
    	return new ResponseEntity<>(dataCountService.getMotherCountInformationForChart(provider, district, upazilla, union), HttpStatus.OK);
    }

	

	@RequestMapping(method = RequestMethod.GET, value = "/getMultimedia")
	@ResponseBody
	public ResponseEntity<String> getMultimedia() {
		// multimediaRegisterService.getMultimedia();
		return new ResponseEntity<>("Welcome to multimedia service", HttpStatus.OK);
	}
	

	//Search register data
	 /**
	  * @param queryParameters is a list of key.
	  * @param p is a number of page.
	  * @param limit is a data limit.
	  * @return all households match with specified key.
	  * */
	@RequestMapping(method = RequestMethod.GET, value="/household-search")
	@ResponseBody
	public ResponseEntity<CommonDTO<HouseholdDTO>> getHouseholdByKeys(@RequestParam MultiValueMap<String, String> queryParameters,@RequestParam int p,@RequestParam int limit) throws JsonParseException, JsonMappingException, IOException
	{
		CommonDTO<HouseholdDTO>  households  = luceneHouseHoldService.getData(queryParameters,p,limit);
		return new ResponseEntity<>(households, HttpStatus.OK);
	}
	/**		 
	* @param  @param queryParameters is a list of key.
	* @return total count of households
	* @throws IOException 
	* @throws JsonMappingException 
	* @throws JsonParseException 
	* */
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/get-household-count-by-keys")
	@ResponseBody
	public ResponseEntity<String> getHouseholdCountByKeys(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(luceneHouseHoldService.getDataCount(queryParameters)), HttpStatus.OK);
	}
		
	/**	 
	* @param id is a household id of couchdb auto incremented ID.
	* @return household details of a specified @id 	 * 
	* @throws IOException 
	* @throws JsonMappingException 
	* @throws JsonParseException 
	* */
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/get-household-details")
	@ResponseBody
	public ResponseEntity<String> getHouseholdById(@RequestParam String id) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(hhRegisterService.getHouseholdById(id)), HttpStatus.OK);
	}
	/**
	  * @param queryParameters is a list of key.
	  * @param p is a number of page.
	  * @param limit is a data limit.
	  * @return all households match with specified key.
	  * */
	@RequestMapping(method = RequestMethod.GET, value="/elco-search")
	@ResponseBody
	public ResponseEntity<CommonDTO<ElcoDTO>> getElcoByKeys(@RequestParam MultiValueMap<String, String> queryParameters,@RequestParam int p,@RequestParam int limit) throws JsonParseException, JsonMappingException, IOException
	{
		CommonDTO<ElcoDTO>  households  = luceneElcoService.getData(queryParameters,p,limit);
		return new ResponseEntity<>(households, HttpStatus.OK);
	}
	/**		 
	* @param  @param queryParameters is a list of key.
	* @return total count of households
	* @throws IOException 
	* @throws JsonMappingException 
	* @throws JsonParseException 
	* */
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/get-elco-count-by-keys")
	@ResponseBody
	public ResponseEntity<String> getElcoCountByKeys(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(luceneElcoService.getDataCount(queryParameters)), HttpStatus.OK);
	}
		
	/**	 
	* @param id is a household id of couchdb auto incremented ID.
	* @return household details of a specified @id 	 * 
	* @throws IOException 
	* @throws JsonMappingException 
	* @throws JsonParseException 
	* */
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/get-elco-details")
	@ResponseBody
	public ResponseEntity<String> getElcoById(@RequestParam String id) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(ecRegisterService.getElcoById(id)), HttpStatus.OK);
	}
	
	/**
	  * @param queryParameters is a list of key.
	  * @param p is a number of page.
	  * @param limit is a data limit.
	  * @return all mother match with specified key.
	  * */
	@RequestMapping(method = RequestMethod.GET, value="/mother-search")
	@ResponseBody
	public ResponseEntity<CommonDTO<MotherDTO>> getMotherByKeys(@RequestParam MultiValueMap<String, String> queryParameters,@RequestParam int p,@RequestParam int limit) throws JsonParseException, JsonMappingException, IOException
	{
		CommonDTO<MotherDTO> elcos  = luceneMotherService.getData(queryParameters,p,limit);
		return new ResponseEntity<>(elcos, HttpStatus.OK);
	}
	/**		 
	* @param  @param queryParameters is a list of key.
	* @return total count of mother
	* @throws IOException 
	* @throws JsonMappingException 
	* @throws JsonParseException 
	* */
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/get-mother-count-by-keys")
	@ResponseBody
	public ResponseEntity<String> getMotherCountByKeys(@RequestParam MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(luceneMotherService.getDataCount(queryParameters)), HttpStatus.OK);
	}
		
	/**	 
	* @param id is a mother id of couchdb auto incremented ID.
	* @return mother details of a specified @id 	 * 
	* @throws IOException 
	* @throws JsonMappingException 
	* @throws JsonParseException 
	* */
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/get-mother-details")
	@ResponseBody
	public ResponseEntity<String> getMotherById(@RequestParam String id) throws JsonParseException, JsonMappingException, IOException {
		return new ResponseEntity<>(new Gson().toJson(bNFService.getElcoById(id)), HttpStatus.OK);
	}
	
}
