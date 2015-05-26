package org.corporateforce.client.port;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Notes;
import org.corporateforce.server.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings(value = {"rawtypes","unchecked"})
@Service
public class NotesPort extends AbstractPort<Notes> {
	
	public NotesPort() {
		super(Notes.class);
	}

	public NotesPort(Class<Notes> entityClass) {
		super(entityClass);
	}
	
	public List<Notes> listByUsers(Users u) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByUsers/"+u.getId(), List.class);
		return convertToList(list,entityClass);
	}
	
	public List<Notes> listByUserAndDay(Users u, Date day) {
		String d = new SimpleDateFormat("yyyy-MM-dd").format(day);
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByUserAndDay/"+u.getId()+"/"+d, List.class);
		return convertToList(list,entityClass);
	}
	
	public List<Notes> listByRangeOverlap(Users u, Date startDate, Date endDate) {
		String sD = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		String eD = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByRangeOverlap/"+u.getId()+"/"+sD+"/"+eD, List.class);
		return convertToList(list,entityClass);
	}
}
