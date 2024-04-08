package jeonginho.firstspring.repository;

import jeonginho.firstspring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; // 세이브할 sql

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; // 결과를 받는다
        try {
            conn = getConnection(); //getConnection 을 해서 connection 을 가져온다
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // sql을 넣는다.

            pstmt.setString(1, member.getName()); // parameter index 1번을 하면 ?와 매칭된다. getName()에 값을 넣는다.

            pstmt.executeUpdate(); // 실제 쿼리가 전송된다.
            rs = pstmt.getGeneratedKeys(); // ID 1, 2번을 자동으로 반환해준다.

            if (rs.next()) { // conn, pstmt, rs 값을 릴리즈, 리소스 값을 모두 반환해준다.
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?"; // 쿼리문을 전송하여 sql을 가지고온다.

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try { // connection을 가지고 sql을 전송하고 prepareStatement 세팅한 다음
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery(); //조회는 executeQuery();

            if(rs.next()) { // result set 을 받아와서 만약 값이 있으면 멤버 갱신을 쭉 만든 다음에
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member); // 반환해준다.
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        } }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() { // 데이터 소스 유틸리스를 통해서 커넥션을 획득해야 한다.
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource); // 데이터 소스 유틸리스를 통해서 커넥션을 획득하고 릴리즈 해야한다.
    }
}