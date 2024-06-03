package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Models.Address;

public class AddressDAO {
    private Connection connection;

    public AddressDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Address address) throws SQLException {
        String sql = "INSERT INTO Address (city, state, country, address, address_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, address.getCity());
            stmt.setString(2, address.getState());
            stmt.setString(3, address.getCountry());
            stmt.setString(4, address.getAddress());
            stmt.setString(5, address.getAddress_number());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId_address(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Address findById(int id) throws SQLException {
        String sql = "SELECT id_address, city, state, country, address, address_number FROM Address WHERE id_address = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address();
                    address.setId_address(rs.getInt("id_address"));
                    address.setCity(rs.getString("city"));
                    address.setState(rs.getString("state"));
                    address.setCountry(rs.getString("country"));
                    address.setAddress(rs.getString("address"));
                    address.setAddress_number(rs.getString("address_number"));
                    return address;
                } else {
                    return null; // Endereço não encontrado
                }
            }
        }
    }

    public void update(Address address) throws SQLException {
        String sql = "UPDATE Address SET city = ?, state = ?, country = ?, address = ?, address_number = ? WHERE id_address = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address.getCity());
            stmt.setString(2, address.getState());
            stmt.setString(3, address.getCountry());
            stmt.setString(4, address.getAddress());
            stmt.setString(5, address.getAddress_number());
            stmt.setInt(6, address.getId_address());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Address WHERE id_address = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

