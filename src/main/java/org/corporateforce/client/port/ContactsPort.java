package org.corporateforce.client.port;

import org.corporateforce.server.model.Contacts;
import org.springframework.stereotype.Service;

@Service
public class ContactsPort extends AbstractPort<Contacts> {
	
	public ContactsPort() {
		super(Contacts.class);
	}

	public ContactsPort(Class<Contacts> entityClass) {
		super(entityClass);
	}

}
