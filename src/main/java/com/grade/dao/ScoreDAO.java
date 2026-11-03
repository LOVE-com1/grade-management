package com.grade.dao;

import com.grade.model.Score;
import com.grade.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层：成绩的增删改查与统计
 */
public class ScoreDAO {

    /** 插入一条成绩；若同一学生同一科目已存在则更新分数 */
    public boolean saveOrUpdate(Score s) {
        String check = "SELECT id FROM score WHERE student_id=? AND subject=?";
        String insert = "INSERT INTO score(student_id, student_name, subject, score, input_time) VALUES(?,?,?,?,NOW())";
        String update = "UPDATE score SET score=?, student_name=?, input_time=NOW() WHERE student_id=? AND subject=?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 查重
            ps = conn.prepareStatement(check);
            ps.setString(1, s.getStudentId());
            ps.setString(2, s.getSubject());
            rs = ps.executeQuery();
            if (rs.next()) {
                // 更新
                DBUtil.close(ps, rs);
                ps = conn.prepareStatement(update);
                ps.setDouble(1, s.getScore());
                ps.setString(2, s.getStudentName());
                ps.setString(3, s.getStudentId());
                ps.setString(4, s.getSubject());
            } else {
                // 插入
                DBUtil.close(ps, rs);
                ps = conn.prepareStatement(insert);
                ps.setString(1, s.getStudentId());
                ps.setString(2, s.getStudentName());
                ps.setString(3, s.getSubject());
                ps.setDouble(4, s.getScore());
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }

    /** 查询所有成绩 */
    public List<Score> findAll() {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT * FROM score ORDER BY input_time DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /** 按学号查询（学生端） */
    public List<Score> findByStudentId(String studentId) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT * FROM score WHERE student_id=? ORDER BY subject";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /** 判断学号是否存在 */
    public boolean existsStudent(String studentId) {
        String sql = "SELECT 1 FROM score WHERE student_id=? LIMIT 1";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }

    /** 删除一条 */
    public boolean delete(int id) {
        String sql = "DELETE FROM score WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    /** 班级总平均分（保留两位小数） */
    public double classAverage() {
        String sql = "SELECT AVG(score) FROM score";
        double raw = avg(sql);
        return Math.round(raw * 100.0) / 100.0;
    }

    /** 某科目平均分（保留两位小数） */
    public double subjectAverage(String subject) {
        String sql = "SELECT AVG(score) FROM score WHERE subject=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, subject);
            rs = ps.executeQuery();
            if (rs.next()) {
                double raw = rs.getDouble(1);
                return Math.round(raw * 100.0) / 100.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    /** 班级总及格率（百分比，保留两位小数） */
    public double classPassRate() {
        String sql = "SELECT COUNT(*) total, SUM(CASE WHEN score>=60 THEN 1 ELSE 0 END) pass FROM score";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                int pass = rs.getInt("pass");
                double raw = total == 0 ? 0 : (pass * 100.0 / total);
                return Math.round(raw * 100.0) / 100.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    /** 某科目及格率（百分比，保留两位小数） */
    public double subjectPassRate(String subject) {
        String sql = "SELECT COUNT(*) total, SUM(CASE WHEN score>=60 THEN 1 ELSE 0 END) pass FROM score WHERE subject=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, subject);
            rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                int pass = rs.getInt("pass");
                double raw = total == 0 ? 0 : (pass * 100.0 / total);
                return Math.round(raw * 100.0) / 100.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    /** 获取所有科目列表 */
    public List<String> findAllSubjects() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT subject FROM score ORDER BY subject";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getString("subject"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /** 学生个人平均分（保留两位小数） */
    public double studentAverage(String studentId) {
        String sql = "SELECT AVG(score) FROM score WHERE student_id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                double raw = rs.getDouble(1);
                return Math.round(raw * 100.0) / 100.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    /** 学生个人及格率（百分比，保留两位小数） */
    public double studentPassRate(String studentId) {
        String sql = "SELECT COUNT(*) total, SUM(CASE WHEN score>=60 THEN 1 ELSE 0 END) pass FROM score WHERE student_id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                int pass = rs.getInt("pass");
                double raw = total == 0 ? 0 : (pass * 100.0 / total);
                return Math.round(raw * 100.0) / 100.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    /** 统计概览：返回各科目的平均分与及格率（均已保留两位小数） */
    public List<Map<String, Object>> subjectStat() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT subject," +
                " COUNT(*) cnt," +
                " AVG(score) avg_score," +
                " SUM(CASE WHEN score>=60 THEN 1 ELSE 0 END) pass_cnt" +
                " FROM score GROUP BY subject ORDER BY subject";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("subject", rs.getString("subject"));
                m.put("count", rs.getInt("cnt"));
                double rawAvg = rs.getDouble("avg_score");
                m.put("avg", Math.round(rawAvg * 100.0) / 100.0);
                int cnt = rs.getInt("cnt");
                int pass = rs.getInt("pass_cnt");
                double rawPass = cnt == 0 ? 0 : (pass * 100.0 / cnt);
                m.put("passRate", Math.round(rawPass * 100.0) / 100.0);
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    private Score mapRow(ResultSet rs) throws SQLException {
        Score s = new Score();
        s.setId(rs.getInt("id"));
        s.setStudentId(rs.getString("student_id"));
        s.setStudentName(rs.getString("student_name"));
        s.setSubject(rs.getString("subject"));
        s.setScore(rs.getDouble("score"));
        s.setInputTime(rs.getString("input_time"));
        return s;
    }

    private double avg(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }
}