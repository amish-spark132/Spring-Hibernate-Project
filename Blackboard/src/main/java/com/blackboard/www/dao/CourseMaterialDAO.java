package com.blackboard.www.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.CourseMaterial;

public class CourseMaterialDAO extends DAO{
	
	public CourseMaterialDAO() {
	}

	public CourseMaterial get(int id) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from CourseMaterial where id= :id");
			q.setInteger("id", id);
			CourseMaterial courseMaterial = (CourseMaterial) q.uniqueResult();
			
			return courseMaterial;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get course material with id: " + id, e);
		}
		finally {
			close();
		}
	}
	
	public ArrayList<CourseMaterial> getAll() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from CourseMaterial");
			ArrayList<CourseMaterial> list = (ArrayList<CourseMaterial>) q.getResultList();
			close();
			return list;
			
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Course Materials : " +  e);
		}
		finally{
			close();
		}
	}
	
	
	public CourseMaterial uploadFile(CourseMaterial cm) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(cm);
			commit();
			close();
			return cm;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while uploading file: " + e.getMessage());
		}
		finally{
			close();
		}
	}
	
	public void delete(String fileId) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			int id = Integer.parseInt(fileId);
			Query q = getSession().createQuery("from CourseMaterial where id= :id");
			q.setInteger("id", id);
			CourseMaterial courseMaterial = (CourseMaterial) q.uniqueResult();
			getSession().delete(courseMaterial);
			System.out.println("file deleted");
			commit();
			
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not delete courseMaterial", e);
		}
		finally{
			close();
		}
	}
}
	