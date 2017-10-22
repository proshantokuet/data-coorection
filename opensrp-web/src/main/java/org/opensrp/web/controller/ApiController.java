package org.opensrp.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.opensrp.dto.FieldValue;
import org.opensrp.dto.register.HHRegisterDTO;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class ApiController {

	private LuceneHouseHoldService luceneHouseHoldService;
	
	@Autowired
	public ApiController(LuceneHouseHoldService luceneHouseHoldService)
	{
		this.luceneHouseHoldService = luceneHouseHoldService;
	}
	
	
}
