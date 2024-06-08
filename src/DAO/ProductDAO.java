package DAO;

import Utils.DatabaseConnection;

import java.sql.*;

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

        public static void selectProduct() {
            String query = "SELECT pr.id_product, pr.description, pr.quantity, pr.price, su.name AS supplier_name " +
                    "FROM Product pr " +
                    "JOIN Supplier su ON pr.id_supplier = su.id_supplier";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int idProduto = rs.getInt("id_product");
                    String descricao = rs.getString("description");
                    int quantidade = rs.getInt("quantity");
                    float preco = rs.getFloat("price");
                    String nomeFornecedor = rs.getString("supplier_name");


                    System.out.println("ID do Produto: " + idProduto);
                    System.out.println("Descrição: " + descricao);
                    System.out.println("Quantidade: " + quantidade);
                    System.out.println("Preço: " + preco);
                    System.out.println("Fornecedor: " + nomeFornecedor);
                    System.out.println("----------------------------------");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao listar produtos: " + e.getMessage());
            }
        }
    }

