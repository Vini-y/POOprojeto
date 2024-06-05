import DAO.AdminDAO;
import DAO.ClientDAO;
import DAO.SellerDAO;
import Utils.DatabaseConnection;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private ClientDAO clientDAO;
    private AdminDAO adminDAO;
    private SellerDAO sellerDAO;

    public Menu() {
        scanner = new Scanner(System.in);
        clientDAO = new ClientDAO();
        adminDAO = new AdminDAO();
        sellerDAO = new SellerDAO();
    }

    public void exibirMenu() {
        try {
            DatabaseConnection.connect();

            int option = 0;

            while (option != 99) {
                System.out.println("Menu:");
                System.out.println("1. Inserir Cliente");
                System.out.println("2. Inserir Admin");
                System.out.println("3. Inserir Vendedor");
                System.out.println("99. Sair");
                System.out.print("Escolha uma opção: ");
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                if (option == 1) {
                    inserirCliente();
                } else if (option == 2) {
                    inserirAdmin();
                } else if (option == 3) {
                    inserirVendedor();
                } else if (option == 99) {
                    System.out.println("Saindo...");
                    DatabaseConnection.disconnect();
                    scanner.close();
                    System.exit(0);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void inserirCliente() {
        try {
            System.out.print("Nome: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            System.out.print("Sobrenome: ");
            String lastName = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Data de nascimento (YYYY-MM-DD): ");
            Date birthDate = Date.valueOf(scanner.nextLine());

            System.out.print("Número de telefone: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Data de registro (YYYY-MM-DD): ");
            Date registrationDate = Date.valueOf(scanner.nextLine());

            System.out.print("Cidade: ");
            String city = scanner.nextLine();

            System.out.print("Estado: ");
            String state = scanner.nextLine();

            System.out.print("País: ");
            String country = scanner.nextLine();

            System.out.print("Endereço: ");
            String address = scanner.nextLine();

            System.out.print("Número do endereço: ");
            String addressNumber = scanner.nextLine();

            clientDAO.insertClient(name, email, senha, lastName, cpf, birthDate, phoneNumber, registrationDate, city, state, country, address, addressNumber);

            System.out.println("Cliente inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    private void inserirAdmin() {
        try {
            System.out.print("Nome: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            adminDAO.insertAdmin(name, email, senha);

            System.out.println("Admin inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir admin: " + e.getMessage());
        }
    }

    private void inserirVendedor() {
        try {
            System.out.print("Nome: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            System.out.print("Sobrenome: ");
            String lastName = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Data de nascimento (YYYY-MM-DD): ");
            Date birthDate = Date.valueOf(scanner.nextLine());

            System.out.print("Número de telefone: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Data de registro (YYYY-MM-DD): ");
            Date registrationDate = Date.valueOf(scanner.nextLine());

            System.out.print("Cidade: ");
            String city = scanner.nextLine();

            System.out.print("Estado: ");
            String state = scanner.nextLine();

            System.out.print("País: ");
            String country = scanner.nextLine();

            System.out.print("Endereço: ");
            String address = scanner.nextLine();

            System.out.print("Número do endereço: ");
            String addressNumber = scanner.nextLine();

            sellerDAO.insertSeller(name, email, senha, lastName, cpf, birthDate, phoneNumber, registrationDate, city, state, country, address, addressNumber);

            System.out.println("Vendedor inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir vendedor: " + e.getMessage());
        }
    }
}
