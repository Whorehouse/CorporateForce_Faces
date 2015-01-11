package org.corporateforce.client.port;

import java.util.LinkedHashMap;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Contacts;
import org.corporateforce.server.model.Users;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ContactsPort extends AbstractPort<Contacts> {
	
	public ContactsPort() {
		super(Contacts.class);
	}

	public ContactsPort(Class<Contacts> entityClass) {
		super(entityClass);
	}

}
