import DAO.LoginDAO;
import Utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Tentar conectar ao banco de dados uma vez
        try {
            DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return; // Sai do programa se não conseguir conectar ao banco de dados
        }

        String user = null;
        Scanner sc = new Scanner(System.in);

        while (user == null) {
            try {
                user = login(sc);
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados durante o login: " + e.getMessage());
                return; // Sai do programa se não conseguir verificar o login
            } catch (Exception e) {
                System.out.println("Erro durante o login: " + e.getMessage());
                e.printStackTrace();
            }

            if (user == null) {
                System.out.println("Usuário não cadastrado. Por favor, tente novamente.");
            }
        }

        while (user == "Admin" || user == "Seller") {
            try {
                Menu menu = new Menu(user);
                menu.exibirMenu();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Por favor, tente novamente.");
            } catch (Exception e) {
                System.out.println("Erro no menu.");
                e.printStackTrace();
            }
        }
    }

    public static String login(Scanner sc) throws SQLException {
        LoginDAO loginDAO = new LoginDAO();

        System.out.print("\nLOGIN\n=====\n");
        System.out.print("\nE-mail: ");
        String email = sc.nextLine();

        System.out.print("\nPassword: ");
        String password = sc.nextLine();

        String userType = loginDAO.login(email, password);

        return userType;
    }
}
