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

import user.UserVO;

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
        // 추가
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login_user_name") == null
                || "".equals(session.getAttribute("login_user_name"))) {
            response.sendRedirect("/omoomo/home.do");

        } else {
            RoomMap map = RoomMap.getRoomMap();

            String num = request.getParameter("num");
            String mode = request.getParameter("mode");
            System.out.println("방이 있는가?: " + map.get(num) == null);

            // 추가-장원
            String userName = request.getParameter("username");
            
            UserVO vo = new UserVO();
            vo.setUser_name(userName);
//
//            // 추가-장원
            System.out.println("userName" + vo.getUser_name());
//
            System.out.println("Room Number :" + num);
            System.out.println("Game Mode : " + mode);

            ServletContext sc = request.getServletContext();
            // Map<String, RoomVO> map = (HashMap) sc.getAttribute("roomMap");
//            RoomMap map = (RoomMap) sc.getAttribute("roomMap");

            // 추가장원
//            map.get(num).getUserlist().add(vo);s

            
            map.get(num).setPeopleNum(map.get(num).getPeopleNum() + 1);
            sc.setAttribute("roomMap", map);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/gamePage.jsp");
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("roomNumber", num);
            httpSession.setAttribute("mode", mode);

            System.out.println("httpSession.getAttribute: " + httpSession.getAttribute("roomNum"));

//            System.out.println("방에 인원이?: " + map.get(num).getUserlist().size());
            if (num != null) {
                dispatcher.forward(request, response);
            }
        }
    }

}