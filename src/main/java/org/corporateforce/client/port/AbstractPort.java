package org.corporateforce.client.port;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractPort<T> {
	
	public Class<T> entityClass;
	
	public AbstractPort(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public T add(T entity) {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap res = restTemplate.postForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/add", entity, LinkedHashMap.class);
		return convertToEntity(res,entityClass);
	}
	
	public T update(T entity) {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap res = restTemplate.postForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/update", entity, LinkedHashMap.class);
		return convertToEntity(res,entityClass);
	}
	
	public T get(int id) {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap entity = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/get/" + id, LinkedHashMap.class);
		return convertToEntity(entity,entityClass);
	}
	
	public List<T> list() {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/list", List.class);
		return convertToList(list,entityClass);
	}
	
	public List<T> listExclude(int id) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listExclude/"+id, List.class);
		return convertToList(list,entityClass);
	}
	
	public Status delete(int id) {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap entity = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/delete/" + id, LinkedHashMap.class);
		return convertToEntity(entity,Status.class);
	}
	
	public int count() {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap entity = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/count", LinkedHashMap.class);
		return convertToEntity(entity,int.class);
	}
	
	public static <T> List<T> convertToList(List<LinkedHashMap> list, Class<T> converterclass) {
		ObjectMapper mapper = new ObjectMapper();
		List<T> entities = new ArrayList<>();
		for (LinkedHashMap map : list) {			
			T entity = mapper.convertValue(map, converterclass);
			entities.add(entity);
		}	
		return entities;
	}
	
	public static <T> T convertToEntity(LinkedHashMap map, Class<T> converterclass) {
		ObjectMapper mapper = new ObjectMapper();
		T entity = mapper.convertValue(map, converterclass);
		return entity;
	}
}
