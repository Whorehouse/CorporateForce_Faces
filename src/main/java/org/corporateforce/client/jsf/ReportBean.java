package org.corporateforce.client.jsf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.corporateforce.helper.WorkCalendarHelper.DayReport;
import org.corporateforce.client.port.NotesPort;
import org.corporateforce.helper.WorkCalendarHelper;
import org.corporateforce.helper.WorkCalendarHelper.PeriodRow;
import org.corporateforce.helper.WorkCalendarHelper.PeriodTable;
import org.corporateforce.helper.WorkCalendarHelper.PeriodReport;
import org.corporateforce.server.model.Users;
import org.joda.time.LocalDate;
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
	@Autowired
	private WorkCalendarHelper workCalendarHelper;
	
	private Date startDay;
	private Date endDay;
	private List<Users> usersSelected;
	private List<Users> usersList;

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
	public List<Users> getUsersSelected() {
		return usersSelected;
	}
	public void setUsersSelected(List<Users> usersSelected) {
		this.usersSelected = usersSelected;
	}
	
	@PostConstruct
	public void init() {
		usersSelected = new ArrayList<Users>();
	}
	
	public List<Users> getUsersList() {
		if (usersList!=null) return usersList;
		usersList = usersBean.getList();
		System.out.println("Hello peoples! "+usersSelected);
		return usersList;
	}
	
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}
	
	public boolean isRendered() {
		return (startDay!=null && endDay!=null && usersSelected!=null && usersSelected.size()>0);
	}
	
	private PeriodReport report;
	
	public void renderReport() throws InterruptedException {
		report = new PeriodReport();
		Integer dayInTable = 7;
		Integer sch = 1;
		if (isRendered() && startDay.compareTo(endDay)<=0 && usersSelected.size()>0) {
			PeriodTable table = null;
			Calendar iter = Calendar.getInstance();
	        iter.setTime(startDay);
	        Calendar endIter = Calendar.getInstance();
	        endIter.setTime(endDay);
			while (
				!iter.after(endIter)
			) {
				
				if (sch == 1) {
					table = new PeriodTable();
				}
				for (Users u : usersSelected) {
					PeriodRow row = null;
					if (table.getRows().containsKey(u)) {
						row = table.getRows().get(u);
					} else {
						row = new PeriodRow(u);
						table.getRows().put(u, row);
					}
					row.addDay(workCalendarHelper.getDayReport(u, iter.getTime()));
				}
				if (sch == dayInTable) {
					table.refresh();
					report.addTable(table);
					table = null;
					sch = 1;
				} else {
					sch++;
				}
				iter.add(Calendar.DATE, 1);
			}
			if (table!=null) {
				table.refresh();
				report.addTable(table);
				table = null;
				sch = 1;
			}
		}
	}
	public PeriodReport getReport() {
		return report;
	}
}
