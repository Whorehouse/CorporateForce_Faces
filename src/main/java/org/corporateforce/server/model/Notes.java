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
@Table(name = "notes", catalog = "corporateforce")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  
public class Notes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Users users;
	private Date day;
	private Integer estimate;
	private String name;
	private Integer priority;
	
	public Notes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Notes(Users users, Date day, Integer estimate, String name,
			Integer priority) {
		super();
		this.users = users;
		this.day = day;
		this.estimate = estimate;
		this.name = name;
		this.priority = priority;
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
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DAY", nullable = false, length = 10)
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	
	@Column(name = "ESTIMATE", nullable = false, length = 11)
	public Integer getEstimate() {
		return estimate;
	}
	public void setEstimate(Integer estimate) {
		this.estimate = estimate;
	}
	
	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "PRIORITY", nullable = false, length = 11)
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	
}
