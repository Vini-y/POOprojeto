import DAO.*;
import Models.*;
import Utils.DatabaseConnection;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
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
                        registarVenda(scanner);
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
            Person person = new Person(lastName, cpf, birthDate, phoneNumber, clientAddress, user);

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
            Address sellerAddress = new Address(city, state, country, address, addressNumber);

            // Criando o objeto User
            User user = new User(1, name, email, senha);

            // Criando o objeto Person
            Person person = new Person(lastName, cpf, birthDate, phoneNumber, sellerAddress, user);

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

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            System.out.print("CNPJ: ");
            String cnpj = scanner.nextLine();

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
            Supplier supplier = new Supplier(name, cnpj, supplierAddress, user);

            // Inserindo o fornecedor
            supplierDAO.insertSupplier(supplier);

            System.out.println("Fornecedor inserido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar o fornecedor: " + e.getMessage());
        }
    }


    private void inserirProduto() {
        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        System.out.print("Quantidade: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.print("Preço: ");
        float price = scanner.nextFloat();
        scanner.nextLine(); // Consumir a quebra de linha

        // Listar fornecedores disponíveis antes de solicitar o ID do fornecedor
        listarFornecedores(supplierDAO);

        System.out.print("ID do fornecedor: ");
        int idSupplier = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        productDAO.insertProduct(description, quantity, price, idSupplier);
    }





    public static void listarVendedores(SellerDAO sellerDAO) {
        List<Seller> sellers = sellerDAO.getAllSellers();

        if (sellers.isEmpty()){
            System.out.println("Não há vendedores cadastrados.");
        }

        else {
            System.out.println("Lista de vendedores: ");
            for (Seller seller : sellers) {
                System.out.println("ID: " + seller.getPerson().getUser().getId_user());
                System.out.println("Name: " + seller.getPerson().getUser().getName());
                System.out.println("Email: " + seller.getPerson().getUser().getEmail());
                System.out.println("Last Name: " + seller.getPerson().getLast_name());
                System.out.println("CPF: " + seller.getPerson().getCpf());
                System.out.println("Birth Date: " + seller.getPerson().getBirth_date());
                System.out.println("Phone Number: " + seller.getPerson().getPhone_number());
                System.out.println("City: " + seller.getPerson().getAddress().getCity());
                System.out.println("State: " + seller.getPerson().getAddress().getState());
                System.out.println("Country: " + seller.getPerson().getAddress().getCountry());
                System.out.println("Address: " + seller.getPerson().getAddress().getAddress());
                System.out.println("Address Number: " + seller.getPerson().getAddress().getAddress_number());
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
                System.out.println("ID: " + client.getPerson().getUser().getId_user());
                System.out.println("Nome: " + client.getPerson().getUser().getName());
                System.out.println("Email: " + client.getPerson().getUser().getEmail());
                System.out.println("Último Nome: " + client.getPerson().getLast_name());
                System.out.println("CPF: " + client.getPerson().getCpf());
                System.out.println("Data de Nascimento: " + client.getPerson().getBirth_date());
                System.out.println("Número de Telefone: " + client.getPerson().getPhone_number());
                System.out.println("Cidade: " + client.getPerson().getAddress().getCity());
                System.out.println("Estado: " + client.getPerson().getAddress().getState());
                System.out.println("País: " + client.getPerson().getAddress().getCountry());
                System.out.println("Endereço: " + client.getPerson().getAddress().getAddress());
                System.out.println("Número do Endereço: " + client.getPerson().getAddress().getAddress_number());
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
        try {
            // Listar clientes disponíveis
            listarClientes(clientDAO);

            System.out.print("Digite o ID do cliente que você deseja alterar: ");
            int idSelecionado = Integer.parseInt(scanner.nextLine());

            System.out.print("Novo Nome: ");
            String name = scanner.nextLine();

            System.out.print("Novo Email: ");
            String email = scanner.nextLine();

            System.out.print("Nova Senha: ");
            String senha = scanner.nextLine();

            System.out.print("Novo Sobrenome: ");
            String lastName = scanner.nextLine();

            System.out.print("Novo CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Nova Data de Nascimento (YYYY-MM-DD): ");
            String birthDateString = scanner.nextLine();
            Date birthDate = Date.valueOf(birthDateString);

            System.out.print("Novo Número de Telefone: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Nova Cidade: ");
            String city = scanner.nextLine();

            System.out.print("Novo Estado: ");
            String state = scanner.nextLine();

            System.out.print("Novo País: ");
            String country = scanner.nextLine();

            System.out.print("Novo Endereço: ");
            String address = scanner.nextLine();

            System.out.print("Novo Número do Endereço: ");
            String addressNumber = scanner.nextLine();

            // Criar um objeto Address com os novos valores
            Address newAddress = new Address(city, state, country, address, addressNumber);

            // Criar um objeto User com os novos valores
            User newUser = new User(1, name, email, senha);

            // Criar um objeto Person com os novos valores
            Person newPerson = new Person(lastName, cpf, birthDate, phoneNumber, newAddress, newUser);

            // Criar um objeto Client com os novos valores
            Client updatedClient = new Client(newPerson);
            updatedClient.getPerson().getUser().setId_user(idSelecionado); // Definir o ID do usuário

            // Atualizar o cliente no banco de dados
            clientDAO.updateClient(updatedClient);

            System.out.println("Cliente editado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
    }






    private void editarVendedor() {
        try {
            // Listar vendedores disponíveis
            listarVendedores(sellerDAO);

            System.out.print("Digite o ID do vendedor que você deseja alterar: ");
            int idSelecionado = Integer.parseInt(scanner.nextLine());

            System.out.print("Novo Nome: ");
            String name = scanner.nextLine();

            System.out.print("Novo Email: ");
            String email = scanner.nextLine();

            System.out.print("Nova Senha: ");
            String senha = scanner.nextLine();

            System.out.print("Novo Sobrenome: ");
            String lastName = scanner.nextLine();

            System.out.print("Novo CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Nova Data de Nascimento (YYYY-MM-DD): ");
            String birthDateString = scanner.nextLine();
            Date birthDate = Date.valueOf(birthDateString);

            System.out.print("Novo Número de Telefone: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Nova Cidade: ");
            String city = scanner.nextLine();

            System.out.print("Novo Estado: ");
            String state = scanner.nextLine();

            System.out.print("Novo País: ");
            String country = scanner.nextLine();

            System.out.print("Novo Endereço: ");
            String address = scanner.nextLine();

            System.out.print("Novo Número do Endereço: ");
            String addressNumber = scanner.nextLine();

            // Criar um objeto Address com os novos valores
            Address newAddress = new Address(city, state, country, address, addressNumber);

            // Criar um objeto User com os novos valores
            User newUser = new User(1, name, email, senha);

            // Criar um objeto Person com os novos valores
            Person newPerson = new Person(lastName, cpf, birthDate, phoneNumber, newAddress, newUser);

            // Criar um objeto Seller com os novos valores
            Seller updatedSeller = new Seller(newPerson);
            updatedSeller.getPerson().getUser().setId_user(idSelecionado); // Definir o ID do usuário

            // Atualizar o vendedor no banco de dados
            sellerDAO.updateSeller(updatedSeller);

            System.out.println("Vendedor editado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao editar vendedor: " + e.getMessage());
        }
    }


    private void editarFornecedor() {
        try {
            // Listar fornecedores disponíveis
            listarFornecedores(supplierDAO);

            System.out.print("Digite o ID do fornecedor que você deseja alterar: ");
            int idSelecionado = Integer.parseInt(scanner.nextLine());

            System.out.print("Novo Nome: ");
            String name = scanner.nextLine();

            System.out.print("Novo Email: ");
            String email = scanner.nextLine();

            System.out.print("Nova Senha: ");
            String senha = scanner.nextLine();

            System.out.print("Novo CNPJ: ");
            String cnpj = scanner.nextLine();

            System.out.print("Nova Cidade: ");
            String city = scanner.nextLine();

            System.out.print("Novo Estado: ");
            String state = scanner.nextLine();

            System.out.print("Novo País: ");
            String country = scanner.nextLine();

            System.out.print("Novo Endereço: ");
            String address = scanner.nextLine();

            System.out.print("Novo Número do Endereço: ");
            String addressNumber = scanner.nextLine();

            // Criar um objeto Address com os novos valores
            Address newAddress = new Address(city, state, country, address, addressNumber);

            // Criar um objeto User com os novos valores
            User newUser = new User(1, name, email, senha);

            // Criar um objeto Supplier com os novos valores
            Supplier updatedSupplier = new Supplier(name, cnpj, newAddress, newUser);
            updatedSupplier.getUser().setId_user(idSelecionado); // Definir o ID do usuário

            // Atualizar o fornecedor no banco de dados
            supplierDAO.updateSupplier(updatedSupplier);

            System.out.println("Fornecedor editado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao editar fornecedor: " + e.getMessage());
        }
    }


    private void editarProduto(SupplierDAO supplierDAO) {
        try {
            // Listar produtos disponíveis
            listarProdutos(productDAO);

            System.out.print("Digite o ID do produto que você deseja alterar: ");
            int idSelecionado = Integer.parseInt(scanner.nextLine());

            System.out.print("Nova Descrição: ");
            String novaDescricao = scanner.nextLine();

            System.out.print("Nova Quantidade: ");
            int novaQuantidade = Integer.parseInt(scanner.nextLine());

            System.out.print("Novo Preço: ");
            float novoPreco = Float.parseFloat(scanner.nextLine());

            // Listar fornecedores disponíveis
            listarFornecedores(supplierDAO);
            System.out.print("Digite o ID do fornecedor do produto: ");
            int idFornecedor = Integer.parseInt(scanner.nextLine());

            // Obter o fornecedor pelo ID
            Supplier fornecedor = supplierDAO.getSupplierById(idFornecedor);

            // Criar um objeto Product com os novos valores
            Product novoProduto = new Product(1, novaDescricao, novaQuantidade, novoPreco, fornecedor);

            // Atualizar o produto no banco de dados
            productDAO.updateProduct(novoProduto);

            System.out.println("Produto editado com sucesso!");
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

    private void registarVenda(Scanner scanner) {
        /*
        Somente vendedor pode registrar uma venda. Dados: Cliente (Cliente); itens (ArrayList<Produto, qtde>);
        tipoPagamento (crédito, débito ou dinheiro);
         */

        System.out.println("\nRegistrar uma venda\n===================");

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
                    System.out.println("Finalizando a venda...");
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
        if (tipoPagamento == 3 && precoTotal >= 1000){
            System.out.println("Crédito\n=======\nParcelas em até 5x sem juros.\nJuros de 5% para parcelas acima de 5x.\n");
            System.out.println("\nNúmero de parcelas (1 para ignorar): ");
            parcelas = scanner.nextInt();
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
