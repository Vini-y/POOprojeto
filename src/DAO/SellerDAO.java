package DAO;

import java.sql.*;

import Utils.DatabaseConnection;

public class SellerDAO {

    public void insertSeller(String name, String email, String senha, String lastName, String cpf,
                             Date birthDate, String phoneNumber, Date registrationDate,
                             String city, String state, String country, String address,
                             String addressNumber) throws SQLException {
        String sql = "{CALL insert_seller(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, lastName);
            stmt.setString(5, cpf);
            stmt.setDate(6, birthDate);
            stmt.setString(7, phoneNumber);
            stmt.setDate(8, registrationDate);
            stmt.setString(9, city);
            stmt.setString(10, state);
            stmt.setString(11, country);
            stmt.setString(12, address);
            stmt.setString(13, addressNumber);

            stmt.executeUpdate();
        }
    }
    public void updateSeller(int id_selecionado, String name, String email, String senha, String lastName, String cpf,
                             Date birthDate, String phoneNumber,
                             String city, String state, String country, String address,
                             String addressNumber)throws SQLException {
        String sql = "{CALL update_seller(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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

    public static void selectSeller() {
        String query = "SELECT s.id_seller, u.name, p.last_name, p.cpf, p.birth_date, p.phone_number, a.city, a.state, a.country, a.address, a.address_number, u.email " +
                "FROM Seller s " +
                "JOIN Person p ON s.id_seller = p.id_person " +
                "JOIN Address a ON p.address_id = a.id_address " +
                "JOIN User u ON p.id_person = u.id_user";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idVendedor = rs.getInt("id_seller");
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

                System.out.println("ID do Vendedor: " + idVendedor);
                System.out.println("Nome: " + nome + " " + sobrenome);
                System.out.println("CPF: " + cpf);
                System.out.println("Data de Nascimento: " + dataNascimento);
                System.out.println("Telefone: " + telefone);
                System.out.println("Endereço: " + endereco + ", " + numeroEndereco + ", " + cidade + ", " + estado + ", " + pais);
                System.out.println("Email: " + email);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendedores: " + e.getMessage());
        }
    }

    public static void removeSeller(int sellerId) throws SQLException {
        String sql = "{CALL delete_seller(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, sellerId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
