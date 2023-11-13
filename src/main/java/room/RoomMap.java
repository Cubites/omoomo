package room;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomMap {
    // Room정보를 저장하기 위한 map을 wrap하는 싱글톤 RoomMap객체  기능은 map과 동일함
    // ** 자바에서 컬렉션을 사용할 때 이와 같이 경계클래스(RoomMap)같은 것을 두고, wrapping을 종종 한다
    // 그 이유는 1. 기본 Map컬렉션이 제공하는 기능이 너무 많음으로, 필요한 기능만 정의해두고 사용하면, 편하다.
    //        2. 컬렉션 객체를 불변 객체로 사용하기 위해서 필요하다. (방어적 복사)
   
    private Map<String, RoomVO> map;
    
    //객체 생성없이 클래스 레벨에서 변수를 지정하고 사용해야함 static으로 선언 
    private static RoomMap roomMap;
    
    //객체 생성 못하도록 public으로 만들기
    private RoomMap() {
        map = new HashMap<>();
    }

    //미리 생성된 객체의 인스턴스를 얻기 위해서 사용하는 메서드 
    public static RoomMap getRoomMap() {
        if (roomMap == null) { //생성된 싱글톤 객체가 없다면,
            roomMap = new RoomMap(); //싱글톤 객체 생성
        }
        return roomMap; //roomMap return 
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
    
    //테스트 용도 
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