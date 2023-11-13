package user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserVO {
	private int user_id;
	private String user_name;
	private String user_pw;
	private int user_win;
	private int user_lose;
	private int user_score;
	private int ranking;
	
	// 아이디랑 비번으로 회원가입하기 위해 필요한 생성자
	public UserVO(String user_name, String user_pw) {
		this.user_name = user_name;
		this.user_pw = user_pw;
	}

    public UserVO() {
        // TODO Auto-generated constructor stub
    }
}
