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
		
		if("get".equalsIgnoreCase(method)) { //get요청은 회원가입 폼 버튼 클릭 
			return "registerForm"; //nextPage return
		}
		System.out.println("==>" + request.getRequestURI());
		
		if ("/omoomo/register_check.do".equals(request.getRequestURI())) {
			PrintWriter writer = response.getWriter();
			
			String id = (String) request.getParameter("id");
			System.out.println("id = " + id);
			UserDAO userDAO = new UserDAO();
			boolean overlappedID = userDAO.overlappedID(id);
			System.out.println(">>"+overlappedID);
			if (overlappedID == true) {
				writer.print("not_usable");
			} else {
				writer.print("usable");
			}			
			
		}
		else {
			
		//post요청 (회원가입 정보 입력후 form에서 submit시 로직처리)
		UserVO user = new UserVO(request.getParameter("user_name"), request.getParameter("user_pw"));
		UserDAO userDAO = new UserDAO();
		userDAO.addMember(user);
		}
		
		return "redirect:/home.do"; //회원가입 성공 후 main 페이지로 이동
	}

}
