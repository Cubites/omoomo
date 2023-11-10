package room;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomMap {
    private Map<String, RoomVO> map;

    private static RoomMap roomMap;

    private RoomMap() {
        map = new HashMap<>();
    }

    public static RoomMap getRoomMap() {
        if (roomMap == null) {
            roomMap = new RoomMap();
        }
        return roomMap;
    }

    public RoomVO put(String key, RoomVO vo) {
        map.put(key, vo);
        return vo;
    }

    public RoomVO remove(String key) {
        RoomVO delRoom = map.get(key);
        map.remove(key);
        return delRoom;
    }

    public Set<String> keySet() {

        return map.keySet();
    }

    public RoomVO get(String key) {
        return map.get(key);
    }

    public String RoomName() {
        System.out.println("여기왔다.");
        String roomName = "";
        Set<String> keys = map.keySet();
        for (String name : keys) {
            roomName = map.get(name).getName();
            System.out.println(roomName);
            break;
        }
        return roomName;
    }
}