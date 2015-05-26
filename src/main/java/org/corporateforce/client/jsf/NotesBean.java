package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.client.port.NotesPort;
import org.corporateforce.server.model.Notes;
import org.corporateforce.server.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class NotesBean {

	private Notes currentNotes;
	private List<Notes> listNotes;
	
	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private NotesPort NotesPort;
	
	public Notes getCurrentNotes() {
		return currentNotes;
	}

	public void setCurrentNotes(Notes newNotes) {
		this.currentNotes = newNotes;
	}
	
	public List<Notes> getListNotes() {
		return listNotes;
	}

	public void setListNotes(List<Notes> listNotes) {
		this.listNotes = listNotes;
	}

	@PostConstruct 
	public void init(){
		currentNotes = new Notes();
	}
	
	public List<Notes> listByUsers(Users u) {
		List<Notes> res = null;
		try {
			res = NotesPort.listByUsers(u);
		} catch(Exception e) {
			res = new ArrayList<Notes>();
		}
		return res;
	}

	public void createNotes() throws Exception {
		currentNotes.setUsers(usersBean.getCurrentUser());
		NotesPort.add(currentNotes);
		currentNotes = new Notes();
		listNotes = NotesPort.listByUsers(usersBean.getCurrentUser());
		mainBean.actionNotesList();
	}
	
	public void editNotes() throws Exception {
		NotesPort.update(currentNotes);
		currentNotes = new Notes();
		listNotes = NotesPort.listByUsers(usersBean.getCurrentUser());
		mainBean.actionNotesList();
	}
	
	public void deleteNotes(Notes Notes) throws Exception {
		NotesPort.delete(Notes.getId());
		listNotes = NotesPort.listByUsers(usersBean.getCurrentUser());
	}
}
