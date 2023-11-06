package websocket;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserNameServlet")
public class UserNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/chatPage.jsp");
		HttpSession httpSession = request.getSession();
		String username = request.getParameter("username");
		httpSession.setAttribute("username", username);
		httpSession.setAttribute("roomNumber", (String) request.getParameter("roomNumber"));
		
		if(username != null) {
			dispatcher.forward(request, response);
		}
	}

}
