package DAO;

import java.sql.*;

import Utils.DatabaseConnection;

public class SupplierDAO {

    public void insertSupplier(String name, String email, String senha, String cnpj, String city, String state,
                               String country, String address, String addressNumber) throws SQLException {
        String sql = "{CALL insert_supplier(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cnpj);
            stmt.setString(5, city);
            stmt.setString(6, state);
            stmt.setString(7, country);
            stmt.setString(8, address);
            stmt.setString(9, addressNumber);

            stmt.executeUpdate();
        }
    }
    public void updateSupplier(int id_selecionado, String name, String email, String senha, String cnpj,
                               String city, String state,
                               String country, String address, String addressNumber)throws SQLException{

        String sql = "{CALL update_supplier(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            if (conn != null) {
                stmt.setInt(1, id_selecionado);
                stmt.setString(2, name);
                stmt.setString(3, email);
                stmt.setString(4, senha);
                stmt.setString(5, cnpj);
                stmt.setString(6, city);
                stmt.setString(7, state);
                stmt.setString(8, country);
                stmt.setString(9, address);
                stmt.setString(10, addressNumber);

                stmt.executeUpdate();
            }else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }
        }


    }
    public static void selectSupplier() {
        System.out.println("Entrou");
        String query = "SELECT s.id_supplier, u.name, s.cnpj, s.registration_date, a.city, a.state, a.country, a.address, a.address_number, u.email " +
                "FROM Supplier s " +
                "JOIN Person p ON s.id_supplier = p.id_person " +
                "JOIN Address a ON p.address_id = a.id_address " +
                "JOIN User u ON p.id_person = u.id_user";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("tentei");

            while (rs.next()) {
                System.out.println("entrei no while");
                int idFornecedor = rs.getInt("id_supplier");
                String nome = rs.getString("name");
                String cnpj = rs.getString("cnpj");
                String dataCadastro = rs.getString("registration_date");
                String cidade = rs.getString("city");
                String estado = rs.getString("state");
                String pais = rs.getString("country");
                String endereco = rs.getString("address");
                String numeroEndereco = rs.getString("address_number");
                String email = rs.getString("email");

                System.out.println("ID: " + idFornecedor);
                System.out.println("Nome: " + nome);
                System.out.println("CNPJ: " + cnpj);
                System.out.println("Data de Cadastro: " + dataCadastro);
                System.out.println("Endereço: " + endereco + ", " + numeroEndereco + ", " + cidade + ", " + estado + ", " + pais);
                System.out.println("Email: " + email);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores: " + e.getMessage());
        }
    }

}

