package org.opensrp.service;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.repository.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp.xml")
public class MultimediaServiceTest {
	
	@Mock
	private MultimediaService multimediaService;

	@Autowired
	private MultimediaRepository multimediaRepository;
	
	@Autowired
	@Value("#{opensrp['multimedia.directory.name']}") 
	private String multimediaDirPath;
	
	@Before
	public void setUp() throws Exception
	{
		initMocks(this);
		multimediaService = new MultimediaService(multimediaRepository, multimediaDirPath);
	}
	
	@Test
	public void shouldDoIt()
	{
		
	}
	/*
	@Test
	public void shouldSaveMultimediaFile() throws FileNotFoundException
	{
		System.out.println("multimediaDirPath: " + multimediaDirPath); 

 MultimediaDTO multimedia = new MultimediaDTO("1234567891", "opensrp","image/jpeg", multimediaDirPath + "1234567891.jpg","dp");		
		  FileInputStream fis = new FileInputStream(multimediaDirPath + "/opensrp/images" + "/"  + "1234567890.jpg");		  
          MultipartFile multipartFile = null;
          
		try {
			multipartFile = new MockMultipartFile("file", fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String status = multimediaService.saveMultimediaFile(multimedia,multipartFile);
		
		Assert.assertEquals("success", status);
		
	}
	@Test
	public void shouldGetMultimediaFiles() throws FileNotFoundException
	{
	System.out.println("multimediaDirPath: " + multimediaDirPath); 		
 MultimediaDTO multimediaDTO = new MultimediaDTO("1234567890", "opensrp","image/jpeg", multimediaDirPath + "1234567890.jpg","dp");
		
		Multimedia expectedMultimedia = new Multimedia()
		.withCaseId(multimediaDTO.caseId())
		.withProviderId(multimediaDTO.providerId())
		.withContentType(multimediaDTO.contentType())
		.withFilePath(multimediaDTO.filePath())
		.withFileCategory(multimediaDTO.fileCategory());
		
		FileInputStream fis = new FileInputStream(multimediaDirPath + "/opensrp/images" + "/" + "1234567890.jpg");
		  
          MultipartFile multipartFile = null;
          
		try {
			multipartFile = new MockMultipartFile("file", fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean status = multimediaService.uploadFile(multimediaDTO, multipartFile);
		
		if(status)
			multimediaRepository.add(expectedMultimedia);
		
		List<Multimedia> multimediaFiles = multimediaService.getMultimediaFiles("opensrp");
		
		for(Multimedia actualMultimedia : multimediaFiles)
		{
			if(actualMultimedia.getCaseId().equals(multimediaDTO.caseId()))
					Assert.assertEquals(expectedMultimedia.getFilePath(),actualMultimedia.getFilePath());
		}
	}
*/}
