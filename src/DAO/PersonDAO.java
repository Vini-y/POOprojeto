package DAO;

import java.sql.*;

import Models.Person;

public class PersonDAO {
    private Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Person person) throws SQLException {
        String sql = "INSERT INTO Person (last_name, cpf, birth_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getLast_name());
            stmt.setString(2, person.getCpf());
            stmt.setDate(3, (Date) person.getBirth_date());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId_person(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Person findById(int id) throws SQLException {
        String sql = "SELECT id_person, last_name, cpf, birth_date FROM Person WHERE id_person = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Person person = new Person();
                    person.setId_person(rs.getInt("id_person"));
                    person.setLast_name(rs.getString("last_name"));
                    person.setCpf(rs.getString("cpf"));
                    person.setBirth_date(rs.getDate("birth_date"));
                    return person;
                } else {
                    return null; // Pessoa não encontrada
                }
            }
        }
    }

    public void update(Person person) throws SQLException {
        String sql = "UPDATE Person SET last_name = ?, cpf = ?, birth_date = ? WHERE id_person = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getLast_name());
            stmt.setString(2, person.getCpf());
            stmt.setDate(3, (Date) person.getBirth_date());
            stmt.setInt(4, person.getId_person());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Person WHERE id_person = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

