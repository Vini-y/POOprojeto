package DAO;

import Models.Address;
import Models.User;
import Utils.DatabaseConnection;
import Models.Product;
import Models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insertProduct(Product product) {
        String sql = "{CALL insert_product(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, product.getDescription());
            stmt.setInt(2, product.getQuantity());
            stmt.setFloat(3, product.getPrice());
            stmt.setInt(4, product.getSupplier().getUser().getId_user());

            stmt.execute();

            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "{CALL update_product(?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            if (conn != null) {
                stmt.setInt(1, product.getId_product());
                stmt.setString(2, product.getDescription());
                stmt.setInt(3, product.getQuantity());
                stmt.setFloat(4, product.getPrice());
                stmt.setInt(5, product.getSupplier().getUser().getId_user());

                stmt.executeUpdate();
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }

        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT pr.id_product, pr.description, pr.quantity, pr.price, " +
                "su.name AS supplier_name, su.cnpj, " +
                "a.city, a.state, a.country, a.address, a.address_number, " +
                "u.id_user, u.name AS user_name, u.email, u.senha " +
                "FROM Product pr " +
                "INNER JOIN Supplier su ON pr.id_supplier = su.id_supplier " +
                "INNER JOIN Address a ON su.address_id = a.id_address " +
                "INNER JOIN User u ON su.id_supplier = u.id_user";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_product");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                float price = rs.getFloat("price");
                String supplierName = rs.getString("supplier_name");
                String cnpj = rs.getString("cnpj");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String country = rs.getString("country");
                String address = rs.getString("address");
                String addressNumber = rs.getString("address_number");
                int userId = rs.getInt("id_user");
                String userName = rs.getString("user_name");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Supplier supplier = new Supplier(supplierName, cnpj,
                        new Address(city, state, country, address, addressNumber),
                        new User( userName, email, senha));

                Product product = new Product( description, quantity, price, supplier);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public static void deleteProduct(int productId) {
        String sql = "{CALL delete_product(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, productId);
            stmt.execute();

            System.out.println("Produto deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
