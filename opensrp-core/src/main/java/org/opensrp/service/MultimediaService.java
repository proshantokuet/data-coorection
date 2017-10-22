package org.opensrp.service;

import java.io.File;
import java.util.List;

import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.repository.MultimediaRepository;
import org.opensrp.scheduler.HookedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MultimediaService {
	private static Logger logger = LoggerFactory.getLogger(MultimediaService.class
			.toString());

	private final MultimediaRepository multimediaRepository;
	private String multimediaDirName;
	private String multimediaDirPath;
	private  HookedEvent action;

	@Autowired
	public MultimediaService(MultimediaRepository multimediaRepository, @Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName) {
		this.multimediaRepository = multimediaRepository;
		this.multimediaDirName = multimediaDirName;
		this.multimediaDirPath = multimediaDirName;
	}

	public void setEvent(HookedEvent action){
		this.action = action;
	}
	
	public String saveMultimediaFile(MultimediaDTO multimediaDTO, MultipartFile file) {
		
		multimediaDirPath = multimediaDirName;
		
		boolean uploadStatus = uploadFile(multimediaDTO, file);
         
		String[] multimediaDirPathSplit =  multimediaDirPath.split("/", 3);
		String multimediaDirPathDB = File.separator + multimediaDirPathSplit[2];
		
		if (uploadStatus) {
			try {
				logger.info("Image path : " + multimediaDirPath);
				
				
				Multimedia multimediaFile = new Multimedia()
						.withCaseId(multimediaDTO.caseId())
						.withProviderId(multimediaDTO.providerId())
						.withContentType(multimediaDTO.contentType())
						.withFilePath(multimediaDirPathDB)
						.withFileCategory(multimediaDTO.fileCategory());
				
				List<Multimedia> existsMultimediaList =  multimediaRepository.findByCaseIdAndFileCategory(multimediaDTO.caseId(), multimediaDTO.fileCategory());
				
                if(existsMultimediaList.size()>0)
                {
            	    for (Multimedia existsMultimedia : existsMultimediaList)
            	    multimediaRepository.safeRemove(existsMultimedia);
                }
				
                multimediaRepository.add(multimediaFile);
                action.saveMultimediaToRegistry(multimediaFile);

				return "success";

			} catch (Exception e) {
				e.getMessage();
			}
		}

		return "fail";

	}

	public boolean uploadFile(MultimediaDTO multimediaDTO,
			MultipartFile multimediaFile) {
		 
		if (!multimediaFile.isEmpty()) {
			try {

				 multimediaDirPath += File.separator + multimediaDTO.providerId()+ File.separator;

				switch (multimediaDTO.contentType()) {
				
				case "application/octet-stream":
					String videoDirPath = multimediaDirPath += "videos";
					makeMultimediaDir(videoDirPath);
					multimediaDirPath += File.separator
							+ multimediaDTO.caseId() + "-" + multimediaDTO.fileCategory() + ".mp4";
					break;

				case "image/jpeg":
					String jpgImgDirPath = multimediaDirPath += "images";
					makeMultimediaDir(jpgImgDirPath);
					multimediaDirPath += File.separator
							+ multimediaDTO.caseId() + "-" + multimediaDTO.fileCategory() + ".jpg";
					break;

				case "image/gif":
					String gifImgDirPath = multimediaDirPath += "images";
					makeMultimediaDir(gifImgDirPath);
					multimediaDirPath += File.separator
							+ multimediaDTO.caseId() + "-" + multimediaDTO.fileCategory() + ".gif";
					break;

				case "image/png":
					String pngImgDirPath = multimediaDirPath += "images";
					makeMultimediaDir(pngImgDirPath);
					multimediaDirPath += File.separator
							+ multimediaDTO.caseId() + "-" + multimediaDTO.fileCategory() + ".png";
					break;

				default:
					String defaultDirPath = multimediaDirPath += "images";
					makeMultimediaDir(defaultDirPath);
					multimediaDirPath += File.separator
							+ multimediaDTO.caseId() + "-" + multimediaDTO.fileCategory() + ".jpg";
					break;

				}

				File multimediaDir = new File(multimediaDirPath);

				 multimediaFile.transferTo(multimediaDir);

			/*
			 byte[] bytes = multimediaFile.getBytes();
			 	
			 BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(multimediaDirPath));
				stream.write(bytes);
				stream.close();*/
				 
				return true;
				
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
    private void makeMultimediaDir(String dirPath)
    {
    	File file = new File(dirPath);
		 if(!file.exists())
			 file.mkdirs();
			 
    }
	public List<Multimedia> getMultimediaFiles(String providerId) {
		return multimediaRepository.all(providerId);
	}
	
	
}