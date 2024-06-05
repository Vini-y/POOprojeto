import DAO.ClientDAO;
import Utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnection.connect();

            Scanner scanner = new Scanner(System.in);
            ClientDAO clientDAO = new ClientDAO();

            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Inserir Cliente");
                System.out.println("2. Sair");
                System.out.print("Escolha uma opção: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (option) {
                    case 1:
                        inserirCliente(scanner, clientDAO);
                        break;
                    case 2:
                        System.out.println("Saindo...");
                        DatabaseConnection.disconnect();
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private static void inserirCliente(Scanner scanner, ClientDAO clientDAO) {
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
}
