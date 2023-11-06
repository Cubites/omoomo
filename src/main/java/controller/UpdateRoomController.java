package controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import roomVO.RoomVO;

public class UpdateRoomController implements Controller {
    
    Set<RoomVO> set = new HashSet<>();
    
    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("roomname");
        String mode = request.getParameter("mode");
        
        ServletContext sc = request.getServletContext();
        
        RoomVO vo = new RoomVO();
        vo.setName(name);
        vo.setMode(mode);
        
        set.add(vo);
        
//      HttpSession sess = request.getSession();

        sc.setAttribute("roomSet", set);
        System.out.println(name);
        System.out.println(mode);
        
        return "wroom";
    }

}
