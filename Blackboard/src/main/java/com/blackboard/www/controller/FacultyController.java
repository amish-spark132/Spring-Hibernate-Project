package com.blackboard.www.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.blackboard.www.dao.AnnouncementDAO;
import com.blackboard.www.dao.AssignmentDAO;
import com.blackboard.www.dao.CourseDAO;
import com.blackboard.www.dao.CourseMaterialDAO;
import com.blackboard.www.pojo.Announcement;
import com.blackboard.www.pojo.Assignment;
import com.blackboard.www.pojo.Course;
import com.blackboard.www.pojo.CourseMaterial;
import com.blackboard.www.pojo.User;

@Controller
// @RequestMapping(value = "/faculty")
public class FacultyController {

	private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);

	@RequestMapping(value = "/faculty/announcements.htm", method = RequestMethod.GET)
	public String showAnnouncements(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
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
					return "faculty-announcement";
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

	@RequestMapping(value = { "/faculty/dashboard.htm", "/faculty/" }, method = RequestMethod.GET)
	public String showDashboard(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
				return "faculty-dashboard";
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

	@RequestMapping(value = "/faculty/createAnnouncement.htm", method = RequestMethod.GET)
	public String makeAnnouncement(HttpServletRequest request, CourseDAO courseDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
				try {
					map.addAttribute("courseList", courseDao.getAll());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/createAnnouncement.htm");
					return "error";
				}
				return "faculty-create-announcement";
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

	@RequestMapping(value = "/faculty/createAnnouncement.htm", method = RequestMethod.POST)
	public String submitAnnouncement(HttpServletRequest request, AnnouncementDAO annDao, CourseDAO courseDao,
			ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
				String message = request.getParameter("message");
				message.replaceAll("[^a-zA-Z0-9]", "");

				String course = request.getParameter("course");
				course.replaceAll("[^a-zA-Z0-9]", "");

				try {
					Announcement a = new Announcement();
					// a.setPostedBy(u.getEmail().getUser().toString());
					a.setMessage(message);
					a.setCourse(courseDao.get(course));
					a.setPostedBy(u);
					annDao.createAnnouncement(a);

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

					return "redirect:announcements.htm";

				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/createAnnouncement.htm");
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

	@RequestMapping(value = "/faculty/course-material.htm", method = RequestMethod.GET)
	public String accessCourseMaterials(HttpServletRequest request, CourseMaterialDAO courseMaterialDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {

				try {
					ArrayList<CourseMaterial> list = courseMaterialDao.getAll();
					map.addAttribute("success", list);
					return "faculty-course-material";
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

	@RequestMapping(value = "/faculty/addCourseMaterial.htm", method = RequestMethod.GET)
	public String addCourseMaterials(HttpServletRequest request, CourseDAO courseDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
				try {
					map.addAttribute("courseList", courseDao.getAll());
					return "faculty-add-material";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/course-material.htm");
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

	@RequestMapping(value = "/faculty/addCourseMaterial.htm", method = RequestMethod.POST)
	public String addCourseMaterial(HttpServletRequest request, AssignmentDAO assignmentDao,
			CourseMaterialDAO courseMaterialDao, CourseDAO courseDao, ModelMap map,
			@RequestParam("file") CommonsMultipartFile file) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {

				System.out.println("inside controller method");
				try {
					System.out.println("inside controller method");
					String description = request.getParameter("desc");
					description = description.replaceAll("[^a-zA-Z0-9]", "");
					String fileName = file.getOriginalFilename();
					if (fileName == null || fileName.equals(""))
						fileName = file.getOriginalFilename();
					String course = request.getParameter("course");
					User uploadedBy = u;
					String filePath = "D:\\Project_Files\\";
					try {
						File uploadedFile = new File(filePath, fileName);
						uploadedFile.createNewFile();
						FileInputStream fileInputStream = new FileInputStream(uploadedFile);
						// convert file into array of bytes
						fileInputStream.read(file.getBytes());
						fileInputStream.close();
					} catch (Exception e) {
						map.addAttribute("errorMessage", "Error uploading file!");
						map.addAttribute("backLink", "/faculty/course-material.htm");
						return "error";
					}

					CourseMaterial cm = new CourseMaterial();
					cm.setCourse(courseDao.get(course));
					cm.setFileName(fileName);
					cm.setFilePath(filePath);
					cm.setUploadedBy(uploadedBy);
					cm.setFileDesc(description);
					cm.setType(file.getContentType());
					courseMaterialDao.uploadFile(cm);

					ArrayList<CourseMaterial> list = courseMaterialDao.getAll();
					map.addAttribute("success", list);

					return "faculty-course-material";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/course-material.htm");
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

	@RequestMapping(value = "/faculty/addAssignment.htm", method = RequestMethod.POST)
	public String addAssignment(HttpServletRequest request, AssignmentDAO assignmentDao,
			CourseMaterialDAO courseMaterialDao, CourseDAO courseDao, ModelMap map,
			@RequestParam("file") CommonsMultipartFile file) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {

				System.out.println("inside controller method");
				try {
					System.out.println("inside controller method");
					String title = request.getParameter("title");
					title = title.replaceAll("[^a-zA-Z0-9]", "");
					String description = request.getParameter("desc");
					description = description.replaceAll("[^a-zA-Z0-9]", "");
					String startDateStr = request.getParameter("deadline");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// surround below line with try catch block as below code throws checked
					// exception
					Date startDate = sdf.parse(startDateStr);
					String fileName = file.getOriginalFilename();
					String course = request.getParameter("course");
					User uploadedBy = u;
					String filePath = "D:\\Project_Files\\";
					try {
						File uploadedFile = new File(filePath, fileName);
						uploadedFile.createNewFile();
						FileInputStream fileInputStream = new FileInputStream(uploadedFile);
						// convert file into array of bytes
						fileInputStream.read(file.getBytes());
						fileInputStream.close();
					} catch (Exception e) {
						map.addAttribute("errorMessage", "Error uploading file!");
						map.addAttribute("backLink", "/faculty/course-material.htm");
						return "error";
					}

					CourseMaterial cm = new CourseMaterial();
					cm.setCourse(courseDao.get(course));
					cm.setFileName(fileName + "-" + title);
					cm.setFilePath(filePath);
					cm.setUploadedBy(uploadedBy);
					cm.setFileDesc(description);
					cm.setType(file.getContentType());
					courseMaterialDao.uploadFile(cm);

					Assignment a = new Assignment();
					a.setCourse(courseDao.get(course));
					a.setDeadline(startDate);
					a.setDesc(description);
					a.setTitle(title);
					assignmentDao.createAssignment(a);
					ArrayList<Assignment> list = assignmentDao.getAll();
					map.addAttribute("success", list);

					return "faculty-assignment-page";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/course-material.htm");
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

	@RequestMapping(value = "/faculty/removeCourseMaterial.htm", method = RequestMethod.GET)
	public String removeCourseMaterials(HttpServletRequest request, CourseMaterialDAO courseDao, ModelMap map) {
		System.out.println("inside controller method");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {

				try {

					System.out.println("inside controller method");
					String fileId = request.getParameter("fileId");

					courseDao.delete(fileId);
					ArrayList<CourseMaterial> list = courseDao.getAll();
					map.addAttribute("success", list);

					return "faculty-course-material";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/course-material.htm");
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

	@RequestMapping(value = "/faculty/removeAnnouncement.htm", method = RequestMethod.GET)
	public String removeAnnouncement(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		System.out.println("inside controller method");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {

				try {

					System.out.println("inside controller method");
					String Id = request.getParameter("Id");

					annDao.delete(Id);

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

					return "faculty-announcement";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/announcements.htm");
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

	@RequestMapping(value = "/faculty/removeAnnouncement.htm", method = RequestMethod.POST)
	public String removeSomeAnnouncement(HttpServletRequest request, AnnouncementDAO annDao, ModelMap map) {
		System.out.println("inside controller method");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {

				try {
					System.out.println("inside controller method");
					String Id = request.getParameter("Id");

					String select[] = request.getParameterValues("ids");

					if (select != null) {

						for (String s : select) {
							annDao.delete(s);
						}
					}

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

					return "faculty-announcement";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/announcements.htm");
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

	@RequestMapping(value = "/faculty/assignments.htm", method = RequestMethod.GET)
	public String showAssignments(HttpServletRequest request, AssignmentDAO aDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			// session.setAttribute("user-type", "Faculty");
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
				try {
					ArrayList<Assignment> list;
					list = aDao.getAll();
					map.addAttribute("success", list);
					return "faculty-assignment-page";
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

	@RequestMapping(value = "/faculty/addAssignment.htm", method = RequestMethod.GET)
	public String addAssignment(HttpServletRequest request, CourseDAO cDao, ModelMap map) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("logged-user");
		if (u != null) {
			String userType = (String) session.getAttribute("user-type");
			if (userType.equals("Faculty")) {
				try {
					map.addAttribute("courseList", cDao.getAll());
					return "faculty-add-assignment";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					map.addAttribute("errorMessage", "Some error occured!");
					map.addAttribute("backLink", "/faculty/assignments.htm");
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

}
