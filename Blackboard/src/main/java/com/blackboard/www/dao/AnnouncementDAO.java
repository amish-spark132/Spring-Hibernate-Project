package com.blackboard.www.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.CourseMaterial;

public class AnnouncementDAO extends DAO {

	public AnnouncementDAO() {
	}

	public Announcement get(int id) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Announcement where id= :id");
			q.setInteger("id", id);
			Announcement announcement = (Announcement) q.uniqueResult();
			return announcement;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get announcement with id: " + id, e);
		} finally {
			close();
		}
	}

	public ArrayList<Announcement> get(String course) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Announcement where course= :course");
			q.setString("course", course);
			ArrayList<Announcement> list = (ArrayList<Announcement>) q.getResultList();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get announcements for course: " + course, e);
		} finally {
			close();
		}
	}

	public ArrayList<Announcement> getAll() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Announcement");
			ArrayList<Announcement> list = (ArrayList<Announcement>) q.getResultList();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get announcements : " + e);
		} finally {
			close();
		}
	}

	public ArrayList<Announcement> getSome(int pageNum) throws Exception {
		try {
			begin();
			Criteria crit = getSession().createCriteria(Announcement.class);
			crit.setFirstResult((pageNum-1)*10);
			crit.setMaxResults(10);
			ArrayList<Announcement> list = (ArrayList<Announcement>) crit.list();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get announcements : " + e);
		} finally {
			close();
		}
	}
	
	
	public Announcement createAnnouncement(Announcement a) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(a);
			commit();
			return a;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating announcement: " + e.getMessage());
		} finally {
			close();
		}
	}

	public void delete(Announcement announcement) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().delete(announcement);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not delete announcement", e);
		} finally {
			close();
		}
	}

	public void delete(String Id) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			int id = Integer.parseInt(Id);
			Query q = getSession().createQuery("from Announcement where id= :id");
			q.setInteger("id", id);
			Announcement announcement = (Announcement) q.uniqueResult();
			getSession().delete(announcement);
			System.out.println("announcement deleted");
			commit();

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not delete announcement", e);
		} finally {
			close();
		}
	}

	/*
	 * public boolean updateUser(Announcement id) throws Exception { try { begin();
	 * System.out.println("inside DAO"); Query q =
	 * getSession().createQuery("from Announcement where id = :id");
	 * q.setInteger("id", id); User Announce = (Announcement) q.uniqueResult();
	 * if(user!=null){ user.setStatus(1); getSession().update(user); commit();
	 * return true; }else{ return false; }
	 * 
	 * } catch (HibernateException e) { rollback(); throw new
	 * Exception("Exception while creating user: " + e.getMessage()); }
	 * 
	 * }
	 */

}
