package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thorffin_wears", "root", "PUC@1234");
            if (connection != null) {
                System.out.println("Conexão ao banco de dados estabelecida com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexão ao banco de dados encerrada.");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
