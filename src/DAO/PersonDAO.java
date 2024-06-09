package DAO;

import Models.Address;
import Models.Person;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDAO {
    private Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertPerson(Person person) throws SQLException {
        String sql = "INSERT INTO Person (id_person, last_name, cpf, birth_date, phone_number, registration_date, address_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, person.getUser().getId_user());
            statement.setString(2, person.getLast_name());
            statement.setString(3, person.getCpf());
            statement.setDate(4, (java.sql.Date) person.getBirth_date());
            statement.setString(5, person.getPhone_number());
            statement.setDate(6, (java.sql.Date) person.getRegistration_date());
            statement.setInt(7, person.getAddress().getAddress_id());
            statement.executeUpdate();
        }
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM Person";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id_person = resultSet.getInt("id_person");
                String last_name = resultSet.getString("last_name");
                String cpf = resultSet.getString("cpf");
                Date birth_date = resultSet.getDate("birth_date");
                String phone_number = resultSet.getString("phone_number");
                Date registration_date = resultSet.getDate("registration_date");
                int address_id = resultSet.getInt("address_id");
                Address address = getAddressById(address_id);
                User user = UserDAO.getUserById(id_person); // Aqui usamos o id_person como id_user
                persons.add(new Person(last_name, cpf, birth_date, phone_number, address, user));
            }
        }
        return persons;
    }

    public Person getPersonById(int id) throws SQLException {
        String sql = "SELECT * FROM Person WHERE id_person = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String last_name = resultSet.getString("last_name");
                    String cpf = resultSet.getString("cpf");
                    Date birth_date = resultSet.getDate("birth_date");
                    String phone_number = resultSet.getString("phone_number");
                    Date registration_date = resultSet.getDate("registration_date");
                    int address_id = resultSet.getInt("address_id");
                    Address address = getAddressById(address_id);
                    User user = UserDAO.getUserById(id); // Aqui usamos o id_person como id_user
                    return new Person(last_name, cpf, birth_date, phone_number, address, user);
                }
            }
        }
        return null;
    }

    public void updatePerson(Person person) throws SQLException {
        String sql = "UPDATE Person SET last_name = ?, cpf = ?, birth_date = ?, phone_number = ?, registration_date = ?, address_id = ? WHERE id_person = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getLast_name());
            statement.setString(2, person.getCpf());
            statement.setDate(3, (java.sql.Date) person.getBirth_date());
            statement.setString(4, person.getPhone_number());
            statement.setDate(5, (java.sql.Date) person.getRegistration_date());
            statement.setInt(6, person.getAddress().getAddress_id());
            statement.setInt(7, person.getUser().getId_user());
            statement.setInt(8, person.getUser().getId_user()); // Aqui usamos o id_person como id_user
            statement.executeUpdate();
        }
    }

    public void deletePerson(int id) throws SQLException {
        String sql = "DELETE FROM Person WHERE id_person = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para obter um endereço pelo ID
    private Address getAddressById(int id) throws SQLException {
        String sql = "SELECT * FROM Address WHERE id_address = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

    // Método auxiliar para obter um usuário pelo ID
    private User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE id_user = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String senha = resultSet.getString("senha");
                    return new User(id, name, email, senha);
                }
            }
        }
        return null;
    }


}
