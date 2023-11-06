package websocket;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint(value="/chatroomServerEndpoint", configurator=ChatroomServerconfigurator.class)
public class ChatroomServerEndpoint {
	// String = 방 번호, Set = 방에 있는 유저 목록, Session = 유저
	static Map<String, Set<Session>> roomNUsers = new HashMap<>();
	
	@OnOpen
	public void handleOpen(EndpointConfig endpointConfig, Session userSession) {
		// 세션에 이름과 방 번호 저장
		userSession.getUserProperties().put("username", endpointConfig.getUserProperties().get("username"));
		userSession.getUserProperties().put("roomNumber", endpointConfig.getUserProperties().get("roomNumber"));
		
		// 세션에 해당 번호의 방이 있는지 확인
		if(roomNUsers.get(endpointConfig.getUserProperties().get("roomNumber")) != null) {
			// 생성된 방이 있는 경우, 해당 방에 접속(해당 방에 접속한 유저 저장)
			userSession.getUserProperties().put("stonecolor", "black");
			roomNUsers.get(endpointConfig.getUserProperties().get("roomNumber")).add(userSession);
		} else {
			// 생성된 방이 없는 경우, 해당 번호의 방 생성 및 접속(키 값이 roomNumber, 값이 HashSet인 쌍 생성)
			userSession.getUserProperties().put("stonecolor", "white");
			roomNUsers.put((String) endpointConfig.getUserProperties().get("roomNumber"), new HashSet<Session>(Arrays.asList(userSession)));
		}
		
		// 현재 소켓 접속자 현황 확인용 로그
		System.out.println("[서버] 유저 입장");
		System.out.println("===== 현재 방 현황 =====");
		System.out.println("| 방 번호 | 현재 유저 수 |");
		for(String room : roomNUsers.keySet()) {
			System.out.println("---------------------");
			System.out.println("| " + room + "번방 | " + roomNUsers.get(room).size() + "명 |");
		}
		System.out.println("---------------------");
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession) throws Exception {
		System.out.println("[서버] 메세지 받음");
		String username = (String) userSession.getUserProperties().get("username");
		String roomNumber = (String) userSession.getUserProperties().get("roomNumber");
		if(username != null) {
			JSONObject reqMessage = new JSONObject(message);
			System.out.println("요청 유형: " + reqMessage.get("sign"));
			if("chat".equals(reqMessage.get("sign"))) {
				// 받은 메시지가 채팅 메시지 인 경우 - 채팅 메시지를 같은 방내 다른 사용자에게 전송
				roomNUsers.get(roomNumber).stream().forEach(x -> {
					try{
						x.getBasicRemote().sendText(buildJsonData(username, (String) reqMessage.get("m")));
					} catch(Exception e) {
						e.printStackTrace();
					}
				});
			} else if("stone".equals(reqMessage.get("sign"))){
				// 받은 메시지가 돌을 놓은 좌표인 경우 - 놓은 좌포를 같은 방내 다른 사용자에게 전송
				
				// 전체(or 놓은 위치 주변) 오목판 정보를 받아 오목 판별
				
				/******** 여기에 오목 판별 로직 추가 ********/
				
				// 판별 결과와 놓은 위치 값을 같은 방내 다른 사람에게 전송
				
				roomNUsers.get(roomNumber).stream().forEach(x -> {
					try{
						x.getBasicRemote().sendText(buildJsonData(username,  ("x: " + reqMessage.get("x") + ", y: " + reqMessage.get("y"))));
					} catch(Exception e) {
						e.printStackTrace();
					}
				});
			}
		}
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		// 방에서 나가는 경우, 해당 인원의 세션 제거
		roomNUsers.get(userSession.getUserProperties().get("roomNumber")).remove(userSession);
		
		// 방에 아무도 없는 경우, 방 삭제
		if(roomNUsers.get(userSession.getUserProperties().get("roomNumber")).size() == 0) {
			roomNUsers.remove(userSession.getUserProperties().get("roomNumber"));
		}
		
		// 현재 소켓 접속자 현황 확인용 로그
		System.out.println("[서버] 유저 나감");
		System.out.println("===== 현재 방 현황 =====");
		System.out.println("| 방 번호 | 현재 유저 수 |");
		for(String room : roomNUsers.keySet()) {
			System.out.println("---------------------");
			System.out.println("| " + room + "번방 | " + roomNUsers.get(room).size() + "명 |");
		}
		System.out.println("---------------------");
	}
	
	@OnError
	public void handleError(Throwable t) {}
	
	private String buildJsonData(String username, String message) {
		// 채팅 메세지 생성 메서드
		System.out.println("[서버] 메세지 생성");
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username + ": " + message).build();
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
		return stringWriter.toString();
	}
}