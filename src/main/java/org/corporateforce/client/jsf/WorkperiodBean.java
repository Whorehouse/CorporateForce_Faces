package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.WorkperiodPort;
import org.corporateforce.server.model.Workperiod;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class WorkperiodBean {

	private Workperiod currentWorkperiod;
	private List<Workperiod> listWorkperiod;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private WorkperiodPort WorkperiodPort;
	
	public Workperiod getCurrentWorkperiod() {
		return currentWorkperiod;
	}

	public void setCurrentWorkperiod(Workperiod newWorkperiod) {
		this.currentWorkperiod = newWorkperiod;
	}
	
	public List<Workperiod> getListWorkperiod() {
		return listWorkperiod;
	}

	public void setListWorkperiod(List<Workperiod> listWorkperiod) {
		this.listWorkperiod = listWorkperiod;
	}

	@PostConstruct 
	public void init(){
		currentWorkperiod = new Workperiod();
	}
	
	public List<Workperiod> listByUsers(Users u) {
		List<Workperiod> res = null;
		try {
			res = WorkperiodPort.listByUsers(u);
		} catch(Exception e) {
			res = new ArrayList<Workperiod>();
		}
		return res;
	}

	public void createWorkperiod() throws Exception {
		currentWorkperiod.setUsers(usersBean.getShowUser());
		currentWorkperiod.setType("alt");
		if (currentWorkperiod.getStart().compareTo(currentWorkperiod.getEnd())<=0) {
			WorkperiodPort.add(currentWorkperiod);
		}
		currentWorkperiod = new Workperiod();
		listWorkperiod = WorkperiodPort.listByUsers(usersBean.getShowUser());
		mainBean.actionWorkperiodList();
	}
	
	public void deleteWorkperiod(Workperiod Workperiod) throws Exception {
		WorkperiodPort.delete(Workperiod.getId());
		listWorkperiod = WorkperiodPort.listByUsers(usersBean.getShowUser());
	}
}
