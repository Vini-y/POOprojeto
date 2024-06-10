package DAO;

import Utils.DatabaseConnection;
import Models.Sale;
import Models.Payment;
import Models.Product;

import java.sql.*;

public class SaleDAO {

    public int insertSale(Sale sale) {
        String sql = "{CALL insert_sale(?, ?, ?, ?, ?, ?, ?)}";
        int lastInsertId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, sale.getClient().getPerson().getUser().getId_user());
            stmt.setInt(2, sale.getSeller().getPerson().getUser().getId_user());
            stmt.setTimestamp(3, sale.getSale_date());
            stmt.setInt(4, sale.getPayment().getId_pay());
            stmt.setFloat(5, sale.getTotal_value());
            stmt.setInt(6, sale.getParcelas());

            // Register the out parameter to capture the generated key
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);

            stmt.executeUpdate();

            // Retrieve the last insert ID from the out parameter
            lastInsertId = stmt.getInt(7);

            System.out.println("Venda inserida com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastInsertId;
    }

    public static void getSaleItems(int saleId) {
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
                "LEFT JOIN Sale_itens si ON s.id_sale = si.sale_id " +
                "LEFT JOIN Product pr ON si.product_id = pr.id_product " +
                "LEFT JOIN Pay p ON s.payment = p.id_Pay"; // Join com a tabela Pay para obter o tipo de pagamento

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
                System.out.println("\n  Descrição do Produto: " + rs.getString("product_description"));
                System.out.println("  Quantidade: " + rs.getInt("quantity"));
                System.out.println("  Valor Total do Item: " + rs.getFloat("item_total"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fechamentoDoDia() {
        String sql = "{CALL fechamento_do_dia()}";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareCall(sql)) {

            // Executar a procedure
            boolean hasResults = stmt.execute();

            // Obter as vendas do dia atual
            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    System.out.println("Vendas do dia atual:");
                    while (rs.next()) {
                        int saleId = rs.getInt("id_sale");
                        int clientId = rs.getInt("id_client");
                        int sellerId = rs.getInt("id_seller");
                        String saleDate = rs.getString("sale_date");
                        int payment = rs.getInt("payment");
                        float totalValue = rs.getFloat("total_value");
                        int parcelas = rs.getInt("parcelas");

                        System.out.println("Venda ID: " + saleId);
                        System.out.println("Cliente ID: " + clientId);
                        System.out.println("Vendedor ID: " + sellerId);
                        System.out.println("Data da Venda: " + saleDate);
                        System.out.println("Pagamento: " + payment);
                        System.out.println("Valor Total: " + totalValue);
                        System.out.println("Parcelas: " + parcelas);
                        System.out.println("---");
                    }
                }
            }

            // Mover para o próximo resultado que é a soma total dos valores das vendas do dia
            if (stmt.getMoreResults()) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        float totalSalesValueDebitOfTheDay = rs.getFloat("total_sales_value_debit_of_the_day");
                        float totalSalesValueMoneyOfTheDay = rs.getFloat("total_sales_value_money_of_the_day");
                        System.out.println("Valor total das vendas em débito do dia: " + totalSalesValueDebitOfTheDay);
                        System.out.println("Valor total das vendas em dinheiro do dia: " + totalSalesValueMoneyOfTheDay);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao executar o fechamento do dia: " + e.getMessage());
        }
    }
}

