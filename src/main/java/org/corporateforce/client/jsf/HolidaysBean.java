package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.HolidaysPort;
import org.corporateforce.server.model.Holidays;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class HolidaysBean {

	private Holidays newHolidays;
	private List<Holidays> listHolidays;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private HolidaysPort HolidaysPort;
	
	public Holidays getNewHolidays() {
		return newHolidays;
	}

	public void setNewHolidays(Holidays newHolidays) {
		this.newHolidays = newHolidays;
	}
	
	public List<Holidays> getListHolidays() {
		return listHolidays;
	}

	public void setListHolidays(List<Holidays> listHolidays) {
		this.listHolidays = listHolidays;
	}

	@PostConstruct 
	public void init(){
		newHolidays = new Holidays();
	}
	
	public List<Holidays> listByUsers(Users u) {
		List<Holidays> res = null;
		try {
			res = HolidaysPort.listByUsers(u);
		} catch(Exception e) {
			res = new ArrayList<Holidays>();
		}
		return res;
	}

	public void createHolidays() throws Exception {
		newHolidays.setUsers(usersBean.getShowUser());
		HolidaysPort.add(newHolidays);
		newHolidays = new Holidays();
		mainBean.actionUsersShow();
	}
	
	public void deleteHolidays(Holidays Holidays) throws Exception {
		HolidaysPort.delete(Holidays.getId());
		listHolidays = HolidaysPort.listByUsers(listHolidays.get(0).getUsers());
	}
}
