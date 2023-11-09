package ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import user.UserDAO;
import user.UserVO;


@WebServlet("/getMemberList.do")
public class ajaxservlet extends HttpServlet {

       
   	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		
		System.out.println(name + "login");
		
		UserDAO dao = new UserDAO();
		List<UserVO> list = dao.MemberList(name);
		
		JsonArray jsonArray = new JsonArray();
		
		
		for(UserVO vo : list) {
			JsonObject jo = new JsonObject();
			jo.addProperty("userName", vo.getUser_name());
			jo.addProperty("win", vo.getUser_win());
			jo.addProperty("lose", vo.getUser_lose());
			jo.addProperty("rank", vo.getRanking());
			jsonArray.add(jo);
		}
		String jsonString = jsonArray.toString();
		
		System.out.println(jsonString);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		response.getWriter().print(jsonArray);
	}

}
