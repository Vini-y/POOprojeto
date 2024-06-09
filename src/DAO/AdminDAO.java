package DAO;

import Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public void insertAdmin(String name, String email, String senha) throws SQLException {
        String sql = "{CALL insert_admin(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, senha);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
