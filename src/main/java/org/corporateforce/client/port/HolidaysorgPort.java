package org.corporateforce.client.port;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Holidaysorg;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings(value = {"rawtypes","unchecked"})
@Service
public class HolidaysorgPort extends AbstractPort<Holidaysorg> {
	
	public HolidaysorgPort() {
		super(Holidaysorg.class);
	}

	public HolidaysorgPort(Class<Holidaysorg> entityClass) {
		super(entityClass);
	}
	
	public List<Holidaysorg> listByRangeOverlap(Date startDate, Date endDate) {
		String sD = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		String eD = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByRangeOverlap/"+sD+"/"+eD, List.class);
		return convertToList(list,entityClass);
	}
}
