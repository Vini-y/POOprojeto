package DAO;

import Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class SaleItemDAO {

    public void insertSaleItem(int saleId, int productId, int quantity) throws SQLException {
        String sql = "{CALL insert_sale_item(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, saleId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);

            stmt.executeUpdate();
        }
    }
}

