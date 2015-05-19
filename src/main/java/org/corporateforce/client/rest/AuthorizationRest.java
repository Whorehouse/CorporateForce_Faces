package org.corporateforce.client.rest;

import org.corporateforce.client.jsf.UsersBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("session")
public class AuthorizationRest {
	
	@Autowired
	UsersBean usersBean;

	public void setUsersBean(UsersBean usersBean) {
		this.usersBean = usersBean;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody boolean login(@RequestParam("login") String login, @RequestParam("password") String password) {
		return usersBean.login(login, password);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public @ResponseBody boolean logout() {
		return usersBean.logout();
	}
}
