package DAO;

import Utils.DatabaseConnection;

import java.sql.*;

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

    public static void getSale_Itens(int saleId) {
        String query = "SELECT si.product_id, p.description AS product_name, si.quantity " +
                "FROM Sale s " +
                "JOIN Sale_itens si ON s.id_sale = si.sale_id " +
                "JOIN Product p ON si.product_id = p.id_product " +
                "WHERE s.id_sale = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, saleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");

                    System.out.println("Product ID: " + productId);
                    System.out.println("Product Name: " + productName);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("---");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
