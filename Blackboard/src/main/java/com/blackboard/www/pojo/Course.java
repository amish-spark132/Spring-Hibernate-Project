package com.blackboard.www.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Course_table")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "courseID", unique = true, nullable = false)
	private int courseID;

	@Column(name = "course_name", nullable = false)
	private String courseName;

	public Course() {

	}
	
	public Course(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/*public Person getInstructor() {
		return instructor;
	}

	public void setInstructory(Person instructor) {
		this.instructor = instructor;
	}

	public Set<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Set<Announcement> announcements) {
		this.announcements = announcements;
	}

	public Set<Person> getStudents() {
		return students;
	}

	public void setStudents(Set<Person> students) {
		this.students = students;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<CourseMaterial> getCourseMaterials() {
		return courseMaterials;
	}

	public void setCourseMaterials(List<CourseMaterial> courseMaterials) {
		this.courseMaterials = courseMaterials;
	}

	// Convenience method for bi-directional relationship
	public void add(CourseMaterial tempCM) {
		if (courseMaterials == null)
			courseMaterials = new ArrayList();

		courseMaterials.add(tempCM);

		tempCM.setCourse(this);
	}*/
	
	@Override
	public String toString() {
		return courseName;
	}
	
}
