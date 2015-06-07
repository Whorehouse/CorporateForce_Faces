package org.corporateforce.helper;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.corporateforce.client.port.UsersPort;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@FacesConverter("usersConverter")
@Component
public class UsersConverter implements Converter  {
 
	@Autowired
	private UsersPort usersPort;
	 
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println(usersPort.get(Integer.valueOf(value)));
		return usersPort.get(Integer.valueOf(value));
	}
	 
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Users)value).getId().toString();
	}
}
