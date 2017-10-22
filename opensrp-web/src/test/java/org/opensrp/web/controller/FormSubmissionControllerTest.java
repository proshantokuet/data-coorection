package org.opensrp.web.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.dto.form.MultimediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration({ "classpath:spring/applicationContext-opensrp-web.xml" })
public class FormSubmissionControllerTest {
	/*@Autowired
	WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testUpload() throws Exception {

	            String endpoint =  "htpp://localhost:9979/multimedia-file";

	            FileInputStream fis = new FileInputStream("/home/julkar/nain/image.jpeg");
	            MockMultipartFile multipartFile = new MockMultipartFile("file", fis);

	            HashMap<String, String> contentTypeParams = new HashMap<String, String>();
	            contentTypeParams.put("boundary", "265001916915724");
	            MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
	            List<MultimediaDTO> multimediaList = new ArrayList<MultimediaDTO>(); 
	            
	            MultimediaDTO multimediaDTO = new MultimediaDTO("c2030b46-5fe5-4859-a699-16ad4d8d148c", "opensrp", "image1.jpg");
	            multimediaList.add(multimediaDTO);
	            
	          	            
	            mockMvc.perform(
	                    post(endpoint)
	                    .content(multipartFile.getBytes())
	                    .contentType(mediaType))
	                    .andExpect(status().isOk());
	}*/
	
	
}
