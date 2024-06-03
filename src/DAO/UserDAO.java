package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Models.User;
public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO User (name, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId_user(generatedKeys.getInt(1));
                }
            }
        }
    }

    public User findById(int id) throws SQLException {
        String sql = "SELECT id_user, name, email, senha FROM User WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId_user(rs.getInt("id_user"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setSenha(rs.getString("senha"));
                    return user;
                } else {
                    return null; // Usuário não encontrado
                }
            }
        }
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, email = ?, senha = ? WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setInt(4, user.getId_user());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM User WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

