package ajax;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.JSONObject;

//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

import user.UserDAO;

@WebServlet("/winAndLose")
public class WinAndLose extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dohandle(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dohandle(request, response);
    }

    protected void dohandle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO dao = new UserDAO();
        String username = request.getParameter("username");
        String result = request.getParameter("result");

        if ("win".equalsIgnoreCase(result)) {
            dao.winRecord(username);
        } else {
            dao.loseRecord(username);
        }

        response.getWriter().print("ok");

    }

}
