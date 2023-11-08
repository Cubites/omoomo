package servlet_j;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import userRank.RankDAO;
import userRank.RankVO;


@WebServlet("/getMemberList.do")
public class ajaxservlet extends HttpServlet {

       
   	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		
		System.out.println("ajax호출");
		RankDAO dao = new RankDAO();
		List<RankVO> list = dao.MemberList(name);
		
		JsonArray jsonArray = new JsonArray();
		
		
		for(RankVO vo : list) {
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
