package websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

// Http Session 값을 가져오기 위한 작업
public class ChatroomServerconfigurator extends ServerEndpointConfig.Configurator {
    @Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // ServerEndpointConfig(웹 소켓에서 사용할 값)에 Http Session에 들어있는 유저 이름, 방 이름, 방 모드 값을 넣음
		sec.getUserProperties().put("username", (String) ((HttpSession) request.getHttpSession()).getAttribute("login_user_name"));
		sec.getUserProperties().put("roomNumber", (String) ((HttpSession) request.getHttpSession()).getAttribute("roomNumber"));
		sec.getUserProperties().put("mode", (String) ((HttpSession) request.getHttpSession()).getAttribute("mode"));
	}
}