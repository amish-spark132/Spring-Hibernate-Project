package com.blackboard.www.controller;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blackboard.www.dao.UserDAO;
import com.blackboard.www.pojo.User;
import com.captcha.botdetect.web.servlet.Captcha;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	/*
	 * @Autowired
	 * 
	 * @Qualifier("userDao") UserDAO userDao;
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/user/login.htm", method = RequestMethod.GET)
	public String showLoginForm() {

		return "user-login";
	}

	@RequestMapping(value = "/user/login.htm", method = RequestMethod.POST)
	public String handleLoginForm(HttpServletRequest request, HttpServletResponse response, UserDAO userDao,
			ModelMap map) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// String role = request.getParameter("role");
		try {
			User u = userDao.get(username, password);

			if (u != null && u.getStatus() == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("logged-user", u);
				String[] isSelected = request.getParameterValues("rememberMe");
				if (isSelected != null) {
					Cookie c = new Cookie("username", username);
					c.setMaxAge(24 * 60 * 60);
					response.addCookie(c);
				}
				if (u.getRole().equals("Faculty")) {
					session.setAttribute("user-type", "Faculty");
					return "faculty-dashboard";
				} else if (u.getRole().equals("Student")) {
					session.setAttribute("user-type", "Student");
					return "student-dashboard";
				} else if (u.getRole().equals("Admin")) {
					session.setAttribute("user-type", "Admin");
					return "admin-panel";
				}
			} else if (u != null && u.getStatus() == 0) {
				map.addAttribute("errorMessage", "Please activate your account to login!");
				return "error";
			} else {
				map.addAttribute("errorMessage", "Invalid username/password!");
				return "error";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/user/logout.htm", method = RequestMethod.GET)
	public String logoutuser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cookie cookie = new Cookie("username", "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		session.invalidate();
		return "user-login";
	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.GET)
	public String showCreateForm() {

		return "user-create-form";
	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.POST)
	public String handleCreateForm(HttpServletRequest request, UserDAO userDao, ModelMap map) {
		Captcha captcha = Captcha.load(request, "CaptchaObject");
		String captchaCode = request.getParameter("captchaCode");
		HttpSession session = request.getSession();
		if (captcha.validate(captchaCode)) {
			try {
				String emailid = request.getParameter("email");
				String password = request.getParameter("password");
				String username = request.getParameter("username");
				String fname = request.getParameter("fname");
				String lname = request.getParameter("lname");
				String role = request.getParameter("role");

				User user = new User();
				user.setUsername(username);
				com.blackboard.www.pojo.Email email = new com.blackboard.www.pojo.Email(emailid);
				user.setEmail(email);
				user.setPassword(password);
				user.setLastName(lname);
				user.setFirstName(fname);
				user.setRole(role);

				// Change later
				user.setStatus(1);
				email.setUser(user);
				User u = userDao.register(user);
				Random rand = new Random();
				int randomNum1 = rand.nextInt(5000000);
				int randomNum2 = rand.nextInt(5000000);
				try {
					String str = "http://localhost:8080/www/user/validateemail.htm?email=" + emailid + "&key1="
							+ randomNum1 + "&key2=" + randomNum2;
					session.setAttribute("key1", randomNum1);
					session.setAttribute("key2", randomNum2);
					sendEmail(emailid, "Click on this link to activate your account : " + str);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					System.out.println("Email cannot be sent");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			map.addAttribute("errorMessage", "Invalid Captcha!");
			return "user-create-form";
		}

		return "user-created";
	}

	@RequestMapping(value = "/user/forgotpassword.htm", method = RequestMethod.GET)
	public String getForgotPasswordForm(HttpServletRequest request) {

		return "forgot-password";
	}

	@RequestMapping(value = "/user/forgotpassword.htm", method = RequestMethod.POST)
	public String handleForgotPasswordForm(HttpServletRequest request, UserDAO userDao, ModelMap map) {

		String email = request.getParameter("email");

		Captcha captcha = Captcha.load(request, "CaptchaObject");
		String captchaCode = request.getParameter("captchaCode");

		if (captcha.validate(captchaCode)) {
			String emailaddress = userDao.getEmail(email).getEmailAddress();
			if (emailaddress != null) {
				sendEmail(emailaddress, "Your password is : " + userDao.getEmail(email).getUser().getPassword());
				System.out.println("Your password is : " + userDao.getEmail(email).getUser().getPassword());
				return "forgot-password-success";
			} else {
				map.addAttribute("errorMessage", "No user found with that email address!");
				return "error";
			}
		} else {
			request.setAttribute("captchamsg", "Captcha not valid");
			return "forgot-password";
		}
	}

	@RequestMapping(value = "user/resendemail.htm", method = RequestMethod.POST)
	public String resendEmail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String useremail = request.getParameter("username");
		Random rand = new Random();
		int randomNum1 = rand.nextInt(5000000);
		int randomNum2 = rand.nextInt(5000000);
		try {
			String str = "http://localhost:8080/www/user/validateemail.htm?email=" + useremail + "&key1=" + randomNum1
					+ "&key2=" + randomNum2;
			session.setAttribute("key1", randomNum1);
			session.setAttribute("key2", randomNum2);
			sendEmail(useremail, "Click on this link to activate your account : " + str);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Email cannot be sent");
		}

		return "user-created";
	}

	public void sendEmail(String useremail, String message) {
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("blackboard.test132@gmail.com", "blackboard132"));
			email.setSSLOnConnect(true);
			email.setFrom("blackboard.test132@gmail.com"); // This user email does not
			email.setSubject("Password Reminder");
			email.setMsg(message); // Retrieve email from the DAO and send this
			email.addTo(useremail);
			email.send();
			/*
			 * } catch (EmailException e) { System.out.println(e.getMessage());
			 * System.out.println("Email cannot be sent"); }
			 */
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@RequestMapping(value = "user/validateemail.htm", method = RequestMethod.GET)
	public String validateEmail(HttpServletRequest request, UserDAO userDao, ModelMap map) {

		// The user will be sent the following link when the use registers
		// This is the format of the email
		// http://hostname:8080/lab10/user/validateemail.htm?email=useremail&key1=<random_number>&key2=<body
		// of the email that when user registers>
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		int key1 = Integer.parseInt(request.getParameter("key1"));
		int key2 = Integer.parseInt(request.getParameter("key2"));
		System.out.println(session.getAttribute("key1"));
		System.out.println(session.getAttribute("key2"));

		if ((Integer) (session.getAttribute("key1")) == key1 && ((Integer) session.getAttribute("key2")) == key2) {
			try {
				System.out.println("HI________");
				boolean updateStatus = userDao.updateUser(email);
				if (updateStatus) {
					return "user-login";
				} else {

					return "error";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			map.addAttribute("errorMessage", "Link expired , generate new link");
			map.addAttribute("resendLink", true);
			return "error";
		}

		return "user-login";

	}

}
