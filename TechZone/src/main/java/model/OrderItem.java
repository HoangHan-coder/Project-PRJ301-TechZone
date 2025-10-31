/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author NgKaitou
 */
public class OrderItem {
    private int orderItemId;
    private OrderCore order;
    private Product product;
    private String productNameSnapshot;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;

    public OrderItem() {
    }

    public OrderItem(int orderItemId, OrderCore order, Product product, String productNameSnapshot, BigDecimal unitPrice, int quantity, BigDecimal totalPrice) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.product = product;
        this.productNameSnapshot = productNameSnapshot;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderCore getOrder() {
        return order;
    }

    public void setOrder(OrderCore order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductNameSnapshot() {
        return productNameSnapshot;
    }

    public void setProductNameSnapshot(String productNameSnapshot) {
        this.productNameSnapshot = productNameSnapshot;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
