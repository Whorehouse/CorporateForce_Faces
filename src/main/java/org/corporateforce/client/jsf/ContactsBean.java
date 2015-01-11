package org.corporateforce.client.jsf;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.ContactsPort;
import org.corporateforce.client.port.UsersPort;
import org.corporateforce.server.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ContactsBean {

	private Contacts currentContacts;
	
	@Autowired
	private MainBean mainBean;
	@Autowired
	private UsersBean usersBean;
	@Autowired
	private UsersPort usersPort;
	@Autowired
	private ContactsPort contactsPort;
	
	public Contacts getCurrentContacts() {
		return currentContacts;
	}
	public void setCurrentContacts(Contacts currentContacts) {
		this.currentContacts = currentContacts;
	}
	
	@PostConstruct 
	public void init(){
		currentContacts = new Contacts();
	}
	
	
	public void edit() throws Exception {
		contactsPort.update(currentContacts);
		currentContacts = new Contacts();
		mainBean.actionMainPage();
	}
		
	public void save() throws Exception {
		usersBean.getCurrentUser().setContacts(contactsPort.add(currentContacts));
		usersPort.update(usersBean.getCurrentUser());
		currentContacts = new Contacts();
		mainBean.actionMainPage();
	}

}
