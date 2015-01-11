package org.corporateforce.client.jsf;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.corporateforce.client.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@Scope("request")
public class MainBean implements Serializable {

	public static final String PAGE_LOGIN = "login";
	public static final String PAGE_LOGOUT = "logout";
	public static final String PAGE_CONSOLE = "console";
	
	public static final String PAGE_INDEX = "/index";
	public static final String PAGE_WELCOME = "/welcome";
	
	private static final String MODULE_FACES = "Faces";
	private static final String MODULE_PROJECTS = "Projects";
	private static final String MODULE_TRAININGS = "Trainings";

	private void redirect(String page, boolean external) throws Exception {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if (!external) page= context.getRequestContextPath() + page;
		context.redirect(page);
	}
	
	private void redirect(String page) throws Exception {
		this.redirect(page,false);		
	}

	public void actionMainPage() throws Exception {
		this.redirect(PAGE_INDEX);
	}
	

	//External resources
	
	public void actionServer() throws Exception {
		this.redirect(Config.getUriServer(),true);
	}

	public void actionLogin() throws Exception {
		this.redirect(Config.getUriServer()+PAGE_LOGIN,true);
	}

	public void actionLogout() throws Exception {
		this.redirect(Config.getUriServer()+PAGE_LOGOUT,true);
	}
	
	public void actionConsole() throws Exception {
		this.redirect(Config.getUriServer()+PAGE_CONSOLE,true);
	}

	public void actionOpenFaces() throws Exception {
		this.redirect(Config.getUriModule(MODULE_FACES),true);
	}

	public void actionOpenProjects() throws Exception {
		this.redirect(Config.getUriModule(MODULE_PROJECTS),true);
	}
	
	public void actionOpenTrainings() throws Exception {
		this.redirect(Config.getUriModule(MODULE_TRAININGS),true);
	}
}
