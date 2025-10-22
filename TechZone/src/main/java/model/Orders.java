/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author letan
 */
public class Orders {
    private int orderId;             // Mã đơn hàng
    private int accountId;           // ID người dùng
    private String orderCode;        // Mã code đơn hàng
    private LocalDateTime orderTime; // Thời gian đặt hàng
    private double totalAmount;      // Tổng tiền
    private double shippingFee;      // Phí vận chuyển
    private String status;           // Trạng thái đơn hàng
    private String shippingAddress;  // Địa chỉ giao hàng
    private String paymentMethod;    // Phương thức thanh toán
    private String paymentStatus;    // Trạng thái thanh toán
    private Integer voucherId;       // Mã giảm giá (nếu có)
    private boolean isDeleted;       // Cờ xóa mềm (soft delete)

    // ======= Constructors =======
    public Orders() {}

    public Orders(int orderId, int accountId, String orderCode, LocalDateTime orderTime, double totalAmount,
                 double shippingFee, String status, String shippingAddress, String paymentMethod,
                 String paymentStatus, Integer voucherId, boolean isDeleted) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.orderCode = orderCode;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.shippingFee = shippingFee;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.voucherId = voucherId;
        this.isDeleted = isDeleted;
    }

    // ======= Getters & Setters =======
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
