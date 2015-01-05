package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractPort {
	
	public static LinkedHashMap get(String service, int id) {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap entity = restTemplate.getForObject(Config.serverURI +service + "/get/"+id, LinkedHashMap.class);
		return entity;
	}
	
	public static List<LinkedHashMap> list(String service) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.serverURI +service + "/list", List.class);
		return list;
	}
}
