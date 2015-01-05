package org.corporateforce.client.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	public static String serverURI;
	
	static {
		Properties properties = new Properties();
		InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("/corporateforce.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverURI = properties.getProperty("client.serverURI");
		System.out.println("client.serverURI = "+serverURI);
	}

}
