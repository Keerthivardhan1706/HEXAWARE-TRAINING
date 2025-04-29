package com.hexaware.studentinformationsystem.dao.interfaces;


import com.hexaware.studentinformationsystem.entity.Payment;
import java.util.List;

public interface PaymentDAO {
    void addPayment(Payment payment);
    void updatePayment(Payment payment);
    void deletePayment(int paymentId);
    Payment getPaymentById(int paymentId);
    List<Payment> getAllPayments();
}
