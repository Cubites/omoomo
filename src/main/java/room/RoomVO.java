package room;

import lombok.Data;

@Data
public class RoomVO {

    private String num;
    private String name;
    private String mode;
    private String owner;
    //private Set<Session> users;
    
    public String genRoomNumber() {
        String seed = name + mode;
    	int hashCode = name.hashCode();
    	String roomNumber = Integer.toHexString(hashCode);
    	
    	return roomNumber;
    }
}
