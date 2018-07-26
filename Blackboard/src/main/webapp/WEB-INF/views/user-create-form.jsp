<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.captcha.botdetect.web.servlet.Captcha"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
<script>
	function checkUsername() {

		var xmlHttp;
		try // Firefox, Opera 8.0+, Safari
		{
			xmlHttp = new XMLHttpRequest();
		} catch (e) {
			try // Internet Explorer
			{
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
					alert("Your browser does not support AJAX!");
					return false;
				}
			}
		}

		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4) {
				document.getElementById("usernameAvailability").innerHTML = xmlHttp.responseText;
			}
		}

		var queryString = document.getElementById("username").value;

		xmlHttp.open("POST", "../usernameavailability.htm?username=" + queryString, true);
		xmlHttp.send();
	}
	
	function checkEmail() {

		var xmlHttp;
		try // Firefox, Opera 8.0+, Safari
		{
			xmlHttp = new XMLHttpRequest();
		} catch (e) {
			try // Internet Explorer
			{
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
					alert("Your browser does not support AJAX!");
					return false;
				}
			}
		}

		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4) {
				document.getElementById("emailAvailability").innerHTML = xmlHttp.responseText;
			}
		}

		var queryString = document.getElementById("email").value;

		xmlHttp.open("POST", "../emailavailability.htm?email=" + queryString, true);
		xmlHttp.send();
	}
</script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="${contextPath}/resources/images/icons/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/vendor/select2/select2.min.css">
<!--==============================================================;/=================================-->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/util.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/main.css">
</head>
<body>

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form validate-form"
					action="${contextPath}/user/create.htm" method="POST">
					<span class="login100-form-title"> Create Account </span>
					<div class="wrap-input100 validate-input"
						data-validate="Valid email is required: ex@abc.xyz">
						<input class="input100" type="email" name="email" id="email"
							placeholder="Email" required="required" onblur="checkEmail()"> <span
							class="focus-input100"></span> <span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
					</div>
					<div id="emailAvailability"></div>
					
					<div class="wrap-input100 validate-input" data-validate="UserName is required">
						<input class="input100" type="text" name="username" id="username"
							placeholder="Username" required="required" onblur="checkUsername()"> <span
							class="focus-input100"></span> <span class="symbol-input100"> </span>
					</div>
					<div id="usernameAvailability"></div>
					<div class="wrap-input100 validate-input"
						data-validate="Password is required">
						<input class="input100" type="password" name="password"
							placeholder="Password" required="required"> <span
							class="focus-input100"></span> <span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input"
						data-validate="First Name is required">
						<input class="input100" type="text" name="fname" id="fname"
							placeholder="First Name" required="required"> <span
							class="focus-input100"></span> <span class="symbol-input100">

						</span>
					</div>

					<div class="wrap-input100 validate-input"
						data-validate="Last Name is required">
						<input class="input100" type="text" name="lname" id="lname"
							placeholder="Last Name" required="required"> <span
							class="focus-input100"></span> <span class="symbol-input100">

						</span>
					</div>

					<div class="wrap-input100">
						<select id="role" name="role" class="input100">
							<option value="NONE">--- Select ---</option>
							<option value="Faculty">Faculty</option>
							<option value="Student">Student</option>
							<option value="Admin">Admin</option>
						</select> <span class="focus-input100"></span> <span
							class="symbol-input100"> <i class="fa fa-lock"
							aria-hidden="true"></i>
						</span>
					</div>

					<div class="wrap">
						<label for="captchaCode" class="prompt">Retype the
							characters from the picture:</label>
						<%
							// Adding BotDetect Captcha to the page
							Captcha captcha = Captcha.load(request, "CaptchaObject");
							captcha.setUserInputID("captchaCode");

							String captchaHtml = captcha.getHtml();
							out.write(captchaHtml);
						%>
						<input class="input100" id="captchaCode" type="text"
							name="captchaCode" required="required" border="1" /> <span
							class="focus-input100"></span> <span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn">Create Account</button>
					</div>

				</form>

			</div>
		</div>
	</div>




	<!--===============================================================================================-->
	<script
		src="${contextPath}/resources/vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
	<script src="${contextPath}/resources/vendor/bootstrap/js/popper.js"></script>
	<script
		src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="${contextPath}/resources/vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="${contextPath}/resources/vendor/tilt/tilt.jquery.min.js"></script>
	<script>
		$('.js-tilt').tilt({
			scale : 1.1
		})
	</script>
	<!--===============================================================================================-->
	<script src="${contextPath}/resources/js/main.js"></script>

	<script>
		$('.js-tilt').tilt({
			scale : 1.1
		})
	</script>
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async
		src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag() {
			dataLayer.push(arguments);
		}
		gtag('js', new Date());

		gtag('config', 'UA-23581568-13');
	</script>

	<!--===============================================================================================-->
	<script src="${contextPath}/resources/js/main.js"></script>


	<%-- 	<font color="red">${errorMessage}</font>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<form action="${contextPath}/user/create.htm" method="POST">
		<table>
			<tr>
				<td>User Email:</td>
				<td><input type="text" name="username" id="username" size="30"
					onblur="ajaxEvent()" /></td>
				<td>
					<div id="availability"></div>
				</td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" size="30" /></td>
			</tr>
			<tr>
				<td>Account Type:</td>
				<td><select id="role" name="role">
						<option value="NONE">--- Select ---</option>
						<option value="Faculty">Faculty</option>
						<option value="Student">Student</option>
				</select></td>
			</tr>

			<tr>
				<td colspan="2"><label for="captchaCode" class="prompt">Retype
						the characters from the picture:</label> <%
 	// Adding BotDetect Captcha to the page
 	Captcha captcha = Captcha.load(request, "CaptchaObject");
 	captcha.setUserInputID("captchaCode");

 	String captchaHtml = captcha.getHtml();
 	out.write(captchaHtml);
 %> <input id="captchaCode" type="text" name="captchaCode"
					required="required" /></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Login" /></td>
			</tr>

		</table>
	</form> --%>

</body>
</html>