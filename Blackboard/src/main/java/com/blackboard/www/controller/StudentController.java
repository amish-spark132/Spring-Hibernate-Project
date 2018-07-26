package com.blackboard.www.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blackboard.www.dao.AnnouncementDAO;
import com.blackboard.www.dao.AssignmentDAO;
import com.blackboard.www.dao.CourseMaterialDAO;
import com.blackboard.www.dao.UserDAO;
import com.blackboard.www.excelview.ExcelAnnouncementView;
import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.Assignment;
import com.blackboard.www.pojo.CourseMaterial;
import com.blackboard.www.pojo.User;

@Controller
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@RequestMapping(value = { "/student/dashboard.htm", "/" }, method = RequestMethod.GET)
	public String showDashboard(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Student")) {
				return "student-dashboard";
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

	@RequestMapping(value = "/student/assignments.htm", method = RequestMethod.GET)
	public String showAssignments(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {

			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Student")) {
				return "student-assignments";
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

	@RequestMapping(value = "/student/announcements.htm", method = RequestMethod.GET)
	public String showAnnouncements(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {

			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Student")) {

				try {

					ArrayList<Announcement> list;

					session.setAttribute("ROWS_TO_DISPLAY", 5);
					if (request.getParameter("page") == null) {
						list = annDao.getSome(1);
					} else {
						list = annDao.getSome(Integer.parseInt(request.getParameter("page")));
					}
					int rowCount = annDao.getAll().size();
					session.setAttribute("rowCount", String.valueOf(Math.abs(rowCount / 5)));

					map.addAttribute("success", list);

					return "student-announcements";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/dashboard.htm");
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

	@RequestMapping(value = "/student/course-material.htm", method = RequestMethod.GET)
	public String accessCourseMaterials(HttpServletRequest request, CourseMaterialDAO courseDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {

			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Student")) {
				try {
					ArrayList<CourseMaterial> list = courseDao.getAll();
					map.addAttribute("success", list);
					return "student-course-material";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/student/dashboard.htm");
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

	@RequestMapping(value = "/student/assignment.htm", method = RequestMethod.GET)
	public String accessAssignments(HttpServletRequest request, AssignmentDAO assignmentDao,
			CourseMaterialDAO courseDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {

			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Student")) {
				try {
					ArrayList<Assignment> list = assignmentDao.getAll();
					map.addAttribute("success", list);
					return "student-assignment";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/student/dashboard.htm");
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

	@RequestMapping(value = "/student/course-material-{id}.htm", method = RequestMethod.GET)
	public @ResponseBody void downloadCourseMaterials(@PathVariable int id, CourseMaterialDAO courseDao, ModelMap map,
			HttpServletResponse response) {

		try {
			System.out.println(id);
			CourseMaterial cm = courseDao.get(id);
			String fullFilePath = cm.getFilePath() + "\\" + cm.getFileName();
			File file = getFile(fullFilePath);
			InputStream in = new FileInputStream(file);

			response.setContentType(cm.getType());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName());
			response.setHeader("Content-Length", String.valueOf(file.length()));
			FileCopyUtils.copy(in, response.getOutputStream());
			// return cm;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.addAttribute("errorMessage", "Some error occured!");
			map.addAttribute("backLink", "/student/dashboard.htm");
			// return null;
		}

	}

	@RequestMapping(value = "/student/downloadAnnouncements.htm", method = RequestMethod.POST)
	public ModelAndView downloadAnnouncements(HttpServletRequest request, ExcelAnnouncementView excel, ModelMap map) {
		HttpSession session = request.getSession();
		System.out.println("inside excel controller method");
		ArrayList<Announcement> list = (ArrayList<Announcement>) session.getAttribute("announcements");
		return new ModelAndView(new ExcelAnnouncementView(), "list", list);

	}

	private File getFile(String FILE_PATH) throws FileNotFoundException {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			throw new FileNotFoundException("file with path: " + FILE_PATH + " was not found.");
		}
		return file;
	}

}
