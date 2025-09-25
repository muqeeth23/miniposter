package com.example.minifb.dao;

import com.example.minifb.model.Comment;
import com.example.minifb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public void save(Comment cmt) throws SQLException {
        String sql = "INSERT INTO comments(post_id, user_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cmt.getPostId());
            ps.setInt(2, cmt.getUserId());
            ps.setString(3, cmt.getContent());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) cmt.setId(rs.getInt(1));
            }
        }
    }

    public List<Comment> findByPostId(int postId) throws SQLException {
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY created_at ASC";
        List<Comment> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment cm = new Comment();
                    cm.setId(rs.getInt("id"));
                    cm.setPostId(rs.getInt("post_id"));
                    cm.setUserId(rs.getInt("user_id"));
                    cm.setContent(rs.getString("content"));
                    cm.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(cm);
                }
            }
        }
        return list;
    }
}
