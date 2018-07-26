package com.blackboard.www.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.Assignment;
import com.blackboard.www.pojo.Course;

public class AssignmentDAO extends DAO{

	public AssignmentDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public Assignment get(int id) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Assignment where id= :id");
			q.setInteger("id", id);
			Assignment assignment = (Assignment) q.uniqueResult();
			return assignment;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Assignment with id: " + id, e);
		} finally {
			close();
		}
	}
	
	public Assignment getByCourse(String course) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Assignment where course= :course");
			q.setString("course", course);
			Assignment assignment = (Assignment) q.uniqueResult();
			return assignment;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Assignment with course: " + course, e);
		} finally {
			close();
		}
	}
	
	public ArrayList<Assignment> getAll() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Assignment");
			ArrayList<Assignment> list = (ArrayList<Assignment>) q.getResultList();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Assignment : " + e);
		} finally {
			close();
		}
	}
	
	public Assignment createAssignment(Assignment a) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(a);
			commit();
			return a;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating Assignment: " + e.getMessage());
		} finally {
			close();
		}
	}
	
	public void delete(Assignment assignment) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().delete(assignment);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not delete assignment", e);
		} finally {
			close();
		}
	}
	
}
