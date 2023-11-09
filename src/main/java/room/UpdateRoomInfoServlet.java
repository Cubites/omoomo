package room;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateRoom")
public class UpdateRoomInfoServlet extends HttpServlet {
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
        
        //roomNumber번 방에서 username유저가 나감
        String roomNumber = request.getParameter("roomNumber");
        String username = request.getParameter("username");
        System.out.println("정보 변한 방의 roomNumber :" +roomNumber);
        System.out.println("나간 유저 번호 : " + username);
        
        
        ServletContext sc = request.getServletContext();
        Map<String, RoomVO> map = (HashMap)sc.getAttribute("roomMap");
        if(map.get(roomNumber).getPeopleNum()==2) {
            map.get(roomNumber).setPeopleNum(1);
        }else if(map.get(roomNumber).getPeopleNum()==1) {
            map.remove(roomNumber);
        }
        
        Set keySet= map.keySet();
        
        sc.setAttribute("roomMap", map);
        sc.setAttribute("keySet", keySet);
               
        RequestDispatcher dispatcher = request.getRequestDispatcher("wroom.do");
        
        if(username != null) {
            dispatcher.forward(request, response);
        }
    }

}