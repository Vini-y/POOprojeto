package DAO;

import java.sql.*;

import Utils.DatabaseConnection;

public class ClientDAO {



    public void insertClient(String name, String email, String senha, String lastName, String cpf,
                             Date birthDate, String phoneNumber,
                             String city, String state, String country, String address,
                             String addressNumber) throws SQLException {
        String sql = "{CALL insert_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, lastName);
            stmt.setString(5, cpf);
            stmt.setDate(6, birthDate);
            stmt.setString(7, phoneNumber);
            stmt.setString(8, city);
            stmt.setString(9, state);
            stmt.setString(10, country);
            stmt.setString(11, address);
            stmt.setString(12, addressNumber);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }




    public void updateClient(int id_selecionado, String name, String email, String senha, String lastName, String cpf,
                             Date birthDate, String phoneNumber,
                             String city, String state, String country, String address,
                             String addressNumber) throws SQLException {
        String sql = "{CALL update_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            if (conn != null) {
                stmt.setInt(1, id_selecionado);
                stmt.setString(2, name);
                stmt.setString(3, email);
                stmt.setString(4, senha);
                stmt.setString(5, lastName);
                stmt.setString(6, cpf);
                stmt.setDate(7, birthDate);
                stmt.setString(8, phoneNumber);
                stmt.setString(9, city);
                stmt.setString(10, state);
                stmt.setString(11, country);
                stmt.setString(12, address);
                stmt.setString(13, addressNumber);


                stmt.executeUpdate();
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }
        }
    }
        public static void selectClient() {
            String query = "SELECT c.id_client, u.name, p.last_name, p.cpf, p.birth_date, p.phone_number, a.city, a.state, a.country, a.address, a.address_number, u.email " +
                    "FROM Client c " +
                    "JOIN Person p ON c.id_client = p.id_person " +
                    "JOIN Address a ON p.address_id = a.id_address " +
                    "JOIN User u ON p.id_person = u.id_user";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                DatabaseConnection.getConnection();

                while (rs.next()) {
                    int idCliente = rs.getInt("id_client");
                    String nome = rs.getString("name");
                    String sobrenome = rs.getString("last_name");
                    String cpf = rs.getString("cpf");
                    String dataNascimento = rs.getString("birth_date");
                    String telefone = rs.getString("phone_number");
                    String cidade = rs.getString("city");
                    String estado = rs.getString("state");
                    String pais = rs.getString("country");
                    String endereco = rs.getString("address");
                    String numeroEndereco = rs.getString("address_number");
                    String email = rs.getString("email");


                    System.out.println("ID: " + idCliente);
                    System.out.println("Nome: " + nome + " " + sobrenome);
                    System.out.println("CPF: " + cpf);
                    System.out.println("Data de Nascimento: " + dataNascimento);
                    System.out.println("Telefone: " + telefone);
                    System.out.println("Endereço: " + endereco + ", " + numeroEndereco + ", " + cidade + ", " + estado + ", " + pais);
                    System.out.println("Email: " + email);
                    System.out.println("----------------------------------");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao listar clientes: " + e.getMessage());
            }
        }



    public static void deleteClient(int clientId) {
        String sql = "{CALL delete_client(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, clientId);
            stmt.execute();

            System.out.println("Cliente deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }

