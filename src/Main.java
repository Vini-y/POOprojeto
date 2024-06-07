import DAO.LoginDAO;
import Utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnection.connect();

            String user = login();
            Menu menu = new Menu(user);
            menu.exibirMenu();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static String login() throws SQLException {
        LoginDAO loginDAO = new LoginDAO();

        Scanner sc = new Scanner(System.in);
        System.out.printf("\nLOGIN\n=====\n");
        System.out.printf("\nE-mail: ");
        String email = sc.nextLine();

        System.out.printf("\nPassword: ");
        String password = sc.nextLine();

        String userType = loginDAO.login(email, password);

        return userType;
    }
}
