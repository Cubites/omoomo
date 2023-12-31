package socket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.json.JSONObject;

import room.RoomMap;

public class SocketConnection {
    // String = 방 번호, Set = 방에 있는 유저 목록, Session = 유저, c = 돌 색
    private Map<String, Set<Session>> userSockets = new HashMap<>();
    private int ready = 0; // 시작을 위한 준비 여부 카운트
    
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
            RoomMap roomMap = RoomMap.getRoomMap();

            roomMap.get(roomNumber).addUser((String) session.getUserProperties().get("username"));
            
            session.getUserProperties().put("c",  -1);
            session.getUserProperties().put("ready", false);
            userSockets.put(roomNumber, new HashSet<Session>(Arrays.asList(session)));
        } else if(userSockets.get(roomNumber).size() >= 2) {
            JSONObject json = new JSONObject();
            json.put("sign", "noSeat");
            json.put("username", session.getUserProperties().get("username"));
            json.put("roomNumber", roomNumber);
            try {
                session.getBasicRemote().sendText(json.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            // 방이 있는 경우 입장
            for(Session user : userSockets.get(roomNumber)) {
                session.getUserProperties().put("c", ((int) user.getUserProperties().get("c")) == -1 ? 1 : -1);
                userSockets.get(roomNumber).add(session);
                JSONObject json = new JSONObject();
                try {
                    // 먼저 들어와있던 유저에게 입장한 유저의 닉네임을 보냄
                    json.put("sign", "match");
                    json.put("matchUserNickname", session.getUserProperties().get("username"));
                    System.out.println("들어온 유저: " + session.getUserProperties().get("username"));
                    System.out.println("들어와 있던 유저: " + user.getUserProperties().get("username"));
                    user.getBasicRemote().sendText(json.toString());
                    
                    // 입장한 유저의 닉네임을 먼저 들어와있던 유저에게 보냄
                    json.put("matchUserNickname", user.getUserProperties().get("username"));
                    session.getBasicRemote().sendText(json.toString());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                
            }
        }
    }

    // 초기값 설정
    public void setInit(Session userSession) {
        JSONObject resMessage = new JSONObject();
        resMessage.put("sign", "init");
        resMessage.put("c", userSession.getUserProperties().get("c"));
        resMessage.put("ready", false);
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
    
    // 게임 준비
    public void gameReady(String roomNumber, String username, Boolean ready) {
        userSockets.get(roomNumber).stream().forEach(user -> {
            if(username.equals((String) user.getUserProperties().get("username"))) {
                user.getUserProperties().put("ready", ready);
                try{
                    JSONObject json = new JSONObject();
                    json.put("sign", "ready");
                    json.put("value", ready);
                    user.getBasicRemote().sendText(json.toString());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    // 게임 시작 여부(둘 다 준비했는지)
    public void gameStart(String roomNumber) {
        
        userSockets.get(roomNumber).stream().forEach(user -> {
            if((Boolean) user.getUserProperties().get("ready")) {
                ready++;
            }
        });
        if(ready == 2) {
            JSONObject json = new JSONObject();
            json.put("sign", "start");
            json.put("value", true);
            userSockets.get(roomNumber).stream().forEach(user -> {
                try{
                    user.getBasicRemote().sendText(json.toString());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });
        }
        ready = 0;
    }
    
    // 승패 여부 전송
    public void gameEnd(String roomNumber, Session session, Boolean winLose) {
        JSONObject json = new JSONObject();
        json.put("sign", "gameEnd");
        
        
        userSockets.get(roomNumber).stream().forEach(user -> {
            user.getUserProperties().put("ready", false);
            if(winLose) {
                if(user.getUserProperties().get("username").equals(session.getUserProperties().get("username"))) {
                    user.getUserProperties().put("c", 1);
                    json.put("win", user.getUserProperties().get("username"));
                } else {
                    user.getUserProperties().put("c", -1);
                    json.put("lose", user.getUserProperties().get("username"));
                }
            } else {
                if(user.getUserProperties().get("username").equals(session.getUserProperties().get("username"))) {
                    user.getUserProperties().put("c", -1);
                    json.put("lose", user.getUserProperties().get("username"));
                } else {
                    user.getUserProperties().put("c", 1);
                    json.put("win", user.getUserProperties().get("username"));
                }
            }
            try {
                user.getBasicRemote().sendText(json.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    // 승패 여부 전송 - 게임 중 나간 경우
    public void runGame(String roomNumber, String username) {
        JSONObject json = new JSONObject();
        json.put("sign", "run");
        json.put("ready", false);
        json.put("roomNumber", roomNumber);
        for(Session user : userSockets.get(roomNumber)) {
            if(username.equals(user.getUserProperties().get("username"))) {
                json.put("lose", username);
            } else {
                json.put("win", user.getUserProperties().get("username"));
            }
        }
        userSockets.get(roomNumber).stream().forEach(user -> {
            user.getUserProperties().put("ready", false);
            try{
                user.getBasicRemote().sendText(json.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void exitRoom(String roomNumber, Session session){
        RoomMap roomMap = RoomMap.getRoomMap();
        roomMap.get(roomNumber).removeUser((String) session.getUserProperties().get("username"));
        
        JSONObject json = new JSONObject();
        json.put("sign", "exit");
        json.put("username", session.getUserProperties().get("username"));
        json.put("roomNumber", roomNumber);
        try {
            session.getBasicRemote().sendText(json.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    // 소켓 끊김
    public void closeSocket(String roomNumber, Session session) {
        RoomMap roomMap = RoomMap.getRoomMap();
        
        roomMap.get(roomNumber).removeUser((String) session.getUserProperties().get("username"));
        if(roomMap.get(roomNumber).getUserlist().size() == 0) {
            roomMap.remove(roomNumber);
        }
        
        userSockets.get(session.getUserProperties().get("roomNumber")).remove(session);
        
        // 방에 아무도 없으면 방도 삭제
        if(userSockets.get(session.getUserProperties().get("roomNumber")).size() == 0) {
            userSockets.remove(session.getUserProperties().get("roomNumber"));
        }
    }
}
