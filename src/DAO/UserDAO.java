package DAO;

import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO User (name, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getSenha());
            statement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id_user = resultSet.getInt("id_user");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                users.add(new User(id_user, name, email, senha));
            }
        }
        return users;
    }

    public static User getUserById(int id) throws SQLException {
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

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, email = ?, senha = ? WHERE id_user = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getSenha());
            statement.setInt(4, user.getId_user());
            statement.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM User WHERE id_user = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
