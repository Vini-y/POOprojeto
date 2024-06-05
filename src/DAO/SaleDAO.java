package DAO;

import Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SaleDAO {

    public void insertSale(int idClient, int idSeller, Timestamp saleDate, int payment, float totalValue, int parcelas) throws SQLException {
        String sql = "{CALL insert_sale(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idClient);
            stmt.setInt(2, idSeller);
            stmt.setTimestamp(3, saleDate);
            stmt.setInt(4, payment);
            stmt.setFloat(5, totalValue);
            stmt.setInt(6, parcelas);

            stmt.executeUpdate();
        }
    }
}

