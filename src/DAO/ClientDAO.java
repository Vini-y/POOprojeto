package DAO;

import Models.*;
import Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public void insertClient(Client client) throws SQLException {
        String sql = "{CALL insert_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            Person person = client.getPerson();
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

    public void updateClient(Client client) throws SQLException {
        String sql = "{CALL update_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            Person person = client.getPerson();
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
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT u.id_user, u.name, u.email, u.senha, p.last_name, p.cpf, p.birth_date, p.registration_date, p.phone_number, a.city, a.state, a.country, a.address, a.address_number " +
                "FROM Client c " +
                "JOIN Person p ON c.id_client = p.id_person " +
                "JOIN Address a ON p.address_id = a.id_address " +
                "JOIN User u ON p.id_person = u.id_user";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id_user = rs.getInt("id_user");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String last_name = rs.getString("last_name");
                String cpf = rs.getString("cpf");
                Date birth_date = rs.getDate("birth_date");
                // Include the registration date from the result set
                Date registration_date = rs.getDate("registration_date");
                String phone_number = rs.getString("phone_number");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String country = rs.getString("country");
                String address = rs.getString("address");
                String address_number = rs.getString("address_number");

                User user = new User(id_user, name, email, senha);
                Address addr = new Address(city, state, country, address, address_number);
                // Now, include the registration date in the Person object
                Person person = new Person(last_name, cpf, birth_date, registration_date, phone_number, addr, user);

                clients.add(new Client(person));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void deleteClient(int id_user) throws SQLException {
        String sql = "{CALL delete_client(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id_user);
            stmt.executeUpdate();

            System.out.println("Cliente deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Client getClientById(int id_client) {
        String sql = "SELECT u.id_user, u.name, u.email, u.senha, p.last_name, p.cpf, p.birth_date, p.registration_date, p.phone_number, a.id_address, a.city, a.state, a.country, a.address, a.address_number " +
                "FROM Client c " +
                "JOIN Person p ON c.id_client = p.id_person " +
                "JOIN Address a ON p.address_id = a.id_address " +
                "JOIN User u ON p.id_person = u.id_user " +
                "WHERE c.id_client = ?";
        Client client = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_client);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id_user = rs.getInt("id_user");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String senha = rs.getString("senha");
                    String last_name = rs.getString("last_name");
                    String cpf = rs.getString("cpf");
                    Date birth_date = rs.getDate("birth_date");
                    Date registration_date = rs.getDate("registration_date");
                    String phone_number = rs.getString("phone_number");
                    int address_id = rs.getInt("id_address");
                    String city = rs.getString("city");
                    String state = rs.getString("state");
                    String country = rs.getString("country");
                    String address = rs.getString("address");
                    String address_number = rs.getString("address_number");

                    User user = new User(id_user, name, email, senha);
                    Address addr = new Address(city, state, country, address, address_number);
                    addr.setAddress_id(address_id);
                    Person person = new Person(last_name, cpf, birth_date, registration_date, phone_number, addr, user);

                    client = new Client(person);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }


}
