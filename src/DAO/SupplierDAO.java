package DAO;

import Models.Address;
import Models.Supplier;
import Models.User;
import Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DAO.UserDAO.getUserById;

public class SupplierDAO {

    public void insertSupplier(Supplier supplier) throws SQLException {
        String sql = "{CALL insert_supplier(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            User user = supplier.getUser();
            Address address = supplier.getAddress();

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, supplier.getCnpj());
            stmt.setString(5, address.getCity());
            stmt.setString(6, address.getState());
            stmt.setString(7, address.getCountry());
            stmt.setString(8, address.getAddress());
            stmt.setString(9, address.getAddress_number());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateSupplier(Supplier supplier) throws SQLException {
        String sql = "{CALL update_supplier(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            User user = supplier.getUser();
            Address address = supplier.getAddress();

            stmt.setInt(1, supplier.getUser().getId_user());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getSenha());
            stmt.setString(5, supplier.getCnpj());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getState());
            stmt.setString(8, address.getCountry());
            stmt.setString(9, address.getAddress());
            stmt.setString(10, address.getAddress_number());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT s.id_supplier, u.id_user, u.name, u.email, u.senha, s.cnpj, s.registration_date, " +
                "a.city, a.state, a.country, a.address, a.address_number " +
                "FROM Supplier s " +
                "INNER JOIN Address a ON s.address_id = a.id_address " +
                "INNER JOIN User u ON s.id_supplier = u.id_user";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id_supplier = rs.getInt("id_supplier");
                int id_user = rs.getInt("id_user");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String cnpj = rs.getString("cnpj");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String country = rs.getString("country");
                String address = rs.getString("address");
                String addressNumber = rs.getString("address_number");

                Address addressObj = new Address(city, state, country, address, addressNumber);
                User user = new User(id_user, name, email, senha);
                Supplier supplier = new Supplier(name, cnpj, addressObj, user);

                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }

    public void deleteSupplier(int supplierId) throws SQLException {
        String sql = "{CALL delete_supplier(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, supplierId);
            stmt.executeUpdate();

            System.out.println("Fornecedor deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM Supplier WHERE id_supplier = ?";
        Supplier supplier = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Recuperar os dados do fornecedor do resultado da consulta
                int supplierId = rs.getInt("id_supplier");
                String name = rs.getString("name");
                String cnpj = rs.getString("cnpj");

                // Recuperar o endereço do fornecedor
                Address address = AddressDAO.getAddressById(rs.getInt("address_id"));

                // Recuperar o usuário do fornecedor
                User user = getUserById(rs.getInt("user_id"));

                // Criar o objeto Supplier com os dados obtidos
                supplier = new Supplier( name, cnpj, address, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

}
