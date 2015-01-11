package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Chatter;
import org.corporateforce.server.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatterPort extends AbstractPort<Chatter> {
	
	public ChatterPort() {
		super(Chatter.class);
	}

	public ChatterPort(Class<Chatter> entityClass) {
		super(entityClass);
	}
	
	public List<Chatter> listForParent(Users u) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listForParent/"+u.getId(), List.class);
		return convertToList(list,entityClass);
	}
	
}
