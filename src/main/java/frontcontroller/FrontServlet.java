package frontcontroller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;


@WebServlet("*.do")
public class FrontServlet extends HttpServlet {
	private ControllerMapper mapper;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		mapper = new ControllerMapper();
	}
   
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String contextPath = request.getContextPath();
		
		//localhost:8080/~에서 contextPath랑 쿼리스트링 제외한 부분 
		String commandUrl = getCommandPath(request,contextPath);
		
		
		
		//맵퍼에서 맵핑된 컨트롤러를 찾기 
		Controller cont = mapper.getController(commandUrl);
		
		
		
		if(cont == null) {//매핑된 컨트롤러가 없다면 일단 홈화면으로 처리
			response.sendRedirect("/omoomo/home.do");
		}
		
		//컨트롤러 호출 결과로 nextPage얻음
		String nextPage = cont.handle(request, response);
		 
		//nextPage가 redirect라면 처리
		if(nextPage.contains("redirect:")) {
			nextPage = nextPage.substring("redirect:".length());
			String location = contextPath + nextPage;
			response.sendRedirect(location);
			
		}else {
			String viewPath;
			if(nextPage.startsWith("omokRoom")) {
			  viewPath = omokViewResolve(nextPage);	
			}else {
			//뷰리졸버를 통해  WEB-INF밑에 있는 viewPath를얻어서 포워딩
			 viewPath = viewResolve(nextPage);
			}
			request.getRequestDispatcher(viewPath).forward(request, response);
			
		}
		
		
	}
	
	
	
	
	private String omokViewResolve(String nextPage) {
		String prefix = "/WEB-INF/omok/";
		String suffix = ".jsp";
		return prefix+nextPage+suffix;
	}

	private String viewResolve(String nextPage) {
		String prefix = "/WEB-INF/view/member/";
		String suffix = ".jsp";
		
		return prefix+nextPage+suffix;
	}

	private String getCommandPath(HttpServletRequest request,String contextPath) {
		
		 String url = request.getRequestURI();
		 
		 
		 String commandUrl = url.substring(contextPath.length());
		
		 return commandUrl;
	}
}
