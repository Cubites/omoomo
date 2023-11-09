package room;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class roomInfoServlet
 */
@WebServlet("/roomInfo")
public class roomInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String num = request.getParameter("num");
		System.out.println("request.getParamer :" +num);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/chatPage.jsp");
		HttpSession httpSession = request.getSession();


		httpSession.setAttribute("roomNumber", num);
		
		System.out.println("httpSession.getAttribute: "+httpSession.getAttribute("roomNumber"));
		
		if(num != null) {
			dispatcher.forward(request, response);
		}
	}

}
