import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thorffin_wears", "root", "");
            if (connection != null) {
                System.out.println("Conexão ao banco de dados estabelecida com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexão ao banco de dados encerrada.");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
