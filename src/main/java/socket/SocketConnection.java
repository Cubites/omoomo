package socket;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.Session;

public class SocketConnection {
    private Map<String, Set<Session>> userSockets = new HashMap<>();
    
    public Map<String, Set<Session>> getUserSocketsMap() {
        return userSockets;
    }
    
    // 방 입장
    public void enterRoom(String roomNumber, Session session) {
        if(userSockets.get(roomNumber) == null) {
            // 방이 없는 경우 생성 및 입장
            session.getUserProperties().put("stonecolor",  "black");
            userSockets.put(roomNumber, new HashSet<Session>(Arrays.asList(session)));
        } else {
            // 방이 있는 경우 입장
            session.getUserProperties().put("stonecolor", "white");
            userSockets.get(roomNumber).add(session);
        }
    }
    
    // 메시지 보내기
    public void sendMessage(String roomNumber, String username, String message) {
        userSockets.get(roomNumber).stream().forEach(x -> {
            try{
                x.getBasicRemote().sendText(buildJsonData(username, message));
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private String buildJsonData(String username, String message) {
        // 채팅 메세지 생성 메서드
        System.out.println("[서버] 메세지 생성");
        JsonObject jsonObject = Json.createObjectBuilder().add("message", username + ": " + message).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
        return stringWriter.toString();
    }
}
