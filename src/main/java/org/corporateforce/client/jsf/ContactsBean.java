package org.corporateforce.client.jsf;

import org.corporateforce.client.port.ContactsPort;
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
	private ContactsPort contactsPort;
	
	public Contacts getCurrentContacts() {
		return currentContacts;
	}
	public void setCurrentContacts(Contacts currentContacts) {
		this.currentContacts = currentContacts;
	}
	
	public void edit() throws Exception {
		contactsPort.update(currentContacts);
		mainBean.actionMainPage();
	}

}
