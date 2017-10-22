package org.opensrp.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.dashboard.dto.PrivilegeDTO;
import org.opensrp.dashboard.domain.Privilege;
import org.opensrp.dashboard.repository.AllPrivileges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {
	
	private static Logger logger = LoggerFactory.getLogger(PrivilegeService.class);
	
	private AllPrivileges allPrivileges;
	
	@Autowired
	public PrivilegeService(AllPrivileges allPrivileges) {
		this.allPrivileges = allPrivileges;
	}
	
	public String addPrivilege(PrivilegeDTO privilegeDTO) {
		Privilege existingPrivilege = allPrivileges.privilegeByName(privilegeDTO.getName());
		if (existingPrivilege == null) {
			try {
				Privilege privilege = new Privilege();
				privilege.withName(privilegeDTO.getName());
				privilege.withStatus(privilegeDTO.getStatus() == null ? "Active" : privilegeDTO.getStatus());
				allPrivileges.add(privilege);
				return "1";
			}
			catch (Exception e) {
				return "0";
			}
		} else {
			return "2";
		}
	}
	
	public PrivilegeDTO getPrivilegeByName(String privilegeName) {
		logger.info("inside service");
		Privilege privilegeByName = allPrivileges.privilegeByName(privilegeName);
		logger.info("returned from repository class");
		if (privilegeByName != null) {
			logger.info("privilege found");
			PrivilegeDTO privilegeDTO = new PrivilegeDTO();
			privilegeDTO.withPrivilegeName(privilegeByName.getName());
			return privilegeDTO;
		} else {
			logger.info("privilege not found");
			return null;
		}
	}
	
	public ArrayList<PrivilegeDTO> getAllPrivileges() {
		List<Privilege> privileges = allPrivileges.privileges();
		ArrayList<PrivilegeDTO> privilegesDTO = new ArrayList<PrivilegeDTO>();
		for (Privilege privilege : privileges) {
			PrivilegeDTO tempDTO = new PrivilegeDTO();
			tempDTO.withPrivilegeName(privilege.getName());
			tempDTO.withPrivilegeStatus(privilege.getStatus());
			privilegesDTO.add(tempDTO);
		}
		return privilegesDTO;
	}
	
	public String editPrivilege(PrivilegeDTO privilegeDTO) {
		Privilege privilege = allPrivileges.get(privilegeDTO.getId());		
		try {
			Privilege updatedPrivilege = new Privilege();
			updatedPrivilege.withName(privilegeDTO.getName());
			updatedPrivilege.withStatus(privilegeDTO.getStatus() == null ? privilege.getStatus() : privilegeDTO.getStatus());
			updatedPrivilege.setId(privilegeDTO.getId());
			updatedPrivilege.setRevision(privilege.getRevision());
			allPrivileges.update(updatedPrivilege);
			return "1";
		}
		catch (Exception e) {
			return "0";
		}
	}
}
