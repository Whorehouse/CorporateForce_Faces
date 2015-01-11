package org.corporateforce.client.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Users;
import org.corporateforce.client.config.Config;
import org.corporateforce.client.port.ContactsPort;
import org.corporateforce.client.port.UsersPort;
import org.corporateforce.helper.ClientFileUploader;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UsersBean {

	private Users currentUser;
	private Users showUser;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersPort usersPort;
	@Autowired
	private ContactsPort contactsPort;
	
	public Users getShowUser() {
		return showUser;
	}

	public void setShowUser(Users showUser) {
		this.showUser = showUser;
	}

	public Users getCurrentUser() {
		return currentUser;
	}
	
	public Map<String, String> getGenders() {
		Map<String, String> genders= new HashMap<String, String>();
		genders.put("male", "Мужской");
		genders.put("female", "Женский");
		return genders;
	}
	
	public Map<String, String> invertMap(Map<String, String> map) {
		Map<String, String> newMap= new HashMap<String, String>();
		for (Entry<String, String> entry : map.entrySet()) {
			newMap.put(entry.getValue(), entry.getKey());
		}
		return newMap;
	}

	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}	

	public void setMainBean(MainBean mainBean) {
		this.mainBean = mainBean;
	}
	public void setUsersPort(UsersPort usersPort) {
		this.usersPort = usersPort;
	}
	public void setContactsPort(ContactsPort contactsPort) {
		this.contactsPort = contactsPort;
	}

	public List<Users> getList() {
		List<Users> res = null;
		try {
			res = usersPort.list();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return res;
	}
	
	public boolean isSignedIn() {
		return currentUser!=null;
	}
	
	public boolean isLoginEnabledAccess() {
		return isLoginEnabledAccess(currentUser);
	}
	
	public boolean isLoginEnabledAccess(Users u) {
		return u!=null && u.getProfiles()!=null && u.getProfiles().isLoginEnabled();
	}
	
	public boolean isManageUsersAccess() {
		return isManageUsersAccess(currentUser);
	}
	
	public boolean isManageUsersAccess(Users u) {
		return u!=null && u.getProfiles()!=null && u.getProfiles().isManageUsers();
	}
	
	public boolean isSystemControlAccess() {
		return isSystemControlAccess(currentUser);
	}
	
	public boolean isSystemControlAccess(Users u) {
		return u!=null && u.getProfiles()!=null && u.getProfiles().isSystemControl();
	}
	
	public void logout() {
		currentUser = null;
	}
	
	public void login() {
		String login = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("login");
		String password = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("password");
		System.out.println("HEEEY Faces Login "+login+" password "+password);
		Users res = usersPort.login(login, password);
		if (res!=null && res.getId()>0) {
			System.out.println(res);
			currentUser = res;			
		} else {
			System.out.println("No:"+res);
		}
	}
	
	public void updateUser() {
		if (currentUser!=null) {
			setCurrentUser(usersPort.get(currentUser.getId()));
		}
	}
	
	//Contacts
    
    public void uploadImage(FileUploadEvent event) throws Exception {
        if (event.getFile() != null) {
        	ClientFileUploader.uploadAvatar(event.getFile(), currentUser);
        	mainBean.actionMainPage();
        }
    }
    
	public boolean isExistContacts() {
		return isExistContacts(currentUser);
	}
    
	public boolean isExistAvatar() {
		return isExistAvatar(currentUser);
	}
	
	public boolean isExistContacts(Users u) {
		return (u!=null && u.getContacts()!=null) ? true : false;
	}
	
	public boolean isExistAvatar(Users u) {
		return (isExistContacts(u)&&u.getContacts().getAvatars()!=null) ? true : false;
	}
    
    public String getAvatar() {
    	if (isExistAvatar()) {
    		return Config.getUriServer()+"Avatars/showAvatar/"+currentUser.getContacts().getAvatars().getId();
    	} else {
    		return Config.getUriServer()+"resources/images/img_no_photo.png";
    	}
    }
}
