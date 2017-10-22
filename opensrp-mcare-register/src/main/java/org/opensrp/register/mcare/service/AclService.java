package org.opensrp.register.mcare.service;

import java.util.ArrayList;
import java.util.List;

import org.opensrp.dto.AclDTO;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.repository.AllAcls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AclService {

	private static Logger logger = LoggerFactory.getLogger(AclService.class
			.toString());
	private AllAcls allAcls;
	
	@Autowired
	public AclService(AllAcls allAcls)
	{
		this.allAcls = allAcls;
	}
	
	public String addAcl(AclDTO aclDTO)	{		
		Acl acls = allAcls.findByRoleName(aclDTO.getRoleName());
		if (acls == null) {
			try{
				Acl acl = new Acl();
				acl.withRoleName(aclDTO.getRoleName());
				acl.withAccessTokens(aclDTO.getAccessTokens());
				acl.withStatus(aclDTO.getStatus());
				allAcls.add(acl);
				return "1";
			}catch(Exception e){
				return "0";
			}
		}else{
			return "2";
		}
	}
	public String editAcl(AclDTO aclDTO) {		
		Acl acls = allAcls.get(aclDTO.getRoleId());
		
		try{
			Acl acl = new Acl();
			acl.withRoleName(aclDTO.getRoleName());
			acl.setId(aclDTO.getRoleId());
			acl.setRevision(acls.getRevision());
			acl.withStatus(aclDTO.getStatus());
			acl.withAccessTokens(aclDTO.getAccessTokens());
			allAcls.update(acl);
			return "1";
		}catch(Exception e){
			return "0";
		}
		
	}
	
	public ArrayList<AclDTO> getRolesAndAccessTokens(){
		List<Acl> acls = allAcls.roles();		//Acl ac = allAcls.get("");
		ArrayList<AclDTO> aclList = new ArrayList<AclDTO>();
		for (Acl acl : acls) {
			AclDTO aclDTO = new AclDTO()
			.withRoleName(acl.getRoleName())
			.withRoleId(acl.getId())
			.withStatus(acl.getStatus())
			.withAccessTokens(acl.getAccessTokens());			
			aclList.add(aclDTO);			
		}		
		return aclList;
	}
	
	public ArrayList<AclDTO> getActiveRolesAndAccessTokens(){
		List<Acl> acls = allAcls.allActiveRoles();		//Acl ac = allAcls.get("");
		ArrayList<AclDTO> aclList = new ArrayList<AclDTO>();
		for (Acl acl : acls) {
			AclDTO aclDTO = new AclDTO()
			.withRoleName(acl.getRoleName())
			.withRoleId(acl.getId())
			.withStatus(acl.getStatus())
			.withAccessTokens(acl.getAccessTokens());			
			aclList.add(aclDTO);			
		}		
		return aclList;
	}
	public AclDTO getRoleAndAccessTokensByName(String roleId){
		Acl acl = allAcls.get(roleId);		
		AclDTO aclDTO = new AclDTO()
		 .withRoleName(acl.getRoleName())
		 .withRoleId(acl.getId())
		 .withStatus(acl.getStatus())
		 .withAccessTokens(acl.getAccessTokens());
		return aclDTO;
	}
}
