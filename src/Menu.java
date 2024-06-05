import DAO.*;
import Utils.DatabaseConnection;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {

    private Scanner scanner;
    private ClientDAO clientDAO;
    private AdminDAO adminDAO;
    private SellerDAO sellerDAO;
    private SupplierDAO supplierDAO;
    private ProductDAO productDAO;
    private String clienteTipo;

    public Menu(String clienteTipo) {
        scanner = new Scanner(System.in);
        clientDAO = new ClientDAO();
        adminDAO = new AdminDAO();
        sellerDAO = new SellerDAO();
        supplierDAO = new SupplierDAO();
        productDAO = new ProductDAO();

        this.clienteTipo = clienteTipo;
    }

    private ArrayList<String> filtrarOpcoes() {
        ArrayList<String> opcoes = new ArrayList<>();

        // Cadastrar
        opcoes.add("Cadastrar Admin"); // Admin
        opcoes.add("Cadastrar Vendedor"); // Admin
        opcoes.add("Cadastrar Cliente"); // Admin e Vendedor
        opcoes.add("Cadastrar Fornecedor"); // Admin e Vendedor
        opcoes.add("Cadastrar Produto"); // Admin e Vendedor

        // Listar
        opcoes.add("Listar Vendedor"); // Admin e Vendedor
        opcoes.add("Listar Cliente"); // Admin e Vendedor
        opcoes.add("Listar Fornecedor"); // Admin e Vendedor
        opcoes.add("Listar Produto"); // Admin e Vendedor

        // Editar
        opcoes.add("Editar Vendedor"); // Admin e Vendedor
        opcoes.add("Editar Cliente"); // Admin e Vendedor
        opcoes.add("Editar Fornecedor"); // Admin e Vendedor
        opcoes.add("Editar Produto"); // Admin e Vendedor

        // Deletar
        opcoes.add("Deletar Vendedor"); // Admin
        opcoes.add("Deletar Cliente"); // Admin
        opcoes.add("Deletar Fornecedor"); // Admin
        opcoes.add("Deletar Produto"); // Admin

        // Venda
        opcoes.add("Registrar Venda");
        opcoes.add("Listar Vendas");

        opcoes.add("Fechamento do Dia");
        opcoes.add("Sair");

        // Filter Menu
        ArrayList<String> filteredOpcoes = new ArrayList<>();

        for (String o : opcoes) {
            // Se tipo de cliente é "seller"
            if (clienteTipo.equals("Seller")) {
                // Filtrar os seguintes valores
                if (!(o.contains("Deletar") || o.contains("Cadastrar Admin") || o.contains("Cadastrar Vendedor"))) {
                    filteredOpcoes.add(o);
                }
            } else if (clienteTipo.equals("Supplier") || clienteTipo.equals("Client")) {
                if ((o.contains("Sair"))) {
                    filteredOpcoes.add(o);
                }
            } else {
                filteredOpcoes.add(o);
            }
        }

        return filteredOpcoes;
    }

    public void exibirMenu() {
        try {
            DatabaseConnection.connect();

            ArrayList<String> opcoes = filtrarOpcoes();
            int option = -1;

            while (option != opcoes.size() - 1) { // The last option should be "Sair"
                System.out.println("Menu:");
                for (int i = 0; i < opcoes.size(); i++) {
                    System.out.println("(" + i + ") " + opcoes.get(i));
                }

                System.out.print("Escolha uma opção: ");
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                if (option < 0 || option >= opcoes.size()) {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }

                String selectedOption = opcoes.get(option);

                switch (selectedOption) {
                    case "Cadastrar Admin":
                        inserirAdmin();
                        break;
                    case "Cadastrar Vendedor":
                        inserirVendedor();
                        break;
                    case "Cadastrar Cliente":
                        inserirCliente();
                        break;
                    case "Cadastrar Fornecedor":
                        // implementar inserirFornecedor()
                        break;
                    case "Cadastrar Produto":
                        // implementar inserirProduto()
                        break;
                    case "Listar Vendedor":
                        // implementar listarVendedor()
                        break;
                    case "Listar Cliente":
                        // implementar listarCliente()
                        break;
                    case "Listar Fornecedor":
                        // implementar listarFornecedor()
                        break;
                    case "Listar Produto":
                        // implementar listarProduto()
                        break;
                    case "Editar Vendedor":
                        // implementar editarVendedor()
                        break;
                    case "Editar Cliente":
                        // implementar editarCliente()
                        break;
                    case "Editar Fornecedor":
                        // implementar editarFornecedor()
                        break;
                    case "Editar Produto":
                        // implementar editarProduto()
                        break;
                    case "Deletar Vendedor":
                        // implementar deletarVendedor()
                        break;
                    case "Deletar Cliente":
                        // implementar deletarCliente()
                        break;
                    case "Deletar Fornecedor":
                        // implementar deletarFornecedor()
                        break;
                    case "Deletar Produto":
                        // implementar deletarProduto()
                        break;
                    case "Registrar Venda":
                        // implementar registrarVenda()
                        break;
                    case "Listar Vendas":
                        // implementar listarVendas()
                        break;
                    case "Fechamento do Dia":
                        // implementar fechamentoDoDia()
                        break;
                    case "Sair":
                        System.out.println("Saindo...");
                        DatabaseConnection.disconnect();
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
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
