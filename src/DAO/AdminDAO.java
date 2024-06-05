package DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import Utils.DatabaseConnection;

public class AdminDAO {

    public void insertAdmin(String name, String email, String senha) throws SQLException {
        String sql = "{CALL insert_admin(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            if (conn != null) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, senha);

                stmt.executeUpdate();
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }
        }
    }
}

