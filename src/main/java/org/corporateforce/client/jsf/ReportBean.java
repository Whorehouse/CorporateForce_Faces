package org.corporateforce.client.jsf;

import java.util.Date;

import org.corporateforce.client.port.NotesPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ReportBean {

	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private NotesPort NotesPort;
	@Autowired
	private NotesPort WorktimePort;
	@Autowired
	private NotesPort HolidaysorgPort;
	@Autowired
	private NotesPort WorkperiodPort;
	
	private Date startDay;
	private Date endDay;

	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
}
