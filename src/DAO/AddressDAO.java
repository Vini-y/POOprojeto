package DAO;

import Models.Address;
import Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public void insertAddress(Address address) throws SQLException {
        String sql = "INSERT INTO Address (city, state, country, address, address_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, address.getCity());
            statement.setString(2, address.getState());
            statement.setString(3, address.getCountry());
            statement.setString(4, address.getAddress());
            statement.setString(5, address.getAddress_number());
            statement.executeUpdate();
        }
    }

    public List<Address> getAllAddresses() throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM Address";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id_address = resultSet.getInt("id_address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                String address = resultSet.getString("address");
                String addressNumber = resultSet.getString("address_number");
                addresses.add(new Address(city, state, country, address, addressNumber));
            }
        }
        return addresses;
    }

    public static Address getAddressById(int id) throws SQLException {
        String sql = "SELECT * FROM Address WHERE id_address = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");
                    String country = resultSet.getString("country");
                    String address = resultSet.getString("address");
                    String addressNumber = resultSet.getString("address_number");
                    return new Address(city, state, country, address, addressNumber);
                }
            }
        }
        return null;
    }

    public void updateAddress(Address address) throws SQLException {
        String sql = "UPDATE Address SET city = ?, state = ?, country = ?, address = ?, address_number = ? WHERE id_address = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            System.out.println("SET city = " + address.getCity());
            System.out.println("SET state = " + address.getState());
            System.out.println("SET country = " + address.getCountry());
            System.out.println("SET address = " + address.getAddress());
            System.out.println("SET address_number = " + address.getAddress_number());
            System.out.println("WHERE id_address = " + address.getAddress_id());

            statement.setString(1, address.getCity());
            statement.setString(2, address.getState());
            statement.setString(3, address.getCountry());
            statement.setString(4, address.getAddress());
            statement.setString(5, address.getAddress_number());
            statement.setInt(6, address.getAddress_id());
            statement.executeUpdate();
        }
    }

    public void deleteAddress(int id) throws SQLException {
        String sql = "DELETE FROM Address WHERE id_address = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
