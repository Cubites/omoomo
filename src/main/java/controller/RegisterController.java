package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;
import user.UserVO;

public class RegisterController implements Controller {

	@Override
	public String handle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getMethod(); //요청이 get인지 post인지 구분 
		
		// get요청은 회원가입 폼 버튼 클릭 (home에서 '회원가입 하러가기' 링크 클릭 시)
		if("get".equalsIgnoreCase(method)) {
			return "registerForm"; //nextPage return
		}
			
		// post요청 (회원가입 정보 입력 후 form에서 submit시 로직처리)
		UserVO user = new UserVO(request.getParameter("user_name"), request.getParameter("user_pw"));
		UserDAO userDAO = new UserDAO();
		userDAO.addMember(user);
		
		return "redirect:/home.do"; //회원가입 성공 후 main 페이지로 이동
	}

}
