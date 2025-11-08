/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class Cart {
    private int cartId;
    private Account account;
    private LocalDateTime createdAt;
    private String status;

    public Cart() {
    }

    public Cart(int cartId, Account account, LocalDateTime createdAt, String status) {
        this.cartId = cartId;
        this.account = account;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}