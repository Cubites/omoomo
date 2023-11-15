### 공유할 내용

- 웹 소켓 구현 방법
    - 3개의 파일로 구현
        - page.jsp - 화면
        - ServerEndpoint.java - 웹 소켓 Endpoint, 쉽게 말하면 소켓 서버
        - Serverconfigurator.java - 웹 소켓의 설정 값 추가를 위한 파일
    - 각 파일의 핵심 코드
        - page.jsp
            ```html
            <%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8"%>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <!DOCTYPE html>
            <html>
            <head>
            	<meta charset="UTF-8">
            	<title>Insert title here</title>
            	<link href="game_page.css" rel="stylesheet">
            	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
            </head>
            <body>
            	<div id="container">
            		...
            		<input id="testButton" type="button" value="소켓 통신 테스트">
            	</div>
            	<script>
            		// 소켓 연결 요청
            		const websocket = new WebSocket("ws://localhost:8090/omoomo/serverEndpoint");
            		
            		// websocket.onopen - 웹 소켓이 연결되었을 때 실행할 소스를 적는 곳
            		websocket.onopen = function (message) {
            			websocket.send("보낼 내용 입력");
            		}
            
            		// websocket.ommessage - 소켓 서버에서 오는 메세지를 받음
            		websocket.onmessage = function processMessage(message) {
            			console.log(message);
            		}
            		
            		// websocket.onerror - 오류로 소켓 서버와 연결이 끊어지면 실행하는 코드
            		websocket.onerror = function(e){
            			console.log(e);	// 예시 코드 - 에러 출력
            		}
            
            		// 게임 준비를 누르면 소켓에 전송
            		document.getElementById("testButton").addEventListener("click", (e) => {
            			websocket.send("소켓에 메세지를 보냄");
            		});
            	</script>
            </body>
            </html>
            ``` 
        - Serverconfigurator.java
            ```java
            import javax.servlet.http.HttpSession;
            import javax.websocket.HandshakeResponse;
            import javax.websocket.server.HandshakeRequest;
            import javax.websocket.server.ServerEndpointConfig;
            
            // Http Session 값을 가져오기 위한 작업
            public class Serverconfigurator extends ServerEndpointConfig.Configurator {
              @Override
            	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
                // ServerEndpointConfig(웹 소켓에서 사용할 값)에 Http Session에 들어있는 값을 넣음
            		sec.getUserProperties().put("키값", (String) ((HttpSession) request.getHttpSession()).getAttribute("세션에 있는 키값"));
            	}
            }
            ```
            
        - ServetEndpoint.java
            ```java
            import javax.websocket.EndpointConfig;
            import javax.websocket.OnClose;
            import javax.websocket.OnError;
            import javax.websocket.OnMessage;
            import javax.websocket.OnOpen;
            import javax.websocket.Session;
            import javax.websocket.server.ServerEndpoint;
            
            // 소켓 통시 시, 연결되는 곳
            @ServerEndpoint(value="/serverEndpoint", configurator=Serverconfigurator.class)
            public class ServerEndpoint {
            
              /* 소켓이 열렸을 때 동작하는 메서드 */
            	@OnOpen
            	public void handleOpen(EndpointConfig endpointConfig, Session userSession) {
            	  userSession.getBasicRemote().sendText("보낼 메시지 입력");
            	}
            	
            	/* 소켓 통신으로 메세지가 왔을 때 동작하는 소스 */
            	@OnMessage
            	public void handleMessage(String message, Session userSession) throws Exception {
            		userSession.getBasicRemote().sendText("보낼 메시지 입력");
            	}
            	
            	/* 소켓이 닫혔을 때 동작하는 소스 */
            	@OnClose
            	public void handleClose(Session userSession) {
            		userSessiont.getBasicRemote().sendText("보낼 메시지 입력");
            	}
            	
            	/* 소켓 통신 중 에러 발생 시 동작하는 소스 */
            	@OnError
            	public void handleError(Throwable t) {
            		
            	}
            }
            ```
            
- omo omo http 구조
    * 구조 링크: https://github.com/mimi5963/dsomok
    * 개발 환경: 톰캣 v9버전, 자바11버전
    * 기타 : jstl 추가
    
    * 실행화면
        ![스크린샷(33)](https://github.com/Cubites/omoomo/assets/75084369/9d3ace3c-ea48-4fb1-8f22-a2d08e164871)
        →회원가입 버튼 누를 시 
        
        ![스크린샷(34)](https://github.com/Cubites/omoomo/assets/75084369/b21cec98-37e4-48a5-a692-6ead35f31f60)
        →로그인 버튼 누를 시 (로그인 완료라고 생각하고 바로 대기방으로 가도록 했습니다.)
        
        ![스크린샷(35)](https://github.com/Cubites/omoomo/assets/75084369/28953a4d-8c67-4800-a223-6d02c985fa0a)
        →오목방 버튼 클릭시
        
        ![스크린샷(37)](https://github.com/Cubites/omoomo/assets/75084369/6163e5b6-81cd-42c6-bf49-4882556b9bff)
        
        ![스크린샷(36)](https://github.com/Cubites/omoomo/assets/75084369/f4e5d4a1-6f69-43d1-bcce-9d61d5acd46d)
        버튼에 따라 미리 만들어둔 방으로 가도록 했습니다.
        
    
    * 전체 패키지<br>
    <img width="200" alt="구조1 PNG" src="https://github.com/Cubites/omoomo/assets/75084369/acfec3f3-023a-4d5a-8388-d3d800b56784">
    
    - controller 패키지
        
        ```java
        public interface Controller {
        
        public String handle(HttpServletRequest request, HttpServletResponse response) 
        		throws ServletException, IOException;
        
        }
        위 인터페이스를 구현하는 컨트롤러들이 모여있습니다.
        
        아래와 같습니다. 역할은 기존 서블릿과 크게 다를 바 없습니다!! 
        
        public class RegisterController implements Controller {
        
        	@Override
        	public String handle(HttpServletRequest request, HttpServletResponse response)
        			throws ServletException, IOException {
        
        		String method = request.getMethod(); //요청이 get인지 post인지 구분 
        		
        		if("get".equalsIgnoreCase(method)) { //get요청은 회원가입 폼 버튼 클릭 
        			return "registerForm"; //nextPage return
        		}
        		
        		
        		//post요청 (회원가입 정보 입력후 form에서 submit시 로직처리)
        		
        		
        		
        		return "redirect:/home.do"; //회원가입 성공 후 main 페이지로 이동
        	}
        
        }
        ```
        
        매개변수: 기존 서블릿의 request,response와 동일 
        
        * controller의 리턴값 : 
            * **포워드**로 처리할 경우 → **뷰이름만** 리턴 
            * **리다이렉트**로 처리할 경우  → **해당 뷰와 매핑된 컨트롤러**를 호출 
        
        이유는 WEB-INF 밑으로 뷰를 모두 두었기 때문에, **컨트롤러를 통해서 뷰로 가야 하기 때문**
        
        ( home으로 리다이렉트하고 싶다면, 바로 home.jsp로 이동하는 게 아니라, 
        home.do → home.jsp : home.do를 호출하고, home.do가 home.jsp로 이동하도록 만듬. 이런 방식으로 url에서 .jsp와 같은 확장자를 숨김)
        
    - **ControllerMapper**
        
        ```java
        public class ControllerMapper {
        	private Map<String,Controller> mapper;
        	
        	public ControllerMapper() {
        		mapper = new ConcurrentHashMap<>();
        		
        		//url주소와 이를 처리할 컨트롤러 연결
        		mapper.put("/register.do", new RegisterController());
        		mapper.put("/home.do", new HomeController());
        		mapper.put("/wroom.do", new WroomController());
        		mapper.put("/enterRoom.do", new EnterRoomController());
        		mapper.put("/login.do",new LoginController());
        	}
        	
        	public Controller getController(String url) {
        		return mapper.get(url);
        	}
        }
        ```
        
        생성과 동시에 url과 각 컨트롤러들을 매핑
        
        getController: Map에서 url과 매핑된 컨트롤러를 리턴
        
        **새로운 url을 사용하고 싶다**면, 여기에 **url과 이를 처리할 컨트롤러를 등록하면 됨**
        
    - frontServlet
        
        ```java
        @WebServlet("*.do")
        public class FrontServlet extends HttpServlet {
        	private ControllerMapper mapper;
        	
        	@Override //mapper 초기화
        	public void init(ServletConfig config) throws ServletException {
        		mapper = new ControllerMapper();
        	}
           
        	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        		//contextPath
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
        			nextPage = nextPage.substring("redirect:".length()); //redirect:글자빼기
        			String location = contextPath + nextPage; // contextPath합치기
        			response.sendRedirect(location); //redirect
        			
        		}else {
        			String viewPath; // omokRoom과 그냥 view들의 경로가 달라서 확인절차
        			if(nextPage.startsWith("omokRoom")) { 
        			  viewPath = omokViewResolve(nextPage);	//omokRoom에 해당하는 경로 완성
        			}else {
        			//뷰리졸버를 통해  WEB-INF밑에 있는 viewPath를얻어서 포워딩
        			 viewPath = viewResolve(nextPage); 
        			}
        			request.getRequestDispatcher(viewPath).forward(request, response);
        			
        		}
        		
        		
        	}
        }
        ```
        
        → .do요청이 오면, 
        
        1 요청url에서 contextPath제거하고 뒷부분만 남김 
        
        (ex /omomo/home.do → /home.do)
        
        ```jsx
        	String commandUrl = getCommandPath(request,contextPath);
        ```
        
        2. mapper에서 해당 url과 맵핑된 컨트롤러 찾음 
        
             → 만약 맵핑된 컨트롤러가 없으면, home.do로 리다이렉트 (홈화면으로 보냄) 
        
        ```java
        	//맵퍼에서 맵핑된 컨트롤러를 찾기 
        		Controller cont = mapper.getController(commandUrl);
        		
        		
        		
        		if(cont == null) {//매핑된 컨트롤러가 없다면 일단 홈화면으로 처리
        			response.sendRedirect("/omoomo/home.do");
        		}
        ```
        
        3. 찾은 컨트롤러에 요청 request,response를 넘겨서 처리하고 (유사 포워드)
        
         view페이지나 redirect경로를 받음  
        
        ```java
        //컨트롤러 호출 결과로 nextPage얻음
        		String nextPage = cont.handle(request, response);
        ```
        
           
        
        4. 리턴받은 뷰나 리다이렉트 경로를 완성하고 포워딩 혹은 리다이렉트 처리함  
        ```java
        //nextPage가 redirect라면 처리
        		if(nextPage.contains("redirect:")) {
        			nextPage = nextPage.substring("redirect:".length()); //redirect:글자빼기
        			String location = contextPath + nextPage; // contextPath합치기
        			response.sendRedirect(location); //redirect
        			
        		}else {
        			String viewPath; // omokRoom과 그냥 view들의 경로가 달라서 확인절차
        			if(nextPage.startsWith("omokRoom")) { 
        			  viewPath = omokViewResolve(nextPage);	//omokRoom에 해당하는 경로 완성
        			}else {
        			//뷰리졸버를 통해  WEB-INF밑에 있는 viewPath를얻어서 포워딩
        			 viewPath = viewResolve(nextPage); 
        			}
        			request.getRequestDispatcher(viewPath).forward(request, response);
        			
        		}
        ```
        
        - 뷰리졸버
            
            ```java
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
            ```
            
            nextPage의 논리적 이름이 들어오면, 물리적 경로로 변환
            
    - HomeAndEncodingFilter
        ```java
        public class HomeAndEncoding implements Filter {
        
         
          
        	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        		
        		request.setCharacterEncoding("UTF-8");
        		
        		HttpServletRequest req = (HttpServletRequest)request;
        		String url = req.getRequestURI();
        		
        		
        		if(!url.contains(".do")) {
        			String contextPath = req.getContextPath();
        			HttpServletResponse resp = (HttpServletResponse) response;
        			resp.sendRedirect(contextPath+"/home.do");
        		}else {
        		chain.doFilter(request, response);
        		}
        	}
        
        	
        	
        }
        ```
        
        1. request에 encoding설정
        2. request에서 url을 찾은 다음에,  .do가 포함되지 않은 부적절한 요청은 모두 home.do로 보내버림
    
    * 전체 뷰
    <img width="200" alt="전체뷰 PNG" src="https://github.com/Cubites/omoomo/assets/75084369/ee9e2ef3-2b2b-4fdd-a80e-c805a489b67b">

# 각자 구현한 오목
- [장원](https://www.notion.so/6731c3da28264cd2bcab4fafe72aee85?pvs=21)
    
- [주희철](https://www.notion.so/Omok-8b7a221f4e934c009855a74d3e584138?pvs=21)
    
- [강태연](https://www.notion.so/3d3adbf60bcc446bbd5601692dd10bf8?pvs=21)
    
- [김이슬](https://www.notion.so/71f13bd5967f4a7abf8f638050b27f40?pvs=21)
    
- [송진주](https://www.notion.so/ee805bd06daa4976b877e93434534d1d?pvs=21)