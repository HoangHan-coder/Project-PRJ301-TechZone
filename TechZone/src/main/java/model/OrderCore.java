/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author NgKaitou
 */
public class OrderCore {

    private int orderId;
    private Account account;
    private String orderCode;
    private LocalDateTime orderTime;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private String status;
    private String shippingAddress;
    private String PaymentMethod;
    private String paymentStatus;
    private Voucher voucher;
    private boolean isDeaded;

    public OrderCore() {

    }

    public OrderCore(int orderId, Account account, String orderCode, LocalDateTime orderTime, BigDecimal totalAmount, BigDecimal shippingFee, String status, String shippingAddress, String PaymentMethod, String paymentStatus, Voucher voucher, boolean isDeaded) {
        this.orderId = orderId;
        this.account = account;
        this.orderCode = orderCode;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.shippingFee = shippingFee;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.PaymentMethod = PaymentMethod;
        this.paymentStatus = paymentStatus;
        this.voucher = voucher;
        this.isDeaded = isDeaded;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public int getTotalAmount() {
        return totalAmount.toBigInteger().intValue();
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getShippingFee() {
        return shippingFee.toBigInteger().intValue();
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getStatus() {
        switch (this.status) {
            case "PROCESSING":
                return "Đang chờ xử lý";
            case "PENDING":
                return "Đang giao hàng";
            case "COMPLETED":
                return "Đã giao";
            case "CANCEL":
                return "Hủy giao";

            default:
                return "Đang chờ xử lý";
        }
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
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public boolean isIsDeaded() {
        return isDeaded;
    }

    public void setIsDeaded(boolean isDeaded) {
        this.isDeaded = isDeaded;
    }

}
