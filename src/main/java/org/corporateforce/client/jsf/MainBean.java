package org.corporateforce.client.jsf;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.corporateforce.client.config.Config;
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
	
	//Faces
	
	private static final String FACES_CONTACTS_SAVEOREDIT = "/contacts_saveoredit";
	private static final String FACES_HOLIDAYS_ADD = "/holidays_add";
	private static final String FACES_HOLIDAYS_LIST = "/holidays_list";
	private static final String FACES_USERS_LIST = "/users_list";
	private static final String FACES_USERS_SHOW = "/users_show";
	//New
	private static final String FACES_WORKPERIOD_ADD = "/workperiod_add";
	private static final String FACES_WORKPERIOD_LIST = "/workperiod_list";
	private static final String FACES_HOLIDAYSORG_ADD = "/holidaysorg_add";
	private static final String FACES_HOLIDAYSORG_LIST = "/holidaysorg_list";
	private static final String FACES_WORKTIME_ADD = "/worktime_add";
	private static final String FACES_WORKTIME_LIST = "/worktime_list";
	private static final String FACES_CALENDAR = "/calendar";
	private static final String FACES_NOTES_ADD = "/notes_add";
	private static final String FACES_NOTES_EDIT = "/notes_edit";
	private static final String FACES_NOTES_LIST = "/notes_list";
	
	
	
	public void actionContactsSaveOrEdit() throws Exception {
		this.redirect(FACES_CONTACTS_SAVEOREDIT);
	}
	
	public void actionHolidaysAdd() throws Exception {
		this.redirect(FACES_HOLIDAYS_ADD);
	}
	
	public void actionHolidaysList() throws Exception {
		this.redirect(FACES_HOLIDAYS_LIST);
	}
	
	public void actionUsersList() throws Exception {
		this.redirect(FACES_USERS_LIST);
	}
	
	public void actionUsersShow() throws Exception {
		this.redirect(FACES_USERS_SHOW );
	}
	
	
	//New
	public void actionWorkperiodAdd() throws Exception {
		this.redirect(FACES_WORKPERIOD_ADD);
	}
	public void actionWorkperiodList() throws Exception {
		this.redirect(FACES_WORKPERIOD_LIST);
	}
	public void actionHolidaysorgAdd() throws Exception {
		this.redirect(FACES_HOLIDAYSORG_ADD);
	}
	public void actionHolidaysorgList() throws Exception {
		this.redirect(FACES_HOLIDAYSORG_LIST);
	}
	public void actionWorktimeAdd() throws Exception {
		this.redirect(FACES_WORKTIME_ADD);
	}
	public void actionWorktimeList() throws Exception {
		this.redirect(FACES_WORKTIME_LIST);
	}
	public void actionCalendar() throws Exception {
		this.redirect(FACES_CALENDAR);
	}
	public void actionNotesAdd() throws Exception {
		this.redirect(FACES_NOTES_ADD);
	}
	public void actionNotesEdit() throws Exception {
		this.redirect(FACES_NOTES_EDIT);
	}
	public void actionNotesList() throws Exception {
		this.redirect(FACES_NOTES_LIST);
	}
}
