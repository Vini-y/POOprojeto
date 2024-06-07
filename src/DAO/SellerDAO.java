package DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
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
}
