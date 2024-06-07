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

    public void getSale_Itens(int saleId) {
        String query = "SELECT s.id_sale, s.id_client, s.id_seller, s.sale_date, s.payment, s.total_value, s.parcelas, "
                + "si.id_sale_itens, si.product_id, si.quantity "
                + "FROM Sale s "
                + "JOIN Sale_itens si ON s.id_sale = si.sale_id "
                + "WHERE s.id_sale = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, saleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idSale = resultSet.getInt("id_sale");
                    int idClient = resultSet.getInt("id_client");
                    int idSeller = resultSet.getInt("id_seller");
                    Timestamp saleDate = resultSet.getTimestamp("sale_date");
                    int payment = resultSet.getInt("payment");
                    float totalValue = resultSet.getFloat("total_value");
                    int parcelas = resultSet.getInt("parcelas");
                    int idSaleItens = resultSet.getInt("id_sale_itens");
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");

                    System.out.println("Sale ID: " + idSale);
                    System.out.println("Client ID: " + idClient);
                    System.out.println("Seller ID: " + idSeller);
                    System.out.println("Sale Date: " + saleDate);
                    System.out.println("Payment: " + payment);
                    System.out.println("Total Value: " + totalValue);
                    System.out.println("Parcelas: " + parcelas);
                    System.out.println("Item ID: " + idSaleItens);
                    System.out.println("Product ID: " + productId);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("---");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
