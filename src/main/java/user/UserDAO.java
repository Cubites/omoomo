package user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public UserDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void addMember(UserVO user) {
		try {
			con = dataFactory.getConnection();
			String name = user.getUser_name();
			String pw = user.getUser_pw();
			String query = "INSERT INTO user_table" + " VALUES(user_seq.nextval, ?, ?, 0, 0, 0)";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, pw);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExisted(UserVO user) {
		boolean result = false;
		String name = user.getUser_name();
		String pw = user.getUser_pw();

		try {
			con = dataFactory.getConnection();
			String query = "select decode(count(*),1,'true','false') as result from user_table";
			query += " where user_name=? and user_pw=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, pw);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // 커서를 첫번째 레코드로 위치시킵니다.
			result = Boolean.parseBoolean(rs.getString("result"));
			System.out.println("result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean overlappedID(String id){
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query = "select decode(count(*),1,'true','false') as result from user_table";
			query += " where user_name=?";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			result =Boolean.parseBoolean(rs.getString("result"));
			pstmt.close();
			System.out.println("-->"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
