package DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import Utils.DatabaseConnection;

public class SupplierDAO {

    public void insertSupplier(String name, String email, String senha, String cnpj,
                               Date registrationDate, String city, String state,
                               String country, String address, String addressNumber) throws SQLException {
        String sql = "{CALL insert_supplier(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cnpj);
            stmt.setDate(5, registrationDate);
            stmt.setString(6, city);
            stmt.setString(7, state);
            stmt.setString(8, country);
            stmt.setString(9, address);
            stmt.setString(10, addressNumber);

            stmt.executeUpdate();
        }
    }
}

