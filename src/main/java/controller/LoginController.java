package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements Controller{

	@Override
	public String handle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		//로그인 로직 실행 
		System.out.println("로그인완료!");
		
		return "redirect:/wroom.do";
	}
	
	
}
