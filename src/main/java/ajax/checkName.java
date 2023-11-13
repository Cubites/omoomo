package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;

@WebServlet("/checkName")
public class checkName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // 아이디 중복 확인 로직
		response.setContentType("text");
		// 바로 값을 보낼 수 있도록 사용
		PrintWriter writer = response.getWriter();
		
		// ajax로 보낸 id 받아서 아이디 중복 확인 함수 실행
		String id = (String) request.getParameter("id");
		UserDAO userDAO = new UserDAO();
		boolean overlappedID = userDAO.overlappedID(id);
		
		// 실행 결과에 따라서 다른 값 넣어서 보내줌
		if (overlappedID == true) {
			writer.print("not_usable");
		} else 
			writer.print("usable");
	}

}
