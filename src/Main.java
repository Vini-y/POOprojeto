import Utils.DatabaseConnection;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnection.connect();

            Menu menu = new Menu();
            menu.exibirMenu();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
