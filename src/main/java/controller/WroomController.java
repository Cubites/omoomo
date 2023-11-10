package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WroomController implements Controller {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 추가
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login_user_name") == null
                || "".equals(session.getAttribute("login_user_name"))) {
            return "redirect:/home.do";
        }

        return "wroom";
    }

}
