package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Holidays;
import org.corporateforce.server.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HolidaysPort extends AbstractPort<Holidays> {
	
	public HolidaysPort() {
		super(Holidays.class);
	}

	public HolidaysPort(Class<Holidays> entityClass) {
		super(entityClass);
	}
	
	public List<Holidays> listByUsers(Users u) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByUsers/"+u.getId(), List.class);
		return convertToList(list,entityClass);
	}
	
}
