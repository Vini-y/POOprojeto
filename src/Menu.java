import DAO.*;
import Models.*;
import Utils.DatabaseConnection;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {

    private Scanner scanner;
    private ClientDAO clientDAO;
    private AdminDAO adminDAO;
    private SellerDAO sellerDAO;
    private SupplierDAO supplierDAO;
    private ProductDAO productDAO;
    private SaleDAO saleDAO;
    private PayDAO payDAO;
    private SaleItemDAO saleItemDAO;
    private String clienteTipo;
    private int clienteId;
    private AddressDAO addressDAO;


    public Menu(int clienteId, String clienteTipo) {
        scanner = new Scanner(System.in);
        clientDAO = new ClientDAO();
        adminDAO = new AdminDAO();
        sellerDAO = new SellerDAO();
        supplierDAO = new SupplierDAO();
        productDAO = new ProductDAO();
        saleDAO = new SaleDAO();
        payDAO = new PayDAO();
        saleItemDAO = new SaleItemDAO();
        addressDAO = new AddressDAO();

        this.clienteTipo = clienteTipo;
        this.clienteId = clienteId;
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
            } else if (clienteTipo.equals("Admin")){
                if (!(o.contains("Registrar Venda"))) {
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
            DatabaseConnection.getConnection();

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
                        inserirFornecedor();
                        break;
                    case "Cadastrar Produto":
                        inserirProduto();
                        break;
                    case "Listar Vendedor":
                        listarVendedores(sellerDAO);
                        break;
                    case "Listar Cliente":
                        listarClientes(clientDAO);
                        break;
                    case "Listar Fornecedor":
                        listarFornecedores(supplierDAO);
                        break;
                    case "Listar Produto":
                        listarProdutos(productDAO);
                        break;
                    case "Editar Vendedor":
                        editarVendedor();
                        break;
                    case "Editar Cliente":
                        editarCliente();
                        break;
                    case "Editar Fornecedor":
                        editarFornecedor();
                        break;
                    case "Editar Produto":
                        editarProduto(supplierDAO);
                        break;
                    case "Deletar Vendedor":
                        listarVendedores(sellerDAO);
                        deletarVendedor(sellerDAO, scanner);
                        break;
                    case "Deletar Cliente":
                        listarClientes(clientDAO);
                        deletarCliente(clientDAO, scanner);
                        break;
                    case "Deletar Fornecedor":
                        listarFornecedores(supplierDAO);
                        deletarFornecedor(supplierDAO, scanner);
                        break;
                    case "Deletar Produto":
                        listarProdutos(productDAO);
                        deletarProduto(productDAO, scanner);
                        break;
                    case "Registrar Venda":
                        registrarVenda(scanner);
                        break;
                    case "Listar Vendas":
                        SaleDAO.listarVendas();
                        break;
                    case "Fechamento do Dia":
                        SaleDAO.fechamentoDoDia();
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

            // Criando o objeto Address
            Address clientAddress = new Address(city, state, country, address, addressNumber);

            // Criando o objeto User
            User user = new User(1, name, email, senha);

            // Criando o objeto Person
            Person person = new Person(lastName, cpf, birthDate, null, phoneNumber, clientAddress, user);

            // Criando o objeto Client
            Client client = new Client(person);

            // Inserindo o cliente
            clientDAO.insertClient(client);

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

            if (!email.contains("@")) {
                System.out.println("Email inválido. Deve conter '@'.");
                return;
            }

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            System.out.print("Sobrenome: ");
            String lastName = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                System.out.println("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
                return;
            }

            Date birthDate = null;
            try {
                System.out.print("Data de nascimento (YYYY-MM-DD): ");
                birthDate = Date.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
                return;
            }

            System.out.print("Número de telefone: ");
            String phoneNumber = scanner.nextLine();

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

            // Criando o objeto Address
            Address sellerAddress = new Address(city, state, country, address, addressNumber);

            // Criando o objeto User
            User user = new User(1, name, email, senha);

            // Criando o objeto Person
            Person person = new Person(lastName, cpf, birthDate, null, phoneNumber, sellerAddress, user);

            // Criando o objeto Seller
            Seller seller = new Seller(person);

            // Inserindo o vendedor
            sellerDAO.insertSeller(seller);

            System.out.println("Vendedor inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir vendedor: " + e.getMessage());
        }
    }



    private void inserirFornecedor() {
        try {
            System.out.print("Nome: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            // Verificação do e-mail
            if (!email.contains("@")) {
                System.out.println("Email inválido. Deve conter '@'.");
                return;
            }

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            System.out.print("CNPJ: ");
            String cnpj = scanner.nextLine();

            // Verificação do CNPJ
            if (cnpj.length() != 14 || !cnpj.matches("\\d+")) {
                System.out.println("CNPJ inválido. Deve conter exatamente 14 dígitos numéricos.");
                return;
            }

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

            // Criando o objeto Address
            Address supplierAddress = new Address(city, state, country, address, addressNumber);

            // Criando o objeto User
            User user = new User(1 ,name, email, senha);

            // Criando o objeto Supplier
            Supplier supplier = new Supplier(name, cnpj,null, supplierAddress, user);

            // Inserindo o fornecedor
            supplierDAO.insertSupplier(supplier);

            System.out.println("Fornecedor inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar o fornecedor: " + e.getMessage());
        }
    }


    private void inserirProduto() {
        try {
            System.out.print("Descrição: ");
            String description = scanner.nextLine();

            System.out.print("Quantidade: ");
            int quantity = 0;
            try {
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
            } catch (InputMismatchException e) {
                System.out.println("Quantidade inválida. Insira um número inteiro.");
                scanner.nextLine(); // Consumir a entrada inválida
                return;
            }

            if (quantity <= 0) {
                System.out.println("Quantidade inválida. Insira um valor maior que zero.");
                return;
            }

            System.out.print("Preço: ");
            float price = 0;
            try {
                price = Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido. Insira um número válido.");
                return;
            }

            if (price <= 0) {
                System.out.println("Preço inválido. Insira um valor maior que zero.");
                return;
            }

            // Listar fornecedores disponíveis antes de solicitar o ID do fornecedor
            listarFornecedores(supplierDAO);

            System.out.print("ID do fornecedor: ");
            int idSupplier = 0;
            try {
                idSupplier = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
            } catch (InputMismatchException e) {
                System.out.println("ID do fornecedor inválido. Insira um número inteiro.");
                scanner.nextLine(); // Consumir a entrada inválida
                return;
            }

            productDAO.insertProduct(description, quantity, price, idSupplier);
            System.out.println("Produto inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }





    public static void listarVendedores(SellerDAO sellerDAO) {
        List<Seller> sellers = sellerDAO.getAllSellers();

        if (sellers.isEmpty()){
            System.out.println("Não há vendedores cadastrados.");
        } else {
            System.out.println("Lista de vendedores: ");
            for (Seller seller : sellers) {
                Person person = seller.getPerson();
                System.out.println("ID: " + person.getUser().getId_user());
                System.out.println("Name: " + person.getUser().getName());
                System.out.println("Email: " + person.getUser().getEmail());
                System.out.println("Last Name: " + person.getLast_name());
                System.out.println("CPF: " + person.getCpf());
                System.out.println("Birth Date: " + person.getBirth_date());
                System.out.println("Registration Date: " + person.getRegistration_date());
                System.out.println("Phone Number: " + person.getPhone_number());
                System.out.println("City: " + person.getAddress().getCity());
                System.out.println("State: " + person.getAddress().getState());
                System.out.println("Country: " + person.getAddress().getCountry());
                System.out.println("Address: " + person.getAddress().getAddress());
                System.out.println("Address Number: " + person.getAddress().getAddress_number());
                System.out.println("-----------------------");
            }
        }
    }


    public static void listarClientes(ClientDAO clientDAO) {
        List<Client> clients = clientDAO.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Client client : clients) {
                Person person = client.getPerson();
                System.out.println("ID: " + person.getUser().getId_user());
                System.out.println("Nome: " + person.getUser().getName());
                System.out.println("Email: " + person.getUser().getEmail());
                System.out.println("Último Nome: " + person.getLast_name());
                System.out.println("CPF: " + person.getCpf());
                System.out.println("Data de Nascimento: " + person.getBirth_date());
                System.out.println("Data de Registro: " + person.getRegistration_date());
                System.out.println("Número de Telefone: " + person.getPhone_number());
                System.out.println("Cidade: " + person.getAddress().getCity());
                System.out.println("Estado: " + person.getAddress().getState());
                System.out.println("País: " + person.getAddress().getCountry());
                System.out.println("Endereço: " + person.getAddress().getAddress());
                System.out.println("Número do Endereço: " + person.getAddress().getAddress_number());
                System.out.println("-----------------------");
            }
        }
    }



    public static void listarFornecedores(SupplierDAO supplierDAO) {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();

        if (suppliers.isEmpty()) {
            System.out.println("Não há fornecedores cadastrados.");
        } else {
            System.out.println("Lista de Fornecedores:");
            for (Supplier supplier : suppliers) {
                System.out.println("ID: " + supplier.getUser().getId_user());
                System.out.println("Nome: " + supplier.getUser().getName());
                System.out.println("Email: " + supplier.getUser().getEmail());
                System.out.println("CNPJ: " + supplier.getCnpj());
                // Include the registration date here
                System.out.println("Data de Registro: " + supplier.getRegistration_date());
                System.out.println("Cidade: " + supplier.getAddress().getCity());
                System.out.println("Estado: " + supplier.getAddress().getState());
                System.out.println("País: " + supplier.getAddress().getCountry());
                System.out.println("Endereço: " + supplier.getAddress().getAddress());
                System.out.println("Número do Endereço: " + supplier.getAddress().getAddress_number());
                System.out.println("----------------------------------");
            }
        }
    }


    public static void listarProdutos(ProductDAO productDAO) {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Não há produtos cadastrados.");
        } else {
            System.out.println("Lista de Produtos:");
            for (Product product : products) {
                System.out.println("ID: " + product.getId_product());
                System.out.println("Descrição: " + product.getDescription());
                System.out.println("Quantidade: " + product.getQuantity());
                System.out.println("Preço: " + product.getPrice());
                System.out.println("Fornecedor: " + product.getSupplier().getName());
                System.out.println("----------------------------------");
            }
        }
    }

    private void editarCliente() {

        List<Client> clients = clientDAO.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        }
        else {

            listarClientes(clientDAO);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o ID do cliente que deseja editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            Client client = clientDAO.getClientById(id);
            if (client == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            boolean continuar = true;
            while (continuar) {

                System.out.println("Selecione o campo que deseja editar:");
                System.out.println("1. Nome");
                System.out.println("2. Email");
                System.out.println("3. Último Nome");
                System.out.println("4. CPF");
                System.out.println("5. Data de Nascimento");
                System.out.println("6. Número de Telefone");
                System.out.println("7. Cidade");
                System.out.println("8. Estado");
                System.out.println("9. País");
                System.out.println("10. Endereço");
                System.out.println("11. Número do Endereço");
                System.out.println("12. Salvar e sair");
                System.out.println("13. Sair sem salvar");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha

                switch (opcao) {
                    case 1:
                        System.out.print("Novo Nome: ");
                        client.getPerson().getUser().setName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Novo Email: ");
                        client.getPerson().getUser().setEmail(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Novo Último Nome: ");
                        client.getPerson().setLast_name(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Novo CPF: ");
                        client.getPerson().setCpf(scanner.nextLine());
                        break;
                    case 5:
                        System.out.print("Nova Data de Nascimento (yyyy-mm-dd): ");
                        client.getPerson().setBirth_date(Date.valueOf(scanner.nextLine()));
                        break;
                    case 6:
                        System.out.print("Novo Número de Telefone: ");
                        client.getPerson().setPhone_number(scanner.nextLine());
                        break;
                    case 7:
                        System.out.print("Nova Cidade: ");
                        client.getPerson().getAddress().setCity(scanner.nextLine());
                        break;
                    case 8:
                        System.out.print("Novo Estado: ");
                        client.getPerson().getAddress().setState(scanner.nextLine());
                        break;
                    case 9:
                        System.out.print("Novo País: ");
                        client.getPerson().getAddress().setCountry(scanner.nextLine());
                        break;
                    case 10:
                        System.out.print("Novo Endereço: ");
                        client.getPerson().getAddress().setAddress(scanner.nextLine());
                        break;
                    case 11:
                        System.out.print("Novo Número do Endereço: ");
                        client.getPerson().getAddress().setAddress_number(scanner.nextLine());
                        break;
                    case 12:
                        try {
                            clientDAO.updateClient(client);
                            addressDAO.updateAddress(client.getPerson().getAddress());
                            System.out.println(client.getPerson().getAddress().getCity());
                            System.out.println("Cliente atualizado com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar o cliente: " + e.getMessage());
                        }
                        continuar = false;
                        break;
                    case 13:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        }
    }






    private void editarVendedor() {
        List<Seller> sellers = sellerDAO.getAllSellers();
        if (sellers.isEmpty()) {
            System.out.println("Não há vendedores cadastrados.");
        } else {

            listarVendedores(sellerDAO);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o ID do vendedor que deseja editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            Seller seller = sellerDAO.getSellerById(id);
            if (seller == null) {
                System.out.println("Vendedor não encontrado!");
                return;
            }


            boolean continuar = true;
            while (continuar) {
                System.out.println("Selecione o campo que deseja editar:");
                System.out.println("1. Nome");
                System.out.println("2. Email");
                System.out.println("3. Último Nome");
                System.out.println("4. CPF");
                System.out.println("5. Data de Nascimento");
                System.out.println("6. Número de Telefone");
                System.out.println("7. Cidade");
                System.out.println("8. Estado");
                System.out.println("9. País");
                System.out.println("10. Endereço");
                System.out.println("11. Número do Endereço");
                System.out.println("12. Salvar e sair");
                System.out.println("13. Sair sem salvar");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha

                switch (opcao) {
                    case 1:
                        System.out.print("Novo Nome: ");
                        seller.getPerson().getUser().setName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Novo Email: ");
                        seller.getPerson().getUser().setEmail(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Novo Último Nome: ");
                        seller.getPerson().setLast_name(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Novo CPF: ");
                        seller.getPerson().setCpf(scanner.nextLine());
                        break;
                    case 5:
                        System.out.print("Nova Data de Nascimento (yyyy-mm-dd): ");
                        seller.getPerson().setBirth_date(Date.valueOf(scanner.nextLine()));
                        break;
                    case 6:
                        System.out.print("Novo Número de Telefone: ");
                        seller.getPerson().setPhone_number(scanner.nextLine());
                        break;
                    case 7:
                        System.out.print("Nova Cidade: ");
                        seller.getPerson().getAddress().setCity(scanner.nextLine());
                        break;
                    case 8:
                        System.out.print("Novo Estado: ");
                        seller.getPerson().getAddress().setState(scanner.nextLine());
                        break;
                    case 9:
                        System.out.print("Novo País: ");
                        seller.getPerson().getAddress().setCountry(scanner.nextLine());
                        break;
                    case 10:
                        System.out.print("Novo Endereço: ");
                        seller.getPerson().getAddress().setAddress(scanner.nextLine());
                        break;
                    case 11:
                        System.out.print("Novo Número do Endereço: ");
                        seller.getPerson().getAddress().setAddress_number(scanner.nextLine());
                        break;
                    case 12:
                        try {
                            sellerDAO.updateSeller(seller);
                            addressDAO.updateAddress(seller.getPerson().getAddress());
                            System.out.println("Vendedor atualizado com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar o cliente: " + e.getMessage());
                        }
                        continuar = false;
                        break;
                    case 13:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        }
    }


    private void editarFornecedor() {

        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("Não há fornecedores cadastrados.");
        } else {
            listarFornecedores(supplierDAO);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o ID do fornecedor que deseja editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            Supplier supplier = supplierDAO.getSupplierByIdEdit(id);
            if (supplier == null) {
                System.out.println("Fornecedor não encontrado!");
                return;
            }

            boolean continuar = true;
            while (continuar) {
                System.out.println("Selecione o campo que deseja editar:");
                System.out.println("1. Nome");
                System.out.println("2. Email");
                System.out.println("3. Senha");
                System.out.println("4. CNPJ");
                System.out.println("5. Cidade");
                System.out.println("6. Estado");
                System.out.println("7. País");
                System.out.println("8. Endereço");
                System.out.println("9. Número do Endereço");
                System.out.println("10. Salvar e sair");
                System.out.println("11. Sair sem salvar");
                System.out.print("Digite o que será editado: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha

                switch (opcao) {
                    case 1:
                        System.out.print("Novo Nome: ");
                        supplier.getUser().setName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Novo Email: ");
                        supplier.getUser().setEmail(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Nova Senha: ");
                        supplier.getUser().setSenha(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Novo CNPJ: ");
                        supplier.setCnpj(scanner.nextLine());
                        break;
                    case 5:
                        System.out.print("Nova Cidade: ");
                        supplier.getAddress().setCity(scanner.nextLine());
                        break;
                    case 6:
                        System.out.print("Novo Estado: ");
                        supplier.getAddress().setState(scanner.nextLine());
                        break;
                    case 7:
                        System.out.print("Novo País: ");
                        supplier.getAddress().setCountry(scanner.nextLine());
                        break;
                    case 8:
                        System.out.print("Novo Endereço: ");
                        supplier.getAddress().setAddress(scanner.nextLine());
                        break;
                    case 9:
                        System.out.print("Novo Número do Endereço: ");
                        supplier.getAddress().setAddress_number(scanner.nextLine());
                        break;
                    case 10:
                        try {
                            supplierDAO.updateSupplier(supplier);
                            addressDAO.updateAddress(supplier.getAddress());
                            System.out.println("Fornecedor atualizado com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar o fornecedor: " + e.getMessage());
                        }
                        continuar = false;
                        break;
                    case 11:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        }
    }


    private void editarProduto(SupplierDAO supplierDAO) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Listar produtos disponíveis
            listarProdutos(productDAO);

            System.out.print("Digite o ID do produto que você deseja alterar: ");
            int idSelecionado = Integer.parseInt(scanner.nextLine());

            // Obter o produto pelo ID
            Product produto = productDAO.getProductById(idSelecionado);
            if (produto == null) {
                System.out.println("Produto não encontrado!");
                return;
            }

            boolean continuar = true;
            while (continuar) {
                System.out.println("Selecione o campo que deseja editar:");
                System.out.println("1. Descrição");
                System.out.println("2. Quantidade");
                System.out.println("3. Preço");
                System.out.println("4. Fornecedor");
                System.out.println("5. Salvar e sair");
                System.out.println("6. Sair sem salvar");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha

                switch (opcao) {
                    case 1:
                        System.out.print("Nova Descrição: ");
                        produto.setDescription(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Nova Quantidade: ");
                        produto.setQuantity(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 3:
                        System.out.print("Novo Preço: ");
                        produto.setPrice(Float.parseFloat(scanner.nextLine()));
                        break;
                    case 4:
                        // Listar fornecedores disponíveis
                        listarFornecedores(supplierDAO);
                        System.out.print("Digite o ID do fornecedor do produto: ");
                        int idFornecedor = Integer.parseInt(scanner.nextLine());

                        // Obter o fornecedor pelo ID
                        Supplier fornecedor = supplierDAO.getSupplierByIdEdit(idFornecedor);
                        if (fornecedor == null) {
                            System.out.println("Fornecedor não encontrado!");
                            break;
                        }
                        produto.setSupplier(fornecedor);
                        break;
                    case 5:
                        try {
                            productDAO.updateProduct(produto);
                            System.out.println("Produto atualizado com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar o produto: " + e.getMessage());
                        }
                        continuar = false;
                        break;
                    case 6:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao editar produto: " + e.getMessage());
        }
    }


    public static void deletarVendedor(SellerDAO sellerDAO, Scanner scanner) {
        List<Seller> sellers = sellerDAO.getAllSellers();

        if (sellers.isEmpty()) {
            System.out.println("Não há vendedores cadastrados.");
        }
        else {
            listarVendedores(sellerDAO);
            System.out.print("Digite o ID do vendedor que deseja deletar: ");
            int idVendedor = scanner.nextInt();
            try {
                sellerDAO.deleteSeller(idVendedor);
                System.out.println("Vendedor deletado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao deletar vendedor: " + e.getMessage());
            }
        }
    }

    public static void deletarCliente(ClientDAO clientDAO, Scanner scanner) {
        List<Client> clients = clientDAO.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        }
        else{
            listarClientes(clientDAO);
            System.out.print("Digite o ID do cliente que deseja deletar: ");
            int idCliente = scanner.nextInt();
            try {
                clientDAO.deleteClient(idCliente);
                System.out.println("Cliente deletado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao deletar cliente: " + e.getMessage());
            }
        }
    }

    public static void deletarFornecedor(SupplierDAO supplierDAO, Scanner scanner) {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();

        if (suppliers.isEmpty()) {
            System.out.println("Não há fornecedores cadastrados.");
        }

        else {
            listarFornecedores(supplierDAO);
            System.out.print("Digite o ID do fornecedor que deseja deletar: ");
            int idFornecedor = scanner.nextInt();
            try {
                supplierDAO.deleteSupplier(idFornecedor);
                System.out.println("Fornecedor deletado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao deletar fornecedor: " + e.getMessage());
            }
        }
    }

    public static void deletarProduto(ProductDAO productDAO, Scanner scanner) {
        List<Product> products = productDAO.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("Não há produtos cadastrados.");
        } else {
            listarProdutos(productDAO);
            System.out.print("Digite o ID do produto que deseja deletar: ");
            int idProduto = scanner.nextInt();
            productDAO.deleteProduct(idProduto);
            System.out.println("Produto deletado com sucesso!");
        }
    }

    private void registrarVenda(Scanner scanner) {
        /*
        Somente vendedor pode registrar uma venda. Dados: Cliente (Cliente); itens (ArrayList<Produto, qtde>);
        tipoPagamento (crédito, débito ou dinheiro);
         */

        System.out.println("\nRegistrar uma venda\n===================");

        try {
            listarClientes(clientDAO);
        } catch (Exception e){
            System.out.print(e.getMessage());
        }

        System.out.print("\nId do cliente: ");
        int idCliente = scanner.nextInt();

        System.out.print("\nTipo de pagamento: \n(1) Dinheiro\n(2) Débito\n(3) Crédito\n> ");
        int tipoPagamento = scanner.nextInt();

        listarProdutos(productDAO);
        ArrayList<Product> itens = new ArrayList<>();
        ArrayList<Integer> qtde = new ArrayList<>();

        int entrada = 0;
        while (entrada != 2) {
            System.out.println("(1) Adicionar um produto");
            System.out.println("(2) Finalizar venda");

            System.out.print("> ");
            entrada = scanner.nextInt();

            switch (entrada) {

                case 1:
                    System.out.print("Digite o id do produto que gostaria de adicionar: ");
                    int newIdProduto = scanner.nextInt();
                    System.out.print("Digite a quantidade: ");
                    int quantidadeProduto = scanner.nextInt();

                    itens.add(productDAO.getProductById(newIdProduto));
                    qtde.add(quantidadeProduto);
                    break;
                case 2:
                    System.out.println("...");
                    break;
                default:
                    System.out.println("Entrada inválida!");
                    break;
            }
        }

        Client client = clientDAO.getClientById(idCliente);
        Payment payment = payDAO.getPaymentById(tipoPagamento);
        Seller seller = sellerDAO.getSellerById(clienteId);

        float precoTotal = 0;

        for (int i = 0; i < itens.size(); i++) {
            precoTotal += itens.get(i).getPrice() * qtde.get(i);
        }

        int parcelas = 1;
        if (tipoPagamento == 3 && precoTotal >= 1000) {
            System.out.println("Crédito\n=======\nParcelas em até 5x sem juros.\nJuros de 5% para parcelas acima de 5x.");
            System.out.print("\nNúmero de parcelas (máximo 10): ");
            parcelas = scanner.nextInt();

            if (parcelas > 5) {
                precoTotal *= 1.05;  // Adiciona 5% de juros
            }
            if (parcelas > 10) {
                System.out.println("Número máximo de parcelas atingido. Tente novamente.");
            }
        }

        Sale sale = new Sale(0, client, seller, new Timestamp(2024), payment, precoTotal, parcelas);

        int sale_id = saleDAO.insertSale(sale);

        sale.setId_sale(sale_id);

        try {
            for (int i = 0; i < itens.size(); i++) {
                SaleItem saleItem = new SaleItem(sale_id, sale, itens.get(i), qtde.get(i), itens.get(i).getPrice());
                saleItemDAO.insertSaleItem(saleItem);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }


    }


}
