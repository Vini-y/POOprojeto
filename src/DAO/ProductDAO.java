package DAO;

import Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class ProductDAO {

    public void insertProduct(String description, int quantity, float price, int idSupplier) throws SQLException {
        String sql = "{CALL insert_product(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, description);
            stmt.setInt(2, quantity);
            stmt.setFloat(3, price);
            stmt.setInt(4, idSupplier);

            stmt.executeUpdate();
        }
    }

    public void updateProduct(int id_product, String description, int quantity, float price, int idSupplier) throws SQLException {
        String sql = "{CALL update_product(?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            if (conn != null) {
                stmt.setInt(1, id_product);
                stmt.setString(2, description);
                stmt.setInt(3, quantity);
                stmt.setFloat(4, price);
                stmt.setInt(5, idSupplier);

                stmt.executeUpdate();
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }

        }
    }
}
