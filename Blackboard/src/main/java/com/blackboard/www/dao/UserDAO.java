package com.blackboard.www.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.blackboard.www.pojo.User;
import com.blackboard.www.pojo.Email;

public class UserDAO extends DAO {

	public UserDAO() {
	}

	public User get(String username, String password) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);
			User user = (User) q.uniqueResult();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get user " + username, e);
		} finally {
			close();
		}
	}

	public User getUsername(String username) {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username");
			q.setString("username", username);
			User user = (User) q.uniqueResult();
			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		return null;

	}

	
	
	public Email getEmail(String query) {

		try {
			begin();
			Query q = getSession().createQuery("from Email where emailAddress = :query");
			q.setString("query", query);
			Email email = (Email) q.uniqueResult();
			return email;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		return null;

	}

	public User register(User u) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");

			Email email = new Email(u.getEmail().getEmailAddress());
			User user = new User(u.getUsername(), u.getPassword());

			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());

			user.setEmail(email);
			email.setUser(user);
			getSession().save(u);
			commit();
			return u;

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		} finally {
			close();
		}
	}

	public boolean updateUser(String email) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", email);
			User user = (User) q.uniqueResult();
			if (user != null) {
				user.setStatus(1);
				getSession().update(user);
				commit();
				return true;
			} else {
				return false;
			}

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		} finally {
			close();
		}

	}

}