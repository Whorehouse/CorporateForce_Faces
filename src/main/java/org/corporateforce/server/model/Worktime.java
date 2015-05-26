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
@Table(name = "worktime", catalog = "corporateforce")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Worktime implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Users users;
	private Integer hours;
	private Date day;
	
	public Worktime() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Worktime(Users users, Integer hours, Date day) {
		super();
		this.users = users;
		this.hours = hours;
		this.day = day;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER")
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	@Column(name = "HOURS", nullable = false, length = 11)
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DAY", nullable = false, length = 10)
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	
	
}
