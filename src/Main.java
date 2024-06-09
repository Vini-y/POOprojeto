import DAO.LoginDAO;
import Models.User;
import Models.UserLoginInfo;
import Utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return; // Sai do programa se não conseguir conectar ao banco de dados
        }

        UserLoginInfo user = null;
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

        while (user.getUserType().equals("Admin") || user.getUserType().equals("Seller")) {
            try {
                Menu menu = new Menu(user.getUserId(), user.getUserType());
                menu.exibirMenu();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Por favor, tente novamente.");
            } catch (Exception e) {
                System.out.println("Erro no menu.");
                e.printStackTrace();
            }
        }
    }

    public static UserLoginInfo login(Scanner sc) throws SQLException {
        LoginDAO loginDAO = new LoginDAO();

        System.out.printf("\nLOGIN\n=====\n");
        System.out.printf("\nE-mail: ");
        String email = sc.nextLine();

        System.out.printf("\nPassword: ");
        String password = sc.nextLine();

        UserLoginInfo userInfo = loginDAO.login(email, password);

        return userInfo;
    }
}
