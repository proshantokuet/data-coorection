package org.opensrp.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.opensrp.register.mcare.service.DataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class DataExportController {
	@Autowired
	private DataExportService dataExportService;
	
	
	DataExportController(){
		
	}
	@RequestMapping(value="/export")
    @ResponseBody
	public ResponseEntity<String> dataExport(@RequestParam String formName,@RequestParam String start_date,@RequestParam String end_date,@RequestParam String user,HttpServletResponse response) 
	{		
		dataExportService.exports(response,formName,start_date,end_date,user);		
		return  new ResponseEntity<>(new Gson().toJson(dataExportService.getExportsByUser(user)), HttpStatus.OK);
	}
	@RequestMapping(value="/all-export")
    @ResponseBody
	public ResponseEntity<String> AllDataExport(@RequestParam String user){	
			
		return  new ResponseEntity<>(new Gson().toJson(dataExportService.getExportsByUser(user)), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete-export")
    @ResponseBody
	public ResponseEntity<String> deleteDataExport(@RequestParam String id){
		String msg="1";
		try{
			dataExportService.deleteExport(id);			
		}catch(Exception e){
			e.printStackTrace();
			msg = "0";
		}
		return  new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
}
