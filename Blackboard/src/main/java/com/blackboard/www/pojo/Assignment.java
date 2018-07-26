package com.blackboard.www.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Assignment_table")
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer Id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="description")
	private String desc;
	
	@Column
	@CreationTimestamp
	private LocalDateTime postedOn;
	
	@Column
	private Date Deadline;
	
	@ManyToOne
	@JoinColumn(name = "course")
	private Course course;
	
	@ManyToOne
	@JoinColumn(name = "givenBy")
	private User givenBy;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getDeadline() {
		return Deadline;
	}
	public void setDeadline(Date deadline) {
		Deadline = deadline;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Integer getId() {
		return Id;
	}
	public LocalDateTime getPostedOn() {
		return postedOn;
	}
	public User getGivenBy() {
		return givenBy;
	}
	public void setGivenBy(User givenBy) {
		this.givenBy = givenBy;
	}
	
	
	
	
	
}
