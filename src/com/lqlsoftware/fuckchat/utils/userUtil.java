package com.lqlsoftware.fuckchat.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lqlsoftware.fuckchat.dao.DBManager;

public class userUtil {

    public static String login(String username, String password) throws IOException {
        if (username == null || password == null)
            return null;

        String id = null;
        Connection conn = DBManager.getConnection();
		PreparedStatement ps = null;
        String sql = "SELECT * FROM user WHERE status=0 AND login_name=? AND password=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            id = rs.getString("user_name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TokenManager TMR = new TokenManager();
		TokenModel TM = TMR.createToken(id);
        return TM.getAuthentication();
    }

    public static boolean findUserByName(String username) throws IOException {
        if (username == null || username.equals(""))
            return false;
        Connection conn = DBManager.getConnection();
        PreparedStatement ps = null;
        String sql = "SELECT user_name FROM user WHERE login_name=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return false;
            else
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean creatUser(String phone, String username, String password) {
        if (phone.equals("") || username.equals("") || password.equals("")) {
            return false;
        }
        Connection conn = DBManager.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (user_name,login_name,password)VALUES(?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, phone);
            ps.setString(3, password);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}