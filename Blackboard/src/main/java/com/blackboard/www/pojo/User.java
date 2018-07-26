package com.blackboard.www.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@PrimaryKeyJoinColumn(name = "personID")
public class User extends Person {

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Email email;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "role", nullable = false)
	private String role;

	@OneToMany(mappedBy = "uploadedBy", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private Set<CourseMaterial> courseMaterial = new HashSet<CourseMaterial>();

	@OneToMany(mappedBy = "postedBy", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private Set<Announcement> announcements = new HashSet<Announcement>();

	@OneToMany(mappedBy = "givenBy", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private Set<Assignment> assignment = new HashSet<Assignment>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "STUDENT_COURSES", joinColumns = { @JoinColumn(name = "personID") }, inverseJoinColumns = {
			@JoinColumn(name = "courseID") })
	Set<Course> courses = new HashSet<Course>();

	public User() {

	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Set<CourseMaterial> getCourseMaterial() {
		return courseMaterial;
	}

	public void setCourseMaterial(Set<CourseMaterial> courseMaterial) {
		this.courseMaterial = courseMaterial;
	}

	public Set<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Set<Announcement> announcements) {
		this.announcements = announcements;
	}

	public Set<Assignment> getAssignment() {
		return assignment;
	}

	public void setAssignment(Set<Assignment> assignment) {
		this.assignment = assignment;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public String toString() {
		return super.getFirstName() + " " + super.getLastName();
	}

}