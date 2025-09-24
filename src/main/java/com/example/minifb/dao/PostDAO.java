package com.example.minifb.dao;

import com.example.minifb.model.Post;
import com.example.minifb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public void save(Post post) throws SQLException {
        String sql = "INSERT INTO posts(user_id, caption, image_url) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getCaption());
            ps.setString(3, post.getImageUrl());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) post.setId(rs.getInt(1));
            }
        }
    }

    public List<Post> findAll() throws SQLException {
        String sql = "SELECT * FROM posts ORDER BY created_at DESC";
        List<Post> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setCaption(rs.getString("caption"));
                p.setImageUrl(rs.getString("image_url"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(p);
            }
        }
        return list;
    }

    public Post findById(int id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Post p = new Post();
                    p.setId(rs.getInt("id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setCaption(rs.getString("caption"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setCreatedAt(rs.getTimestamp("created_at"));
                    return p;
                }
            }
        }
        return null;
    }
}
