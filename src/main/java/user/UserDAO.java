package user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataFactory;
    private ResultSet rs;

    public UserDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
            con = dataFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 회원 가입 메서드
    public void addMember(UserVO user) {
        try {
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
        } finally {
            resourceclose();
        }
    }

    // DB에서 확인하는 메서드
    public boolean isExisted(UserVO user) {
        boolean result = false;
        String name = user.getUser_name();
        String pw = user.getUser_pw();
        try {
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
        } finally {
            resourceclose();
        }
        return result;
    }

    // 아이디 중복 확인 메서드
    public boolean overlappedID(String id) {
        boolean result = false;
        try {
            String query = "select decode(count(*),1,'true','false') as result from user_table";
            query += " where user_name=?";
            System.out.println("prepareStatememt: " + query);
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = Boolean.parseBoolean(rs.getString("result"));
            pstmt.close();
            System.out.println("-->" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resourceclose();
        }
        return result;
    }

    public List<UserVO> MemberList(String username) {
        System.out.println("dao호출");
        List<UserVO> list = new ArrayList<>();
        String sql = "select * from rank_view where user_name LIKE '%'||?||'%'";
        try {

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserVO vo = new UserVO();
                vo.setUser_name(rs.getString("user_name"));
                vo.setUser_win(rs.getInt("user_win"));
                vo.setUser_lose(rs.getInt("user_lose"));
                vo.setRanking(rs.getInt("ranking"));
                list.add(vo);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resourceclose();
        }
        return list;
    }

    public void winRecord(String username) {
        String sql1 = "update user_table set user_win = user_win+1 where user_name = ?";
        String sql2 = "update user_table set user_score = round((user_win*3)*0.6+(user_lose*-1)*0.3+(user_win+user_lose)*0.1)";

        try {
            System.out.println("win username: " + username);

            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, username);
            int a = pstmt.executeUpdate();
            System.out.println("win set result: " + a);
            con.prepareStatement(sql2).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resourceclose();
        }

    }

    public void loseRecord(String username) {
        String sql1 = "update user_table set user_lose = user_lose+1 where user_name = ?";
        String sql2 = "update user_table set user_score = round((user_win*3)*0.6+(user_lose*-1)*0.3+(user_win+user_lose)*0.1)";

        try {
            System.out.println("lose username: " + username);
            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, username);
            int a = pstmt.executeUpdate();
            System.out.println("lose set result: " + a);
            
            con.prepareStatement(sql2).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resourceclose();
        }

    }

    private void resourceclose() {
        try {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            if (con != null)
                con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
