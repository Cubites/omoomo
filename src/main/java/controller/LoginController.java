package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;
import user.UserVO;

public class LoginController implements Controller{

	@Override
	public String handle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		//로그인 로직 실행 
		System.out.println("로그인완료!");
		System.out.println("아이디 ->" + request.getParameter("user_name"));
		System.out.println("비밀번호 ->" + request.getParameter("user_pw"));
		
		UserVO user = new UserVO(request.getParameter("user_name"), request.getParameter("user_pw"));
		UserDAO userDAO = new UserDAO();
		boolean result = userDAO.isExisted(user);
		if (result) {
			HttpSession session = request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("login.user_name", request.getParameter("user_name"));
			session.setAttribute("login.user_pw", request.getParameter("user_pw"));
			return "redirect:/wroom.do";
		} else {
			//request.setAttribute("isLogOn", false);
			
			return "redirect:/home.do?isLogOn=false";
		}
		
	}
	
	
}
