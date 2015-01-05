package org.corporateforce.client.jsf;

import java.util.List;

import org.corporateforce.server.model.Users;
import org.corporateforce.client.port.AbstractPort;
import org.corporateforce.helper.RestHelper;

public class UserBean {

	public UserBean() {
		// TODO Auto-generated constructor stub
	}

	public List<Users> getList() {
		List<Users> res = null;
		try {
			res = RestHelper.convertToList(AbstractPort.list("Users"), Users.class);
		} catch (Exception e) {
			return null;
		}
		return res;
	}
}
