package DAO;

import Models.Person;
import Models.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerDAO {
    private Connection connection;

    public SellerDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Seller seller) throws SQLException {
        String sql = "INSERT INTO Seller (id_person) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, seller.getPerson().getId_person());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    seller.setId_seller(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Seller findById(int id) throws SQLException {
        String sql = "SELECT id_seller, id_person FROM Seller WHERE id_seller = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Seller seller = new Seller();
                    seller.setId_seller(rs.getInt("id_seller"));
                    Person person = new Person();
                    person.setId_person(rs.getInt("id_person"));
                    seller.setPerson(person);
                    return seller;
                } else {
                    return null; // Vendedor não encontrado
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Seller WHERE id_seller = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

