package room;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
public class RoomInfoServlet extends HttpServlet {
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
        String mode = request.getParameter("mode");

        System.out.println("Room Number :" + num);
        System.out.println("Game Mode : " + mode);

        ServletContext sc = request.getServletContext();
        Map<String, RoomVO> map = (HashMap) sc.getAttribute("roomMap");
        map.get(num).setPeopleNum(map.get(num).getPeopleNum() + 1);
        sc.setAttribute("roomMap", map);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/chatPage.jsp");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("roomNumber", num);
        httpSession.setAttribute("mode", mode);

        System.out.println("httpSession.getAttribute: " + httpSession.getAttribute("roomNum"));

        if (num != null) {
            dispatcher.forward(request, response);
        }
    }

}