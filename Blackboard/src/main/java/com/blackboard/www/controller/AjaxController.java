package com.blackboard.www.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackboard.www.dao.UserDAO;
import com.blackboard.www.pojo.Email;
import com.blackboard.www.pojo.User;

@Controller
public class AjaxController {
	ArrayList<String> courseList;

	public AjaxController() {

	}

	@RequestMapping(value = "/usernameavailability.htm", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxCheckusername(HttpServletRequest request, UserDAO userDao) {
		String queryString = request.getParameter("username");
		User u = userDao.getUsername(queryString);

		if (u != null) {
			return "Username already exists";
		} else
			return "Username available";
	}

	@RequestMapping(value = "/emailavailability.htm", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxCheckemail(HttpServletRequest request, UserDAO userDao) {
		String queryString = request.getParameter("email");
		Email e = userDao.getEmail(queryString);

		if (e != null) {
			return "Email already exists";
		} else
			return "Email available";
	}
}
