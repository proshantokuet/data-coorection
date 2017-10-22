package org.opensrp.dashboard.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opensrp.dashboard.dto.LocationDTO;
import org.opensrp.dashboard.dto.RoleDTO;
import org.opensrp.dashboard.dto.UserDTO;
import org.opensrp.dashboard.dto.SimplifiedLocation;
import org.opensrp.dashboard.dto.SimplifiedRole;
import org.opensrp.dashboard.dto.SimplifiedUser;
import org.opensrp.dashboard.domain.User;
import org.opensrp.dashboard.repository.AllUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.codec.Base64;

@Service
public class UsersService {
	
	private static Logger logger = LoggerFactory.getLogger(UsersService.class);
	
	private AllUser allUsers;
	
	private RoleService roleService;
	
	@Autowired
	public UsersService(AllUser allUsers, RoleService roleService) {
		this.allUsers = allUsers;
		this.roleService = roleService;
	}
	
	public List<String> getPrivilegesOfAUser(String userName) {
		User userByUserName = allUsers.findUserByUserName(userName);
		if (userByUserName == null || userByUserName.getRoles() == null || userByUserName.getRoles().size() < 1)
			return null;
		else {
			Set<String> privileges = new HashSet<String>();
			for (int i = 0; i < userByUserName.getRoles().size(); i++) {
				logger.info("entered for role - " + userByUserName.getRoles().get(i).getName());
				List<String> roleSpecificPrivileges = roleService.getPrivilegesOfASpecificRole(userByUserName.getRoles()
				        .get(i).getId());
				if (roleSpecificPrivileges != null) {
					for (int j = 0; j < roleSpecificPrivileges.size(); j++) {
						privileges.add(roleSpecificPrivileges.get(j));
					}
				}
			}
			logger.info("privilege list constructed");
			return new ArrayList<String>(privileges);
		}
	}
	
	public String addUser(UserDTO userDTO) {
		logger.info("inside UserService.addUser");
		User userByUserName = allUsers.findUserByUserName(userDTO.getUserName());
		if (userByUserName == null) {
			logger.info("No Such User with given userName Found");
			try {
				User user = new User();
				//role.withUserName(roleDTO.getUserName());
				user.withStatus(userDTO.getStatus());
				user.withGivenName(userDTO.getGivenName());
				user.withFamilyName(userDTO.getFamilyName());
				user.withMiddleName(userDTO.getMiddleName());
				user.withContactNumber(userDTO.getContactNumber());
				user.withPersonalAddress(userDTO.getPersonalAddress());
				user.withEmail(userDTO.getEmail());
				String encodedPassword = new String(Base64.encode(userDTO.getPassword().getBytes()));
				user.withPassword(encodedPassword);
				user.withUserName(userDTO.getUserName());
				user.withGender(userDTO.getGender());
				//parent, children, roles, locations
				UserDTO parentDTO = userDTO.getParent();
				List<UserDTO> childrenDTOs = userDTO.getChildren();
				List<RoleDTO> roleDTOs = userDTO.getRoles();
				List<LocationDTO> locationDTOs = userDTO.getLocation();
				//	
				SimplifiedUser parent = null;
				if (parentDTO != null) {
					parent = new SimplifiedUser();
					parent.withUsername(parentDTO.getUserName());
					parent.withId(parentDTO.getId());
					user.withParent(parent);
					logger.info("parent added in user.");
				}
				List<SimplifiedUser> children = null;
				if (childrenDTOs != null && childrenDTOs.size() > 0) {
					children = new ArrayList<SimplifiedUser>();
					for (int i = 0; i < childrenDTOs.size(); i++) {
						SimplifiedUser tempUser = new SimplifiedUser();
						tempUser.withUsername(childrenDTOs.get(i).getUserName());
						tempUser.withId(childrenDTOs.get(i).getId());
						children.add(tempUser);
					}
					user.withChildren(children);
					logger.info("children added in user.");
				}
				List<SimplifiedRole> roles = null;
				if (roleDTOs != null && roleDTOs.size() > 0) {
					roles = new ArrayList<SimplifiedRole>();
					for (int i = 0; i < roleDTOs.size(); i++) {
						SimplifiedRole tempRole = new SimplifiedRole();
						tempRole.withName(roleDTOs.get(i).getName());
						tempRole.withId(roleDTOs.get(i).getRoleId());
						roles.add(tempRole);
					}
					user.withRoles(roles);
					logger.info("roles added in user.");
				}
				List<SimplifiedLocation> locations = null;
				if (locationDTOs != null && locationDTOs.size() > 0) {
					locations = new ArrayList<SimplifiedLocation>();
					for (int i = 0; i < locationDTOs.size(); i++) {
						SimplifiedLocation tempLocation = new SimplifiedLocation();
						tempLocation.withName(locationDTOs.get(i).getName());
						tempLocation.withId(locationDTOs.get(i).getId());
						locations.add(tempLocation);
						//logger.info("location name - " + tempLocation.getName() + " - " + tempLocation.getId());
					}
					user.withLocation(locations);
					logger.info("loactions added in user.");
				}
				allUsers.add(user);
				return "1";
			}
			catch (Exception e) {
				return "0";
			}
		} else {
			logger.info("User with given userName already exists.");
			return "2";
		}
	}
	
	public String editUser(UserDTO userDTO) {
		//Role roles = allRoles.get(roleDTO.getRoleId() );  // null checking isn't done here, should be done rigorously on client-side
		User userByUserName = allUsers.findUserByUserName(userDTO.getUserName() == null ? "" : userDTO.getUserName());
		if (userByUserName != null) {
			try {
				
				if (userDTO.getStatus() != null) {
					userByUserName.withStatus(userDTO.getStatus());
				}
				if (userDTO.getGivenName() != null) {
					userByUserName.withGivenName(userDTO.getGivenName());
				}
				if (userDTO.getFamilyName() != null) {
					userByUserName.withFamilyName(userDTO.getFamilyName());
				}
				if (userDTO.getMiddleName() != null) {
					userByUserName.withMiddleName(userDTO.getMiddleName());
				}
				if (userDTO.getContactNumber() != null) {
					userByUserName.withContactNumber(userDTO.getContactNumber());
				}
				if (userDTO.getPersonalAddress() != null) {
					userByUserName.withPersonalAddress(userDTO.getPersonalAddress());
				}
				if (userDTO.getEmail() != null) {
					userByUserName.withEmail(userDTO.getEmail());
				}
				if (userDTO.getPassword() != null) {
					String encodedPassword = new String(Base64.encode(userDTO.getPassword().getBytes()));
					userByUserName.withPassword(encodedPassword);
				}
				if (userDTO.getGender() != null) {
					userByUserName.withGender(userDTO.getGender());
				}
				if (userDTO.getParent() != null) {
					UserDTO parentDTO = userDTO.getParent();
					SimplifiedUser parent = null;
					if (parentDTO != null) {
						parent = new SimplifiedUser();
						parent.withUsername(parentDTO.getUserName());
						parent.withId(parentDTO.getId());
						userByUserName.withParent(parent);
						logger.info("parent updated in user.");
					}
				}
				if (userDTO.getChildren() != null) {
					List<UserDTO> childrenDTOs = userDTO.getChildren();
					List<SimplifiedUser> children = null;
					if (childrenDTOs != null && childrenDTOs.size() > 0) {
						children = new ArrayList<SimplifiedUser>();
						for (int i = 0; i < childrenDTOs.size(); i++) {
							SimplifiedUser tempUser = new SimplifiedUser();
							tempUser.withUsername(childrenDTOs.get(i).getUserName());
							tempUser.withId(childrenDTOs.get(i).getId());
							children.add(tempUser);
						}
						userByUserName.withChildren(children);
						logger.info("children updated in user.");
					}
				}
				if (userDTO.getRoles() != null) {
					List<RoleDTO> roleDTOs = userDTO.getRoles();
					List<SimplifiedRole> roles = null;
					if (roleDTOs != null && roleDTOs.size() > 0) {
						roles = new ArrayList<SimplifiedRole>();
						for (int i = 0; i < roleDTOs.size(); i++) {
							SimplifiedRole tempRole = new SimplifiedRole();
							tempRole.withName(roleDTOs.get(i).getName());
							tempRole.withId(roleDTOs.get(i).getRoleId());
							roles.add(tempRole);
						}
						userByUserName.withRoles(roles);
						logger.info("roles updated in user.");
					}
				}
				if (userDTO.getLocation() != null) {
					List<LocationDTO> locationDTOs = userDTO.getLocation();
					List<SimplifiedLocation> locations = null;
					if (locationDTOs != null && locationDTOs.size() > 0) {
						locations = new ArrayList<SimplifiedLocation>();
						for (int i = 0; i < locationDTOs.size(); i++) {
							SimplifiedLocation tempLocation = new SimplifiedLocation();
							tempLocation.withName(locationDTOs.get(i).getName());
							tempLocation.withId(locationDTOs.get(i).getId());
							locations.add(tempLocation);
							//logger.info("location name - " + tempLocation.getName() + " - " + tempLocation.getId());
						}
						userByUserName.withLocation(locations);
						logger.info("loactions updated in user.");
					}
				}
				allUsers.update(userByUserName);
				
				return "1";
			}
			catch (Exception e) {
				return "0";
			}
		} else {
			return "2";
		}
	}
	
	public String ifUserExists(String userName) {
		User userByUserName = allUsers.findUserByUserName(userName);
		logger.info("service returned -" + userName);
		if (userByUserName == null) {
			return "1";
		} else {
			return "2";
		}
	}
}
