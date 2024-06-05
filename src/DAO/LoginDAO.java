package DAO;

import Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public String login(String email, String senha) throws SQLException {
        String sqlUser = "SELECT id_user FROM User WHERE email = ? AND senha = ?";
        String sqlAdmin = "SELECT id_admin FROM admin WHERE id_admin = ?";
        String sqlClient = "SELECT id_client FROM Client WHERE id_client = ?";
        String sqlSeller = "SELECT id_seller FROM Seller WHERE id_seller = ?";
        String sqlSupplier = "SELECT id_supplier FROM Supplier WHERE id_supplier = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Erro ao conectar ao banco de dados.");
                return null;
            }

            // Check User table
            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                stmtUser.setString(1, email);
                stmtUser.setString(2, senha);
                try (ResultSet rsUser = stmtUser.executeQuery()) {
                    if (rsUser.next()) {
                        int userId = rsUser.getInt("id_user");

                        // Check Admin table
                        try (PreparedStatement stmtAdmin = conn.prepareStatement(sqlAdmin)) {
                            stmtAdmin.setInt(1, userId);
                            try (ResultSet rsAdmin = stmtAdmin.executeQuery()) {
                                if (rsAdmin.next()) {
                                    return "Admin";
                                }
                            }
                        }

                        // Check Client table
                        try (PreparedStatement stmtClient = conn.prepareStatement(sqlClient)) {
                            stmtClient.setInt(1, userId);
                            try (ResultSet rsClient = stmtClient.executeQuery()) {
                                if (rsClient.next()) {
                                    return "Client";
                                }
                            }
                        }

                        // Check Seller table
                        try (PreparedStatement stmtSeller = conn.prepareStatement(sqlSeller)) {
                            stmtSeller.setInt(1, userId);
                            try (ResultSet rsSeller = stmtSeller.executeQuery()) {
                                if (rsSeller.next()) {
                                    return "Seller";
                                }
                            }
                        }

                        // Check Supplier table
                        try (PreparedStatement stmtSupplier = conn.prepareStatement(sqlSupplier)) {
                            stmtSupplier.setInt(1, userId);
                            try (ResultSet rsSupplier = stmtSupplier.executeQuery()) {
                                if (rsSupplier.next()) {
                                    return "Supplier";
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found in any table
    }
}