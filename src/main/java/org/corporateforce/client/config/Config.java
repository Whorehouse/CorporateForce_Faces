package org.corporateforce.client.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
	
	public static Properties properties;
	
	static {
		properties = new Properties();
		InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("/corporateforce.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isEnabledModule(String module) {
		return (properties.containsKey("enable"+module) && Integer.valueOf(properties.getProperty("enable"+module,"0"))==1) ? true : false;
	}
	
	public static String getUriModule(String module) {
		return isEnabledModule(module) ? properties.getProperty("uri"+module,"http://localhost:8080/") : null;
	}
	
	public static String getUriServer() {
		return properties.getProperty("serverURI");
	}
	
	public static Map<String, String> getModules() {
		Map<String, String> res = new HashMap<String, String>();
		if (properties.containsKey("clients")) {
			String[] modules = properties.getProperty("clients").split(",");
			for (String module: modules) {
				String uri = getUriModule(module);
				if (uri!=null) res.put(module, uri);
			}
		}
		return res;
	}
}
