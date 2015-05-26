package org.corporateforce.server.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "workperiod", catalog = "corporateforce")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  
public class Workperiod implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String type;
	private Users users;
	private Date start;
	private Date end;
	private Integer workdaylong;
	private Integer restdaylong;
	private Integer hours;
	
	public Workperiod() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Workperiod(String type, Users users, Date start,
			Date end, Integer workdaylong, Integer restdaylong, Integer hours) {
		super();
		this.type = type;
		this.users = users;
		this.start = start;
		this.end = end;
		this.workdaylong = workdaylong;
		this.restdaylong = restdaylong;
		this.hours = hours;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "TYPE", nullable = false, length = 4)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER")
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "START", nullable = false, length = 10)
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "END", nullable = false, length = 10)
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	@Column(name = "WORKDAYLONG", nullable = true, length = 11)
	public Integer getWorkdaylong() {
		return workdaylong;
	}
	public void setWorkdaylong(Integer workdaylong) {
		this.workdaylong = workdaylong;
	}
	
	@Column(name = "RESTDAYLONG", nullable = true, length = 11)
	public Integer getRestdaylong() {
		return restdaylong;
	}
	public void setRestdaylong(Integer restdaylong) {
		this.restdaylong = restdaylong;
	}
	
	@Column(name = "HOURS", nullable = false, length = 11)
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}	
	
}
