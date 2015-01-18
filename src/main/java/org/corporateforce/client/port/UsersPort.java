package org.corporateforce.client.port;

import java.util.LinkedHashMap;
import java.util.List;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Users;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersPort extends AbstractPort<Users> {
	
	public UsersPort() {
		super(Users.class);
	}

	public UsersPort(Class<Users> entityClass) {
		super(entityClass);
	}
	
	public Users login(String login, String password) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> loginData = new LinkedMultiValueMap<String, Object>();
		loginData.add("login", login);
		loginData.add("password", password);
		LinkedHashMap res = restTemplate.postForObject(Config.getUriServer()+"Users/login", new HttpEntity<MultiValueMap<String, Object>>(loginData), LinkedHashMap.class);
		return convertToEntity(res,Users.class);
	}
	
	public List<Users> listByManager(int id) {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> list = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/listByManager/"+id, List.class);
		return convertToList(list,entityClass);
	}
	
	public boolean isManager(int id) {
		RestTemplate restTemplate = new RestTemplate();
		boolean res = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/isManager/"+id, boolean.class);
		return res;
	}
	
	public boolean isManager(int manager, int user) {
		RestTemplate restTemplate = new RestTemplate();
		boolean res = restTemplate.getForObject(Config.getUriServer()+ entityClass.getSimpleName() + "/isManager/"+manager+"/"+user, boolean.class);
		return res;
	}
}
