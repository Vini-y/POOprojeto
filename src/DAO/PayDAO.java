package DAO;

import Utils.DatabaseConnection;
import Models.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayDAO {

    public void insertPayment(String type) {
        String sql = "INSERT INTO `Pay` (`tipo`) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.executeUpdate();

            System.out.println("Pagamento inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePayment(int id_pay, String type) {
        String sql = "UPDATE `Pay` SET `tipo` = ? WHERE `id_Pay` = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.setInt(2, id_pay);
            stmt.executeUpdate();

            System.out.println("Pagamento atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM `Pay`";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_pay = rs.getInt("id_Pay");
                String type = rs.getString("tipo");

                Payment payment = new Payment(id_pay, type);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public Payment getPaymentById(int id_pay) {
        String sql = "SELECT * FROM `Pay` WHERE `id_Pay` = ?";
        Payment payment = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_pay);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("tipo");
                    payment = new Payment(id_pay, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payment;
    }

    public void deletePayment(int id_pay) {
        String sql = "DELETE FROM `Pay` WHERE `id_Pay` = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_pay);
            stmt.executeUpdate();

            System.out.println("Pagamento deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
