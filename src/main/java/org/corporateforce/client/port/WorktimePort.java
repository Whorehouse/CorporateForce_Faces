package org.corporateforce.client.port;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Worktime;
import org.corporateforce.server.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings(value = {"rawtypes","unchecked"})
@Service
public class WorktimePort extends AbstractPort<Worktime> {
	
	public WorktimePort() {
		super(Worktime.class);
	}

	public WorktimePort(Class<Worktime> entityClass) {
		super(entityClass);
	}
	
	public List<Worktime> listByUsers(Users u) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByUser/"+u.getId(), List.class);
		return convertToList(list,entityClass);
	}
	
	public List<Worktime> listByUserAndDay(Users u, Date day) {
		String d = new SimpleDateFormat("yyyy-MM-dd").format(day);
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByUserAndDay/"+u.getId()+"/"+d, List.class);
		return convertToList(list,entityClass);
	}
	
	public List<Worktime> listByRangeOverlap(Users u, Date startDate, Date endDate) {
		String sD = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		System.out.println("Start date worktime: "+sD);
		String eD = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		System.out.println("End date worktime: "+eD);
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByRangeOverlap/"+u.getId()+"/"+sD+"/"+eD, List.class);
		return convertToList(list,entityClass);
	}
}
