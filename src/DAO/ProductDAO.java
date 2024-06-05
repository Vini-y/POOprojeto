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
}
