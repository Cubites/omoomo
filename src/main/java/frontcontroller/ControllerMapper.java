package frontcontroller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import controller.Controller;
import controller.EnterRoomController;
import controller.HomeController;
import controller.LoginController;
import controller.RegisterController;
import controller.WroomController;

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
