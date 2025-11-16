package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private String orderId, email;
    private long totalAmount;
    private String isPaid;
    private LocalDateTime orderDateTime;

    public Order(String orderId, String email, long totalAmount, String isPaid, LocalDateTime orderDateTime) {
        this.orderId = orderId;
        this.email = email;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.orderDateTime = orderDateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public String isPaid() {
        return isPaid;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", email='" + email + '\'' +
                ", totalAmount=" + totalAmount +
                ", isPaid=" + isPaid +
                ", orderDate=" + orderDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return totalAmount == order.totalAmount && isPaid == order.isPaid && Objects.equals(orderId, order.orderId) && Objects.equals(email, order.email) && Objects.equals(orderDateTime, order.orderDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, email, totalAmount, isPaid, orderDateTime);
    }
}
