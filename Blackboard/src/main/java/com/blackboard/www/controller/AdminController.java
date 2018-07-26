package com.blackboard.www.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blackboard.www.dao.AnnouncementDAO;
import com.blackboard.www.dao.CourseDAO;
import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.Course;
import com.blackboard.www.pojo.User;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);

	@RequestMapping(value = "/admin/viewCourse.htm", method = RequestMethod.GET)
	public String viewCourses(HttpServletRequest request, CourseDAO courseDao, ModelMap map) {
		// System.out.println("in page");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Admin")) {
				try {
					ArrayList<Course> list = courseDao.getAll();
					map.addAttribute("success", list);
					return "admin-course";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/admin/dashboard.htm");
					return "error";
				}
			} else {
				map.addAttribute("errorMessage", "You are not authorized to view this page!");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			}
		} else {
			map.addAttribute("errorMessage", "Please login first!");
			map.addAttribute("backLink", "user/login.htm");
			return "error";
		}
	}

	@RequestMapping(value = "/admin/dashboard.htm", method = RequestMethod.GET)
	public String showDashboard(HttpServletRequest request, ModelMap map) {
		// System.out.println("in page");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Admin")) {
				return "admin-panel";
			} else {
				map.addAttribute("errorMessage", "You are not authorized to view this page!");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			}
		} else {
			map.addAttribute("errorMessage", "Please login first!");
			map.addAttribute("backLink", "user/login.htm");
			return "error";
		}
	}

	@RequestMapping(value = "/admin/addCourse.htm", method = RequestMethod.POST)
	public String addCoursesPage(HttpServletRequest request, CourseDAO courseDao, ModelMap map) {
		// System.out.println("in page");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Admin")) {

				String courseName = request.getParameter("courseName");
				try {
					Course c = new Course();
					// a.setPostedBy(u.getEmail().getUser().toString());
					c.setCourseName(courseName);

					courseDao.create(c);

					ArrayList<Course> list = courseDao.getAll();

					map.addAttribute("success", list);

					return "redirect:viewCourse.htm";

				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/admin/viewCourse.htm");
					return "error";
				}
			} else {
				map.addAttribute("errorMessage", "You are not authorized to view this page!");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			}
		} else {
			map.addAttribute("errorMessage", "Please login first!");
			map.addAttribute("backLink", "user/login.htm");
			return "error";
		}

	}

	@RequestMapping(value = "/admin/addCourse.htm", method = RequestMethod.GET)
	public String addCourses(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		// System.out.println("in page");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Admin")) {

				return "admin-add-course";
			} else {
				map.addAttribute("errorMessage", "You are not authorized to view this page!");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			}
		} else {
			map.addAttribute("errorMessage", "Please login first!");
			map.addAttribute("backLink", "user/login.htm");
			return "error";
		}
	}

	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String returnLogin(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		// System.out.println("in page");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Admin")) {
				return "login";
			} else {
				map.addAttribute("errorMessage", "You are not authorized to view this page!");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			}
		} else {
			map.addAttribute("errorMessage", "Please login first!");
			map.addAttribute("backLink", "user/login.htm");
			return "error";
		}
	}

	@RequestMapping(value = "/error.htm", method = RequestMethod.POST)
	public String adminError(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		// System.out.println("in page");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Admin")) {
				map.addAttribute("errorMessage", "Your credentials didnt match! NOT AUTHORIZED");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			} else {
				map.addAttribute("errorMessage", "You are not authorized to view this page!");
				map.addAttribute("backLink", "user/login.htm");
				return "error";
			}
		} else {
			map.addAttribute("errorMessage", "Please login first!");
			map.addAttribute("backLink", "user/login.htm");
			return "error";
		}
	}
}
