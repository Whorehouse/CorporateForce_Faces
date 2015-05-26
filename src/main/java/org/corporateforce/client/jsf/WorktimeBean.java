package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.WorktimePort;
import org.corporateforce.server.model.Worktime;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class WorktimeBean {

	private Worktime currentWorktime;
	private List<Worktime> listWorktime;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private WorktimePort WorktimePort;
	
	public Worktime getCurrentWorktime() {
		return currentWorktime;
	}

	public void setCurrentWorktime(Worktime newWorktime) {
		this.currentWorktime = newWorktime;
	}
	
	public List<Worktime> getListWorktime() {
		return listWorktime;
	}

	public void setListWorktime(List<Worktime> listWorktime) {
		this.listWorktime = listWorktime;
	}

	@PostConstruct 
	public void init(){
		currentWorktime = new Worktime();
	}
	
	public List<Worktime> listByUsers(Users u) {
		List<Worktime> res = null;
		try {
			res = WorktimePort.listByUsers(u);
		} catch(Exception e) {
			res = new ArrayList<Worktime>();
		}
		return res;
	}

	public void createWorktime() throws Exception {
		currentWorktime.setUsers(usersBean.getCurrentUser());
		WorktimePort.add(currentWorktime);
		currentWorktime = new Worktime();
		listWorktime = WorktimePort.listByUsers(usersBean.getCurrentUser());
		mainBean.actionWorktimeList();
	}
	
	public void deleteWorktime(Worktime Worktime) throws Exception {
		WorktimePort.delete(Worktime.getId());
		listWorktime = WorktimePort.listByUsers(usersBean.getCurrentUser());
	}
}
