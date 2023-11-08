package userRank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankDAO {
	private Connection conn;
	private PreparedStatement pst;
	private ResultSet rs;
	public RankDAO() {
		try {
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
			String dbid = "JANG";
			String pwd = "jang";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,dbid,pwd);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void insertMember(RankVO vo) {
	
	}
	
	public synchronized List<RankVO> MemberList(String username){
		System.out.println("dao호출");
		List<RankVO> list = new ArrayList<>();
		String sql ="select * from rank_view where user_name LIKE '%'||?||'%'";
		try {
			
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				RankVO vo = new RankVO();
				vo.setUser_name(rs.getString("user_name"));
				vo.setUser_win(rs.getInt("user_win"));
				vo.setUser_lose(rs.getInt("user_lose"));
				vo.setRanking(rs.getInt("ranking"));
				list.add(vo);
			}
			rs.close();
			pst.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public synchronized void recordWinAndLose(String winner, String loser) {
		String sql1 = "update user_table set user_win = user_win+1 where user_name = ?";
		String sql2 = "update user_table set user_lose = user_lose+1 where user_name = ?";
		String sql3 = "update user_table set user_score = round((user_win*3)*0.6+(user_lose*-1)*0.3+(user_win+user_lose)*0.1)";
		
		try {
			
			
			pst = conn.prepareStatement(sql1);
			pst.setString(1, winner);
			pst.executeUpdate();
			pst = conn.prepareStatement(sql2);
			pst.setString(1, loser);
			pst.executeUpdate();
			
			conn.prepareStatement(sql3).executeUpdate();
			
			pst.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
