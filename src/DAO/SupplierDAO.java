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
        String sql = "SELECT s.id_supplier, s.name, s.cnpj, s.registration_date, " +
                "a.city, a.state, a.country, a.address, a.address_number " +
                "FROM Supplier s " +
                "INNER JOIN Address a ON s.address_id = a.id_address";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID do Fornecedor: " + rs.getInt("id_supplier"));
                System.out.println("Nome: " + rs.getString("name"));
                System.out.println("CNPJ: " + rs.getString("cnpj"));
                System.out.println("Data de Registro: " + rs.getDate("registration_date"));
                System.out.println("Cidade: " + rs.getString("city"));
                System.out.println("Estado: " + rs.getString("state"));
                System.out.println("País: " + rs.getString("country"));
                System.out.println("Endereço: " + rs.getString("address"));
                System.out.println("Número do Endereço: " + rs.getString("address_number"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar fornecedores: " + e.getMessage());
        }
    }

        public static void deleteSupplier(int supplierId) {
            String sql = "{CALL delete_supplier(?)}";

            try (Connection conn = DatabaseConnection.getConnection();
                 CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setInt(1, supplierId);
                stmt.execute();

                System.out.println("Fornecedor deletado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


}

