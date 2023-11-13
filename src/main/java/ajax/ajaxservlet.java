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
		
		//파라미터로 이름을 받아서 
		String name = request.getParameter("name");
		
		System.out.println(name + "login");
		
		UserDAO dao = new UserDAO();
		
		//LIKE에 들어갈 이름을 매개변수로 넣어서 list받아옴 
		List<UserVO> list = dao.MemberList(name);
		
		JsonArray jsonArray = new JsonArray();
		
		//list에서 VO를 꺼내서 JSON Object로 만들기 
		for(UserVO vo : list) {
			JsonObject jo = new JsonObject();
			jo.addProperty("userName", vo.getUser_name());
			jo.addProperty("win", vo.getUser_win());
			jo.addProperty("lose", vo.getUser_lose());
			jo.addProperty("rank", vo.getRanking());
			jsonArray.add(jo);
		}
		
		//json을 보내기 위해 String으로 변환
		String jsonString = jsonArray.toString();
		
		System.out.println(jsonString);
		
		//!! HTTP MessageBody에 값을 넣기 위해서는 반드시 ContentType을 지정해야함 
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		response.getWriter().print(jsonArray);
	}

}
