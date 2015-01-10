package org.corporateforce.client.jsf;

import java.util.List;

import javax.faces.context.FacesContext;

import org.corporateforce.server.model.Users;
import org.corporateforce.client.port.UsersPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UsersBean {

	private Users currentUser;
	
	@Autowired
	private UsersPort usersPort;
	
	public Users getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}	
	
	public void setUsersPort(UsersPort usersPort) {
		this.usersPort = usersPort;
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
		System.out.println("HEEEY Login "+login+" password "+password);
		Users res = usersPort.login(login, password);
		if (res!=null && res.getId()>0) {
			System.out.println(res);
			currentUser = res;			
		} else {
			System.out.println("No:"+res);
		}
	}
	
}
