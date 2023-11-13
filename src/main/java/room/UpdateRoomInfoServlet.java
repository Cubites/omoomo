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

    /*
    RoomMap.java로 대체되어 안 쓰이는 코드
    */
    //doHandle로 get, post 요청 둘 다 받음
    protected void doHandle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        
        //roomNumber번 방에서 username유저가 나감
        //방에서 나갔을 때, 그 때의 방번호와 나간 유저의 이름이 넘어옴
        String roomNumber = request.getParameter("roomNumber");
        String username = request.getParameter("username");
        System.out.println("정보 변한 방의 roomNumber :" +roomNumber);
        System.out.println("나간 유저 번호 : " + username);
        
        //roomMap의 정보를 업데이트
        ServletContext sc = request.getServletContext();
        Map<String, RoomVO> map = (HashMap)sc.getAttribute("roomMap");
        map.get(roomNumber).setPeopleNum(map.get(roomNumber).getPeopleNum()-1);
        if(map.get(roomNumber).getPeopleNum() <= 0) {
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