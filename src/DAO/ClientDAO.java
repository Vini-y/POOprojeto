package DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import Utils.DatabaseConnection;

public class ClientDAO {

    public void insertClient(String name, String email, String senha, String lastName, String cpf,
                             Date birthDate, String phoneNumber, Date registrationDate,
                             String city, String state, String country, String address,
                             String addressNumber) throws SQLException {
        String sql = "{CALL insert_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            if (conn != null) {
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
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }
        }
    }
}
