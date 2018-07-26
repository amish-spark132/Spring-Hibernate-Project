package com.blackboard.www.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.Course;
import com.blackboard.www.pojo.CourseMaterial;

public class CourseDAO extends DAO {

	public CourseDAO() {
		// TODO Auto-generated constructor stub
	}

	public Course get(String courseName) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Course where courseName= :courseName");
			q.setString("courseName", courseName);
			Course course = (Course) q.uniqueResult();
			if (course == null) {
				Course c = new Course();
				c.setCourseName(courseName);
				Course cs = create(c);
				return cs;
			} else
				return course;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get announcements for course: " + courseName, e);
		} finally {
			close();
		}
	}

	public Course create(Course c) throws Exception {
		try {
			begin();
			System.out.println("creating course");
			getSession().save(c);
			commit();
			return c;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating announcement: " + e.getMessage());
		} finally {
			close();
		}
	}

	public ArrayList<Course> getAll() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Course");
			ArrayList<Course> list = (ArrayList<Course>) q.getResultList();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get courses : " + e);
		} finally {
			close();
		}
	}

}
