package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.HolidaysorgPort;
import org.corporateforce.server.model.Holidaysorg;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class HolidaysorgBean {

	private Holidaysorg currentHolidaysorg;
	private List<Holidaysorg> listHolidaysorg;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private HolidaysorgPort HolidaysorgPort;
	
	public Holidaysorg getCurrentHolidaysorg() {
		return currentHolidaysorg;
	}

	public void setCurrentHolidaysorg(Holidaysorg newHolidaysorg) {
		this.currentHolidaysorg = newHolidaysorg;
	}
	
	public List<Holidaysorg> getListHolidaysorg() {
		return listHolidaysorg;
	}

	public void setListHolidaysorg(List<Holidaysorg> listHolidaysorg) {
		this.listHolidaysorg = listHolidaysorg;
	}

	@PostConstruct 
	public void init(){
		currentHolidaysorg = new Holidaysorg();
	}
	
	public List<Holidaysorg> list() {
		List<Holidaysorg> res = null;
		try {
			res = HolidaysorgPort.list();
		} catch(Exception e) {
			res = new ArrayList<Holidaysorg>();
		}
		return res;
	}

	public void createHolidaysorg() throws Exception {
		if (currentHolidaysorg.getStart().compareTo(currentHolidaysorg.getEnd())<=0) {
			HolidaysorgPort.add(currentHolidaysorg);
		}
		currentHolidaysorg = new Holidaysorg();
		listHolidaysorg = HolidaysorgPort.list();
		mainBean.actionHolidaysorgList();
	}
	
	public void deleteHolidaysorg(Holidaysorg Holidaysorg) throws Exception {
		HolidaysorgPort.delete(Holidaysorg.getId());
		listHolidaysorg = HolidaysorgPort.list();
	}
}
