package org.corporateforce.client.jsf;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.corporateforce.client.port.NotesPort;
import org.corporateforce.client.port.WorktimePort;
import org.corporateforce.client.port.HolidaysorgPort;
import org.corporateforce.client.port.WorkperiodPort;
import org.corporateforce.server.model.Holidaysorg;
import org.corporateforce.server.model.Notes;
import org.corporateforce.server.model.Workperiod;
import org.corporateforce.server.model.Worktime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
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
	private NotesPort NotesPort;
	@Autowired
	private WorktimePort WorktimePort;
	@Autowired
	private HolidaysorgPort HolidaysorgPort;
	@Autowired
	private WorkperiodPort WorkperiodPort;
	
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
		List<Workperiod> wps = WorkperiodPort.listByRangeOverlap(usersBean.getCurrentUser(), day, day);
		List<Worktime> wts = WorktimePort.listByRangeOverlap(usersBean.getCurrentUser(), day, day);
		List<Holidaysorg> hs = HolidaysorgPort.listByRangeOverlap(day, day);
		Integer wrk = 0;
		Integer est = 0;
		
		if (hs.size()<=0) {
			for (Workperiod wp: wps) {
				Integer tmp = getEstimateDay(wp, day);
				if (tmp>0) {
					est = tmp;
					break;
				}
			}
		}		
		for (Worktime wt: wts) {
			wrk+=wt.getHours();
		}
		currentDayReport.setDay(day);
		currentDayReport.setWorktime(wrk);
		currentDayReport.setEstimate(est);
		currentDayReport.setHolidaysorg(hs);
		currentDayReport.setNotes(NotesPort.listByUserAndDay(usersBean.getCurrentUser(), day));
	}
	public Integer getEstimateDay(Workperiod wp, Date ed) {
		if (ed.compareTo(wp.getStart())>=0 && ed.compareTo(wp.getEnd())<=0) {
			Integer cicle = wp.getWorkdaylong()+wp.getRestdaylong();
			Integer btw = Days.daysBetween(DateToLocalDate(wp.getStart()), DateToLocalDate(day)).getDays();
			Integer ost = btw % cicle;
			System.out.println("ost"+ost+"cicle"+cicle+"btw"+btw);
			if (ost<wp.getWorkdaylong()) {
				return wp.getHours();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	@SuppressWarnings("deprecation")
	public LocalDate DateToLocalDate(Date date) {
		GregorianCalendar cl = new GregorianCalendar();
		cl.setTime(date);
		return new LocalDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH));
	}
	
	@SuppressWarnings("deprecation")
	public Date LocalDateToDate(LocalDate localDate) {
		return new Date(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
	}
	
	public class DayReport {
		private Date day;
		private Integer worktime;
		private Integer estimate;
		private List<Holidaysorg> holidaysorg;
		private List<Notes> notes;
		
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
	}
}
