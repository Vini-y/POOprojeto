package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Models.Client;
import Models.Person;
public class ClientDAO {
    private Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Client client) throws SQLException {
        String sql = "INSERT INTO Client (id_person) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, client.getPerson().getId_person());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId_client(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Client findById(int id) throws SQLException {
        String sql = "SELECT id_client, id_person FROM Client WHERE id_client = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId_client(rs.getInt("id_client"));
                    Person person = new Person();
                    person.setId_person(rs.getInt("id_person"));
                    client.setPerson(person);
                    return client;
                } else {
                    return null; // Cliente não encontrado
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Client WHERE id_client = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

