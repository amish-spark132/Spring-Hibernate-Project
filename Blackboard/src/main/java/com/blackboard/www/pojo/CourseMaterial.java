package com.blackboard.www.pojo;

import java.io.File;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "course_material_table")
public class CourseMaterial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "fileName", nullable = false)
	private String fileName;

	@Column(name = "filePath", nullable = false)
	private String filePath;

	@Column(name = "type", nullable = false)
	private String type;

	@Column
	@CreationTimestamp
	private LocalDateTime uploadedOn;

	@ManyToOne
	@JoinColumn(name = "uploaded_by")
	private User uploadedBy;

	@ManyToOne
	@JoinColumn(name = "course")
	private Course course;

	

	// @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
	// CascadeType.DETACH, CascadeType.REFRESH})
	// @JoinColumn(name="courseID")
	// private Course course;

	@Column(name = "fileDesc", nullable = true)
	private String fileDesc;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public User getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(User uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Integer getId() {
		return id;
	}

	public LocalDateTime getUploadedOn() {
		return uploadedOn;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CourseMaterial))
			return false;
		CourseMaterial other = (CourseMaterial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDocument [id=" + id + ", name=" + fileName + ", description=" + fileDesc + ", type=" + type + "]";
	}

}
