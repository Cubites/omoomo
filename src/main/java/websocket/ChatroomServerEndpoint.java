package websocket;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import room.RoomMap;
import room.RoomVO;
import socket.BoardVO;
import socket.SocketConnection;

//@ServerEndpoint("/chatroomServerEndpoint")
@ServerEndpoint(value="/chatroomServerEndpoint", configurator=ChatroomServerconfigurator.class)
public class ChatroomServerEndpoint {
	// 방과 유저 정보
	static SocketConnection sc = new SocketConnection();
	// 게임 판 정보(Map<방번호, 게임판>)
	static Map<String, BoardVO> boards = new HashMap<>();
	
    /* 소켓이 열렸을 때 동작하는 메서드 */
	@OnOpen
	public void handleOpen(EndpointConfig endpointConfig, Session userSession) {
        System.out.println("[소켓 연결됨] 방 번호: " + endpointConfig.getUserProperties().get("roomNumber"));
	    System.out.println("[소켓 연결됨] 유저 이름: " + endpointConfig.getUserProperties().get("username"));
		
	    // 세션에 이름과 방 번호, 준비 여부 저장
        String roomNumber = (String) endpointConfig.getUserProperties().get("roomNumber");
        String username = (String) endpointConfig.getUserProperties().get("username");
        userSession.getUserProperties().put("roomNumber", roomNumber);
		userSession.getUserProperties().put("username", username);
		userSession.getUserProperties().put("ready", false);
		
		// 세션에 해당 번호의 방이 있는지 확인해서, 있으면 유저만 추가, 없으면 방 추가 후 유저 추가
		sc.enterRoom(roomNumber, (Session) userSession);
		
		// 빈 오목판 생성
		boards.put(roomNumber, new BoardVO((String) userSession.getUserProperties().get("mode")));
		
		// 현재 소켓 접속자 현황 확인용 로그
		System.out.println("[소켓 연결됨] 유저 입장");
		sc.printRoomAndSockets();
	}
	
	/* 소켓 통신으로 메세지가 왔을 때 동작하는 소스 */
	@OnMessage
	public void handleMessage(String message, Session userSession) throws Exception {
		System.out.println("[서버] 메세지 받음");
		String username = (String) userSession.getUserProperties().get("username");
		String roomNumber = (String) userSession.getUserProperties().get("roomNumber");
		if(username != null) {
			JSONObject reqMessage = new JSONObject(message);
			System.out.println("[소켓 메시지 받음] 요청 유형: " + reqMessage.get("sign"));
			if("init".equals(reqMessage.get("sign"))) {
			    // 초기 설정 - 방에 입장했을 때
			    sc.setInit(userSession);
			} else if("ready".equals(reqMessage.getString("sign"))) {
                // 사용자가 준비된 경우
			    sc.gameReady(roomNumber, username, reqMessage.getBoolean("value"));
			    sc.gameStart(roomNumber);
            } else if("run".equals(reqMessage.getString("sign"))) {
                // 게임 중 방을 나가는 경우
                sc.runGame(roomNumber, username);
            } else if("chat".equals(reqMessage.getString("sign"))) {
                // 받은 메시지가 채팅 메시지 인 경우
                sc.sendMessage(roomNumber, username, reqMessage.getString("m"));
            } else if("game".equals(reqMessage.getString("sign"))){
                // 사용자가 돌을 놓은 경우
                int h = reqMessage.getInt("h");
                int v = reqMessage.getInt("v");
                
                // 놓은 위치에 이미 돌이 있는지 없는지 && 본인 차례가 맞는지 확인
                if(boards.get(roomNumber).checkOne(h, v) && boards.get(roomNumber).checkTurn((int) userSession.getUserProperties().get("c"))) {
			        boards.get(roomNumber).setStone(h, v, (int) userSession.getUserProperties().get("c"));
			        Map<String, Integer> stoneLocation = new HashMap<>();
			        stoneLocation.put("h", h);
			        stoneLocation.put("v", v);
			        
			        // 놓은 위치 값을 같은 방내 다른 사람에게 전송
                    JSONObject resMessage = new JSONObject();
                    resMessage.put("sign", "game");
                    resMessage.put("h", reqMessage.getInt("h"));
                    resMessage.put("v", reqMessage.getInt("v"));
                    resMessage.put("c", userSession.getUserProperties().get("c"));
                    sc.getUserSockets().get((String) roomNumber).stream().forEach(x -> {
                        try{
                            x.getBasicRemote().sendText(resMessage.toString());
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    Boolean checkThree = boards.get(roomNumber).doubleThree((int) userSession.getUserProperties().get("c"), stoneLocation);
                    System.out.println("33 판정: " + checkThree);

                    Boolean winLose = boards.get(roomNumber).win((int) userSession.getUserProperties().get("c"), stoneLocation);
                    System.out.println("오목 판정: " + winLose);
                    
                    System.out.println("boards.get(roomNumber).getMode(): " + boards.get(roomNumber).getMode());
                    if("33".equals(boards.get(roomNumber).getMode())) {
                        System.out.println("33 모드임");
                        if(checkThree) {
                            System.out.println("33나옴!!!!!");
                            boards.put(roomNumber, new BoardVO());
                            sc.gameEnd(roomNumber, userSession, false);
                        } else {
                            System.out.println("33 안나옴");
                            if(winLose) {
                                System.out.println("33 아니고 이김");
                                boards.put(roomNumber, new BoardVO());
                                sc.gameEnd(roomNumber, userSession, true);
                            }
                        }
                    } else {
                        if(winLose) {
                            boards.put(roomNumber, new BoardVO());
                            sc.gameEnd(roomNumber, userSession, true);
                        }
                    }
                        
			        
			    }
			} else if("exit".equals(reqMessage.get("sign"))) {
			    sc.exitRoom(roomNumber, userSession);
			}
		}
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		
	    // 방에서 나가는 경우, 해당 인원의 세션 제거(유저가 아무도 없게 되면 방도 삭제)
	    sc.closeSocket((String) userSession.getUserProperties().get("roomNumber"), userSession);
		 
		// 현재 소켓 접속자 현황 확인용 로그
		System.out.println("[서버] 유저 나감");
		sc.printRoomAndSockets();
	}
	
	@OnError
	public void handleError(Throwable t) {}
}