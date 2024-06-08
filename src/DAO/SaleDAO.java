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


    public static void listarVendas() {
        String sql = "SELECT s.id_sale, uc.name AS client_name, us.name AS seller_name, s.sale_date, p.tipo AS payment_type, " +
                "s.total_value AS sale_total, s.parcelas, pr.description AS product_description, " +
                "si.quantity, si.total_value AS item_total " +
                "FROM Sale s " +
                "INNER JOIN User uc ON s.id_client = uc.id_user " +
                "INNER JOIN User us ON s.id_seller = us.id_user " +
                "INNER JOIN Sale_itens si ON s.id_sale = si.sale_id " +
                "INNER JOIN Product pr ON si.product_id = pr.id_product " +
                "INNER JOIN Pay p ON s.payment = p.id_Pay"; // Join com a tabela Pay para obter o tipo de pagamento

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int currentSaleId = 0;
            while (rs.next()) {
                int saleId = rs.getInt("id_sale");
                if (saleId != currentSaleId) {
                    // Nova venda
                    System.out.println("Venda ID: " + saleId);
                    System.out.println("Cliente: " + rs.getString("client_name"));
                    System.out.println("Vendedor: " + rs.getString("seller_name"));
                    System.out.println("Data da Venda: " + rs.getString("sale_date"));
                    System.out.println("Método de Pagamento: " + rs.getString("payment_type"));
                    System.out.println("Valor Total da Venda: " + rs.getFloat("sale_total"));
                    System.out.println("Parcelas: " + rs.getInt("parcelas"));
                    currentSaleId = saleId;
                }

                // Item de venda
                System.out.println("  Descrição do Produto: " + rs.getString("product_description"));
                System.out.println("  Quantidade: " + rs.getInt("quantity"));
                System.out.println("  Valor Total do Item: " + rs.getFloat("item_total"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

