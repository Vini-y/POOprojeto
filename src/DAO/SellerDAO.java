package DAO;

import Models.Address;
import Models.Person;
import Models.Seller;
import Models.User;
import Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {

    public void insertSeller(Seller seller) throws SQLException {
        String sql = "{CALL insert_seller(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            Person person = seller.getPerson();
            stmt.setString(1, person.getUser().getName());
            stmt.setString(2, person.getUser().getEmail());
            stmt.setString(3, person.getUser().getSenha());
            stmt.setString(4, person.getLast_name());
            stmt.setString(5, person.getCpf());
            stmt.setDate(6, new java.sql.Date(person.getBirth_date().getTime()));
            stmt.setString(7, person.getPhone_number());
            stmt.setString(8, person.getAddress().getCity());
            stmt.setString(9, person.getAddress().getState());
            stmt.setString(10, person.getAddress().getCountry());
            stmt.setString(11, person.getAddress().getAddress());
            stmt.setString(12, person.getAddress().getAddress_number());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateSeller(Seller seller) throws SQLException {
        String sql = "{CALL update_seller(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            Person person = seller.getPerson();
            stmt.setInt(1, person.getUser().getId_user());
            stmt.setString(2, person.getUser().getName());
            stmt.setString(3, person.getUser().getEmail());
            stmt.setString(4, person.getUser().getSenha());
            stmt.setString(5, person.getLast_name());
            stmt.setString(6, person.getCpf());
            stmt.setDate(7, new java.sql.Date(person.getBirth_date().getTime()));
            stmt.setString(8, person.getPhone_number());
            stmt.setString(9, person.getAddress().getCity());
            stmt.setString(10, person.getAddress().getState());
            stmt.setString(11, person.getAddress().getCountry());
            stmt.setString(12, person.getAddress().getAddress());
            stmt.setString(13, person.getAddress().getAddress_number());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Seller> getAllSellers() {
        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT s.id_seller, u.name, u.email, u.senha, p.last_name, p.cpf, p.birth_date, p.phone_number, a.city, a.state, a.country, a.address, a.address_number " +
                "FROM Seller s " +
                "JOIN Person p ON s.id_seller = p.id_person " +
                "JOIN Address a ON p.address_id = a.id_address " +
                "JOIN User u ON p.id_person = u.id_user";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id_user = rs.getInt("id_seller");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String last_name = rs.getString("last_name");
                String cpf = rs.getString("cpf");
                Date birth_date = rs.getDate("birth_date");
                String phone_number = rs.getString("phone_number");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String country = rs.getString("country");
                String address = rs.getString("address");
                String address_number = rs.getString("address_number");

                User user = new User(id_user, name, email, senha);
                Address addr = new Address(city, state, country, address, address_number);
                Person person = new Person(last_name, cpf, birth_date, phone_number, addr, user);

                sellers.add(new Seller(person));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellers;
    }

    public void deleteSeller(int id_user) throws SQLException {
        String sql = "{CALL delete_seller(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id_user);
            stmt.executeUpdate();

            System.out.println("Vendedor deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Seller getSellerById(int id_seller) {
        String sql = "SELECT * FROM Seller s " +
                "JOIN Person p ON s.id_seller = p.id_person " +
                "JOIN Address a ON p.address_id = a.id_address " +
                "JOIN User u ON p.id_person = u.id_user " +
                "WHERE s.id_seller = ?";
        Seller seller = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_seller);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String senha = rs.getString("senha");
                    String last_name = rs.getString("last_name");
                    String cpf = rs.getString("cpf");
                    Date birth_date = rs.getDate("birth_date");
                    String phone_number = rs.getString("phone_number");
                    String city = rs.getString("city");
                    String state = rs.getString("state");
                    String country = rs.getString("country");
                    String address = rs.getString("address");
                    String address_number = rs.getString("address_number");
                    int id_user = rs.getInt("id_user");

                    User user = new User(id_user, name, email, senha);
                    Address addr = new Address(city, state, country, address, address_number);
                    Person person = new Person(last_name, cpf, birth_date, phone_number, addr, user);

                    seller = new Seller(person);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seller;
    }

}
