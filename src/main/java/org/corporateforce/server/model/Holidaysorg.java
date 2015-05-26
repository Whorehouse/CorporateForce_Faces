package org.corporateforce.server.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "holidaysorg", catalog = "corporateforce")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Holidaysorg implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date start;
	private Date end;
	
	public Holidaysorg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Holidaysorg(String name, Date start, Date end) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "NAME", nullable = false, length = 50)	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START", nullable = false, length = 10)
	public Date getStart() {
		return this.start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END", nullable = false, length = 10)
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
}
