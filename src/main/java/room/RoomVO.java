package room;

import java.util.List;
import java.util.Vector;

import lombok.Data;
import user.UserVO;

@Data
public class RoomVO {

    private String num;
    private String name;
    private String mode;
    private String owner;
    private int peopleNum;
    // private Set<Session> users;
     
    // Room에 UserVO리스트를 저장해서 유저 list를 둠 
    private List<UserVO> userlist;

    public RoomVO() {
        this.userlist = new Vector<>();
    }

    public String genRoomNumber() {
        String seed = name + mode;
        int hashCode = name.hashCode();
        String roomNumber = Integer.toHexString(hashCode);

        return roomNumber;
    }
    
    public void addUser(String username) {
        UserVO userVO = new UserVO();
        userVO.setUser_name(username);
        userlist.add(userVO);
    }
    
    public void removeUser(String username) {
        for(int i=0; i<userlist.size(); i++) {
            if(username.equals(userlist.get(i).getUser_name())) {
                userlist.remove(i);
            }
        }
    }
}