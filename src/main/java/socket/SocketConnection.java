package socket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.json.JSONObject;

public class SocketConnection {
    // String = 방 번호, Set = 방에 있는 유저 목록, Session = 유저, c = 돌 색
    private Map<String, Set<Session>> userSockets = new HashMap<>();
    
    public Map<String, Set<Session>> getUserSockets() {
        return userSockets;
    }
    
    // 방, 소켓 현황 로그 출력
    public void printRoomAndSockets() {
        System.out.println("===== 현재 방 현황 =====");
        System.out.println("| 방 번호 | 현재 유저 수 |");
        for(String room : userSockets.keySet()) {
            System.out.println("---------------------");
            System.out.println("| " + room + " | " + userSockets.get(room).size() + "명 |");
        }
        System.out.println("---------------------");
    }
    
    // 방 입장
    public void enterRoom(String roomNumber, Session session) {
        if(userSockets.get(roomNumber) == null) {
            // 방이 없는 경우 생성 및 입장
            session.getUserProperties().put("c",  -1);
            session.getUserProperties().put("ready", false);
            userSockets.put(roomNumber, new HashSet<Session>(Arrays.asList(session)));
        } else {
            // 방이 있는 경우 입장
            for(Session user : userSockets.get(roomNumber)) {
                session.getUserProperties().put("c", ((int) user.getUserProperties().get("c")) == -1 ? 1 : -1);
                userSockets.get(roomNumber).add(session);
            }
        }
    }
    
    // 방 나감
    public void exitRoom(Session session) {
        userSockets.get(session.getUserProperties().get("roomNumber")).remove(session);
        
        // 방에 아무도 없으면 방도 삭제
        if(userSockets.get(session.getUserProperties().get("roomNumber")).size() == 0) {
            userSockets.remove(session.getUserProperties().get("roomNumber"));
        }
    }
    
    // 초기값 설정
    public void setInit(Session userSession) {
        JSONObject resMessage = new JSONObject();
        resMessage.put("sign", "init");
        resMessage.put("c", userSession.getUserProperties().get("c"));
        resMessage.put("ready", userSession.getUserProperties().get("ready"));
        try{
            userSession.getBasicRemote().sendText(resMessage.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    // 메시지 보내기
    public void sendMessage(String roomNumber, String username, String chatMessage) {
        JSONObject resMessage = new JSONObject();
        resMessage.put("sign", "chat");
        resMessage.put("username", username);
        resMessage.put("message", chatMessage);
        userSockets.get(roomNumber).stream().forEach(x -> {
            try{
                x.getBasicRemote().sendText(resMessage.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    // 오목 판별
    public boolean checkOmok() {
        return true;
    }
}
