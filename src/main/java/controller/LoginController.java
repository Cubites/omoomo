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
		System.out.println("아이디 ->" + request.getParameter("user_name"));
		System.out.println("비밀번호 ->" + request.getParameter("user_pw"));
		
		// 사용자가 입력한 아이디와 비밀번호로 UserVO 객체 만들어서 DB에 존재하는지 확인
		UserVO user = new UserVO(request.getParameter("user_name"), request.getParameter("user_pw"));
		UserDAO userDAO = new UserDAO();
		
		boolean result = userDAO.isExisted(user);
		if (result) {
		    // 존재하면 로그인 성공 -> wroom으로 이동
			HttpSession session = request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("login_user_name", request.getParameter("user_name"));
			session.setAttribute("login_user_pw", request.getParameter("user_pw"));
			return "redirect:/wroom.do";
		} else {
		    // 존재하지 않다면 로그인 실패 -> isLogOn = false 값 넣어서 redirect
			return "redirect:/home.do?isLogOn=false";
		}
		
	}
	
	
}
