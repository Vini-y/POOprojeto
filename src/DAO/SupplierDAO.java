package DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
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

}

