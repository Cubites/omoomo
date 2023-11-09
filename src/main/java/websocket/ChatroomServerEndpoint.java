package websocket;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import socket.Board;
import socket.SocketConnection;

//@ServerEndpoint("/chatroomServerEndpoint")
@ServerEndpoint(value="/chatroomServerEndpoint", configurator=ChatroomServerconfigurator.class)
public class ChatroomServerEndpoint {
	// 방과 유저 정보
	static SocketConnection sc = new SocketConnection();
	// 게임 판 정보(Map<방번호, 게임판>)
	static Map<String, Board> boards = new HashMap<>();
	
	@OnOpen
	public void handleOpen(EndpointConfig endpointConfig, Session userSession) {
		// 세션에 이름과 방 번호 저장
		userSession.getUserProperties().put("username", endpointConfig.getUserProperties().get("username"));
		userSession.getUserProperties().put("roomNumber", endpointConfig.getUserProperties().get("roomNumber"));
		userSession.getUserProperties().put("ready", false);
		
		String roomNumber = (String) endpointConfig.getUserProperties().get("roomNumber");
		// 세션에 해당 번호의 방이 있는지 확인해서, 있으면 유저만 추가, 없으면 방 추가 후 유저 추가
		sc.enterRoom(roomNumber, (Session) userSession);
		boards.put(roomNumber, new Board());
		// 현재 소켓 접속자 현황 확인용 로그
		System.out.println("[서버] 유저 입장");
		sc.printRoomAndSockets();
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession) throws Exception {
		System.out.println("[서버] 메세지 받음");
		String username = (String) userSession.getUserProperties().get("username");
		String roomNumber = (String) userSession.getUserProperties().get("roomNumber");
		if(username != null) {
			JSONObject reqMessage = new JSONObject(message);
			System.out.println("요청 유형: " + reqMessage.get("sign"));
			if("init".equals(reqMessage.get("sign"))) {
			    // 초기 설정 - 방에 입장했을 때
			    sc.setInit(userSession);
			} else if("ready".equals(reqMessage.getString("sign"))) {
                // 사용자가 준비된 경우 - 소켓에 저장 후, 준비한 사용하에게 해당 값 전달
			    sc.gameReady(roomNumber, username, reqMessage.getBoolean("value"));
			    sc.gameStart(roomNumber);
            } else if("run".equals(reqMessage.getString("sign"))) {
                // 게임 중 방을 나가는 경우
                sc.runGame(roomNumber, username);
            } else if("chat".equals(reqMessage.getString("sign"))) {
                // 받은 메시지가 채팅 메시지 인 경우 - 채팅 메시지를 같은 방내 다른 사용자에게 전송
                sc.sendMessage(roomNumber, username, reqMessage.getString("m"));
            } else if("game".equals(reqMessage.getString("sign"))){
                int h = reqMessage.getInt("h");
                int v = reqMessage.getInt("v");
                
                // 놓은 위치에 이미 돌이 있는지 없는지 & 본인 차례가 맞는지 확인
                if(boards.get(roomNumber).checkOne(h, v) && boards.get(roomNumber).checkTurn((int) userSession.getUserProperties().get("c"))) {
			        boards.get(roomNumber).setStone(h, v, (int) userSession.getUserProperties().get("c"));
			        Map<String, Integer> stoneLocation = new HashMap<>();
			        stoneLocation.put("h", h);
			        stoneLocation.put("v", v);
			        Boolean winLose = boards.get(roomNumber).win((int) userSession.getUserProperties().get("c"), stoneLocation);
			        System.out.println("오목 판정: " + winLose);
			        
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
			        if(winLose) {
			            boards.put(roomNumber, new Board());
			            sc.gameEnd(roomNumber, userSession);
			        }
//			        Boolean checkFours = boards.get(roomNumber).fourLogic((int) userSession.getUserProperties().get("c"), stoneLocation);
//			        System.out.println("사목 판정: " + checkFours);
			        
			        
			    }
			    
				// 받은 메시지가 돌을 놓은 좌표인 경우 - 놓은 좌포를 같은 방내 다른 사용자에게 전송
				
				// 전체(or 놓은 위치 주변) 오목판 정보를 받아 오목 판별
				
				/******** 여기에 오목 판별 로직 추가 ********/
			    // stone color, stone location, stone area
			    // stone xy: {x, y} = {horizon, vertical}
			    /* 
			     *  List<ArrayList<HashMap<String, Integer> stone area = 
			        {
			            {{h: 0, v: 4}, {h: 0, v: 4}, {h: 0, v: 4}, ...},
                        {{h: 0, v: 4}, {h: 0, v: 4}, {h: 0, v: 4}, ...},
			            ...
		            }
		            HashMap<String, Integer> stone location = {h: 0, v: 4}
		            int stone_color =  -1 ? 1 // black = -1 , white = 1
			     */
			    // logic.win((Map) stone_color, (Map) stone_location, (List<ArrayList<HashMap<String, Integer>>) stone_area);
				
			    /*
			     1. stone 5 ?
			     2. Need stone 33 ?
			         2-1. Yes! => check 33
			         2-2. No! => pass
			     3. stone 44 ?
			         3-1. Yes! => check 44
			         3-2. No! => pass
			     */

				
			}
		}
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		// 방에서 나가는 경우, 해당 인원의 세션 제거(유저가 아무도 없게 되면 방도 삭제)
	    sc.exitRoom(userSession);
		
		// 현재 소켓 접속자 현황 확인용 로그
		System.out.println("[서버] 유저 나감");
		sc.printRoomAndSockets();
	}
	
	@OnError
	public void handleError(Throwable t) {}
}