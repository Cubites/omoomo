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

		response.setContentType("text");
		PrintWriter writer = response.getWriter();
		
		String id = (String) request.getParameter("id");
		System.out.println("id = " + id);
		UserDAO userDAO = new UserDAO();
		boolean overlappedID = userDAO.overlappedID(id);
		System.out.println(">>"+overlappedID);
		if (overlappedID == true) {
			writer.print("not_usable");
		} else 
			writer.print("usable");
	}

}
