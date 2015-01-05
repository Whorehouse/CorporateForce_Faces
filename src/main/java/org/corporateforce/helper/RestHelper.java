package org.corporateforce.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class RestHelper {

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
