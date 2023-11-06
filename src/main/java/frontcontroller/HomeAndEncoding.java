package frontcontroller;

import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class HomeAndEncoding implements Filter {

 
   

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpServletRequest req = (HttpServletRequest)request;
		//String url = req.getRequestURI();
		//String protocol = request.getProtocol();
		
		
//		if(!url.contains(".do")) {
//			System.out.println("필터!");
//			String contextPath = req.getContextPath();
//			HttpServletResponse resp = (HttpServletResponse) response;
//			resp.sendRedirect(contextPath+"/home.do");
//		}else {
		chain.doFilter(request, response);
		//}
		
	}

	
	
}
