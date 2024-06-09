package DAO;

import Models.Product;
import Models.SaleItem;
import Utils.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SaleItemDAO {

    public void insertSaleItem(SaleItem saleItem) throws SQLException {
        String sql = "{CALL insert_sale_item(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, saleItem.getSale().getId_sale());
            stmt.setInt(2, saleItem.getProduct().getId_product());
            stmt.setInt(3, saleItem.getQuantity());

            stmt.executeUpdate();
        }
    }
}
