package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import room.RoomVO;

public class UpdateRoomController implements Controller {
    
    Map<String, RoomVO> map = new HashMap<>();
    
    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("roomname");
        String mode = request.getParameter("mode");
        
        
        ServletContext sc = request.getServletContext();
        HttpSession sess = request.getSession();
        
        RoomVO vo = new RoomVO();
        vo.setName(name);
        vo.setMode(mode);
        //vo.setOwner((String)sess.getAttribute("login_user_id")); //? 
        vo.setNum(vo.genRoomNumber());
        vo.setPeopleNum(0);
        map.put(vo.getNum(), vo);
        Set keySet= map.keySet();
//      HttpSession sess = request.getSession();

        sc.setAttribute("roomMap", map);
        sc.setAttribute("keySet", keySet);
        System.out.println(name);
        System.out.println(mode);
        System.out.println(sc.getAttribute(mode));
        
        return "wroom";
    }

}