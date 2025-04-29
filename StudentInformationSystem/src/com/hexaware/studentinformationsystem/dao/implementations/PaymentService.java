package com.hexaware.studentinformationsystem.dao.implementations;

import com.hexaware.studentinformationsystem.dao.interfaces.PaymentDAO;
import com.hexaware.studentinformationsystem.entity.Payment;
import com.hexaware.studentinformationsystem.exception.PaymentNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PaymentService implements PaymentDAO {
    private Connection connection;

    public PaymentService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Payment getPaymentById(int paymentId) throws PaymentNotFoundException {
        Payment payment = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM payments WHERE paymentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, paymentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                payment = mapResultSetToPayment(rs);
            } else {
                throw new PaymentNotFoundException("Payment with ID " + paymentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return payment;
    }

    @Override
    public void addPayment(Payment payment) {
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO payments (studentId, amount, paymentDate) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, payment.getStudentId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, Date.valueOf(payment.getPaymentDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void updatePayment(Payment payment) throws PaymentNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "UPDATE payments SET studentId = ?, amount = ?, paymentDate = ? WHERE paymentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, payment.getStudentId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, Date.valueOf(payment.getPaymentDate()));
            ps.setInt(4, payment.getPaymentId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new PaymentNotFoundException("Payment with ID " + payment.getPaymentId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void deletePayment(int paymentId) throws PaymentNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM payments WHERE paymentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, paymentId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new PaymentNotFoundException("Payment with ID " + paymentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public List<Payment> getPaymentsByStudentId(int studentId) {
        List<Payment> payments = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM payments WHERE studentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return payments;
    }

    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        return new Payment(
            rs.getInt("paymentId"),
            rs.getInt("studentId"),
            rs.getDouble("amount"),
            rs.getDate("paymentDate").toLocalDate()
        );
    }

    private void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            // Do NOT close connection here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
