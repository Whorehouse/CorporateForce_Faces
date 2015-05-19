package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.ChatterPort;
import org.corporateforce.server.model.Chatter;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ChatterBean {

	private Chatter newChatter;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private ChatterPort chatterPort;
	
	public Chatter getNewChatter() {
		return newChatter;
	}

	public void setNewChatter(Chatter newChatter) {
		this.newChatter = newChatter;
	}

	@PostConstruct 
	public void init(){
		newChatter = new Chatter();
	}
	
	public List<Chatter> listForParent(Users u) {
		List<Chatter> res = null;
		try {
			res = chatterPort.listForParent(u);
		} catch(Exception e) {
			res = new ArrayList<Chatter>();
		}
		return res;
	}

	public void createChatter(boolean selfcreate) {
		newChatter.setUsersByCreator(usersBean.getCurrentUser());
		if (selfcreate) {
			newChatter.setUsersByParent(usersBean.getCurrentUser());
		} else {
			newChatter.setUsersByParent(usersBean.getShowUser());
		}
		chatterPort.add(newChatter);
		newChatter = new Chatter();
	}
	
	public void deleteChatter(Chatter chatter) {
		chatterPort.delete(chatter.getId());
	}
}
