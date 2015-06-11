package org.corporateforce.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.corporateforce.client.port.HolidaysorgPort;
import org.corporateforce.client.port.WorkperiodPort;
import org.corporateforce.client.port.WorktimePort;
import org.corporateforce.server.model.Holidaysorg;
import org.corporateforce.server.model.Users;
import org.corporateforce.server.model.Workperiod;
import org.corporateforce.server.model.Worktime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkCalendarHelper {	

	@Autowired
	private WorktimePort WorktimePort;
	@Autowired
	private HolidaysorgPort HolidaysorgPort;
	@Autowired
	private WorkperiodPort WorkperiodPort;

	public DayReport getDayReport(Users u, Date day) {
		DayReport currentDayReport = new DayReport();
		List<Workperiod> wps = WorkperiodPort.listByRangeOverlap(u, day, day);
		List<Worktime> wts = WorktimePort.listByUserAndDay(u, day);
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
		return currentDayReport;
	}
	
	@SuppressWarnings("deprecation")
	public static Integer getEstimateDay(Workperiod wp, Date ed) {
		Date sD = wp.getStart();
		Date eD = wp.getEnd();
		sD.setHours(0); sD.setMinutes(0);sD.setSeconds(0);
		eD.setHours(0); eD.setMinutes(0);eD.setSeconds(0);
		if (ed.compareTo(sD)>=0 && ed.compareTo(eD)<=0) {
			Integer cicle = wp.getWorkdaylong()+wp.getRestdaylong();
			Integer btw = Days.daysBetween(DateToLocalDate(sD), DateToLocalDate(ed)).getDays();
			Integer ost = btw % cicle;
			if (ost<wp.getWorkdaylong()) {
				return wp.getHours();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	public static LocalDate DateToLocalDate(Date date) {
		GregorianCalendar cl = new GregorianCalendar();
		cl.setTime(date);
		return new LocalDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH)+1, cl.get(Calendar.DAY_OF_MONTH));
	}
	
	@SuppressWarnings("deprecation")
	public static Date LocalDateToDate(LocalDate localDate) {
		return new Date(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
	}
	
	public static String dateFormat(Date date) {
		GregorianCalendar cl = new GregorianCalendar();
		cl.setTime(date);
		return cl.get(Calendar.DAY_OF_MONTH)+"."+ (cl.get(Calendar.MONTH)+1)  +"."+cl.get(Calendar.YEAR);
		//SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		//return format.format(date);
	}
	
	public static class DayReport {
		private Date day;
		private Integer worktime;
		private Integer estimate;
		
		public DayReport() {
			
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
	}
		
	public static class PeriodRow {
		private Users users;
		private Map<Date, DayReport> days;
		private Integer totalWorked;
		private Integer totalEstimate;
		private Integer totalOverworked;
		private Integer totalNotWorked;
		private Integer totalBalance;
				
		public PeriodRow(Users u) {
			this.users = u;
			this.days = new TreeMap<Date, DayReport>();
			resetTotal();
		}
		
		public PeriodRow(Users u, List<DayReport> days) {
			this.users = u;
			this.days = new TreeMap<Date, DayReport>();
			resetTotal();
			addDays(days);
		}
		
		public void addDay(DayReport day) {
			totalWorked += day.getWorktime();
			totalEstimate += day.getEstimate();
			totalOverworked = ((totalWorked-totalEstimate)>0) ? (totalWorked-totalEstimate) : 0;
			totalNotWorked = ((totalEstimate-totalWorked)>0) ? (totalEstimate-totalWorked) : 0;
			totalBalance = totalOverworked - totalNotWorked;
			days.put(day.getDay(), day);
		}
		
		public void addDays(List<DayReport> days) {
			for (DayReport dayReport: days) {
				this.addDay(dayReport);
			}
		}
		
		private void resetTotal() {
			totalWorked = 0;
			totalEstimate = 0;
			totalOverworked = 0;
			totalNotWorked = 0;
			totalBalance = 0;
		}
		
		public void refresh() {
			resetTotal();
			for (Date dayDate: this.days.keySet()) {
				DayReport day = this.days.get(dayDate);
				totalWorked += day.getWorktime();
				totalEstimate += day.getEstimate();
				totalOverworked = ((totalWorked-totalEstimate)>0) ? (totalWorked-totalEstimate) : 0;
				totalNotWorked = ((totalEstimate-totalWorked)>0) ? (totalEstimate-totalWorked) : 0;
				totalBalance = totalOverworked - totalNotWorked;
			}
		}
		
		public Users getUsers() {
			return users;
		}
		public void setUsers(Users users) {
			this.users = users;
		}
		public Map<Date, DayReport> getDays() {
			return days;
		}
		public void setDays(Map<Date, DayReport> days) {
			this.days = days;
		}
		public Integer getTotalWorked() {
			return totalWorked;
		}
		public void setTotalWorked(Integer totalWorked) {
			this.totalWorked = totalWorked;
		}
		public Integer getTotalEstimate() {
			return totalEstimate;
		}
		public void setTotalEstimate(Integer totalEstimate) {
			this.totalEstimate = totalEstimate;
		}
		public Integer getTotalOverworked() {
			return totalOverworked;
		}
		public void setTotalOverworked(Integer totalOverworked) {
			this.totalOverworked = totalOverworked;
		}
		public Integer getTotalNotWorked() {
			return totalNotWorked;
		}
		public void setTotalNotWorked(Integer totalNotWorked) {
			this.totalNotWorked = totalNotWorked;
		}
		public Integer getTotalBalance() {
			return totalBalance;
		}
		public void setTotalBalance(Integer totalBalance) {
			this.totalBalance = totalBalance;
		}
		
	}
	
	public static class PeriodTable {
		private Map<Users, PeriodRow> rows;
		private List<Date> dates;
		private Integer totalWorked;
		private Integer totalEstimate;
		private Integer totalOverworked;
		private Integer totalNotWorked;
		private Integer totalBalance;
		
		public PeriodTable() {
			this.rows = new TreeMap<Users, PeriodRow>();
		}
		
		public PeriodTable(List<PeriodRow> rows) {
			resetTotal();
			this.rows = new TreeMap<Users, PeriodRow>();
			addRows(rows);
			checkDates();
		}
		
		public void addRows(List<PeriodRow> rows) {
			for (PeriodRow row: rows) {
				addRow(row);
			}
			checkDates();
		}
		
		public void addRow(PeriodRow row) {
			this.totalWorked += row.totalWorked;
			this.totalEstimate += row.totalEstimate;
			this.totalOverworked += row.totalOverworked;
			this.totalNotWorked += row.totalNotWorked;
			this.totalBalance += row.totalBalance;
			this.rows.put(row.getUsers(), row);
			checkDates();
		}
		
		public void resetTotal() {
			totalWorked = 0;
			totalEstimate = 0;
			totalOverworked = 0;
			totalNotWorked = 0;
			totalBalance = 0;			
		}
		
		public void refresh() {
			resetTotal();
			for (Users usersRow: this.rows.keySet()) {
				PeriodRow row = this.rows.get(usersRow);
				row.refresh();
				this.totalWorked += row.totalWorked;
				this.totalEstimate += row.totalEstimate;
				this.totalOverworked += row.totalOverworked;
				this.totalNotWorked += row.totalNotWorked;
				this.totalBalance += row.totalBalance;
			}
			checkDates();
		}
		
		public void checkDates() {
			this.dates = new ArrayList<Date>();
			List<Date> bufDates = new ArrayList<Date>();
			for (Users usersRow: this.rows.keySet()) {
				bufDates.addAll(rows.get(usersRow).getDays().keySet());
			}
			Set<Date> hs = new HashSet<>();
			hs.addAll(bufDates);
			this.dates.addAll(hs);
			Collections.sort(this.dates);
		}

		public Map<Users, PeriodRow> getRows() {
			return rows;
		}

		public void setRows(Map<Users, PeriodRow> rows) {
			this.rows = rows;
		}

		public Integer getTotalWorked() {
			return totalWorked;
		}

		public void setTotalWorked(Integer totalWorked) {
			this.totalWorked = totalWorked;
		}

		public Integer getTotalEstimate() {
			return totalEstimate;
		}

		public void setTotalEstimate(Integer totalEstimate) {
			this.totalEstimate = totalEstimate;
		}

		public Integer getTotalOverworked() {
			return totalOverworked;
		}

		public void setTotalOverworked(Integer totalOverworked) {
			this.totalOverworked = totalOverworked;
		}

		public Integer getTotalNotWorked() {
			return totalNotWorked;
		}

		public void setTotalNotWorked(Integer totalNotWorked) {
			this.totalNotWorked = totalNotWorked;
		}

		public Integer getTotalBalance() {
			return totalBalance;
		}

		public void setTotalBalance(Integer totalBalance) {
			this.totalBalance = totalBalance;
		}

		public List<Date> getDates() {
			return dates;
		}

		public void setDates(List<Date> dates) {
			this.dates = dates;
		}	
	}
	
	public static class PeriodReport {
		private List<PeriodTable> tables;
		private Integer totalWorked;
		private Integer totalEstimate;
		private Integer totalOverworked;
		private Integer totalNotWorked;
		private Integer totalBalance;
		
		public PeriodReport() {
			resetTotal();
			this.tables = new ArrayList<PeriodTable>();
		}
		
		public PeriodReport(List<PeriodTable> tables) {
			resetTotal();
			this.tables = new ArrayList<PeriodTable>();
			addTables(tables);
		}
		
		public void addTables(List<PeriodTable> tables) {
			for (PeriodTable table: tables) {
				addTable(table);
			}
		}
		
		public void addTable(PeriodTable table) {
			this.totalWorked += table.totalWorked;
			this.totalEstimate += table.totalEstimate;
			this.totalOverworked += table.totalOverworked;
			this.totalNotWorked += table.totalNotWorked;
			this.totalBalance += table.totalBalance;
			this.tables.add(table);
		}
		
		public void resetTotal() {
			totalWorked = 0;
			totalEstimate = 0;
			totalOverworked = 0;
			totalNotWorked = 0;
			totalBalance = 0;			
		}
		
		public void refresh() {
			resetTotal();
			for (PeriodTable table: this.tables) {
				table.refresh();
				this.totalWorked += table.totalWorked;
				this.totalEstimate += table.totalEstimate;
				this.totalOverworked += table.totalOverworked;
				this.totalNotWorked += table.totalNotWorked;
				this.totalBalance += table.totalBalance;
			}
		}

		public List<PeriodTable> getTables() {
			return tables;
		}

		public void setTables(List<PeriodTable> tables) {
			this.tables = tables;
		}

		public Integer getTotalWorked() {
			return totalWorked;
		}

		public void setTotalWorked(Integer totalWorked) {
			this.totalWorked = totalWorked;
		}

		public Integer getTotalEstimate() {
			return totalEstimate;
		}

		public void setTotalEstimate(Integer totalEstimate) {
			this.totalEstimate = totalEstimate;
		}

		public Integer getTotalOverworked() {
			return totalOverworked;
		}

		public void setTotalOverworked(Integer totalOverworked) {
			this.totalOverworked = totalOverworked;
		}

		public Integer getTotalNotWorked() {
			return totalNotWorked;
		}

		public void setTotalNotWorked(Integer totalNotWorked) {
			this.totalNotWorked = totalNotWorked;
		}

		public Integer getTotalBalance() {
			return totalBalance;
		}

		public void setTotalBalance(Integer totalBalance) {
			this.totalBalance = totalBalance;
		}	
	}
}
