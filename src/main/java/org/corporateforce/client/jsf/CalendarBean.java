package org.corporateforce.client.jsf;

import java.util.Date;
import java.util.List;

import org.corporateforce.client.port.NotesPort;
import org.corporateforce.client.port.WorktimePort;
import org.corporateforce.client.port.HolidaysorgPort;
import org.corporateforce.client.port.WorkperiodPort;
import org.corporateforce.helper.WorkCalendarHelper;
import org.corporateforce.server.model.Holidaysorg;
import org.corporateforce.server.model.Notes;
import org.corporateforce.server.model.Workperiod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class CalendarBean {

	@Autowired
	private MainBean mainBean;	
	@Autowired
	private UsersBean usersBean;	
	@Autowired
	private NotesPort notesPort;
	@Autowired
	private WorktimePort worktimePort;
	@Autowired
	private HolidaysorgPort holidaysorgPort;
	@Autowired
	private WorkperiodPort workperiodPort;
	@Autowired
	private WorkCalendarHelper workCalendarHelper;
	
	private Date day;
	private DayReport currentDayReport;
	
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
		setDayReport();
	}
	public DayReport getCurrentDayReport() {
		return currentDayReport;
	}
	public void setCurrentDayReport(DayReport currentDayReport) {
		this.currentDayReport = currentDayReport;
	}
	public boolean isSetDay() {
		return this.day!=null;
	}
	public void setDayReport() {
		currentDayReport = new DayReport();
		WorkCalendarHelper.DayReport dayReport = workCalendarHelper.getDayReport(usersBean.getCurrentUser(), day);
		currentDayReport.setDay(day);
		currentDayReport.setWorktime(dayReport.getWorktime());
		currentDayReport.setEstimate(dayReport.getEstimate());
		
		currentDayReport.setHolidaysorg(holidaysorgPort.listByRangeOverlap(day, day));
		currentDayReport.setNotes(notesPort.listByUserAndDay(usersBean.getCurrentUser(), day));
		currentDayReport.setWorkperiod(workperiodPort.listByRangeOverlap(usersBean.getCurrentUser(), day, day));
	}
		
	public class DayReport {
		private Date day;
		private Integer worktime;
		private Integer estimate;
		private List<Holidaysorg> holidaysorg;
		private List<Notes> notes;
		private List<Workperiod> workperiod;
		
		public DayReport() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public Date getDay() {
			return day;
		}
		public void setDay(Date day) {
			this.day = day;
		}
		public Integer getWorktime() {
			return worktime;
		}
		public void setWorktime(Integer worktime) {
			this.worktime = worktime;
		}
		public Integer getEstimate() {
			return estimate;
		}
		public void setEstimate(Integer estimate) {
			this.estimate = estimate;
		}
		public List<Holidaysorg> getHolidaysorg() {
			return holidaysorg;
		}
		public void setHolidaysorg(List<Holidaysorg> holidaysorg) {
			this.holidaysorg = holidaysorg;
		}
		public List<Notes> getNotes() {
			return notes;
		}
		public void setNotes(List<Notes> notes) {
			this.notes = notes;
		}
		public List<Workperiod> getWorkperiod() {
			return workperiod;
		}
		public void setWorkperiod(List<Workperiod> workperiod) {
			this.workperiod = workperiod;
		}
	}
}
