/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author letan
 */
public class DetailOrder {
    private List<Product> product;
    private List<Orders> order;
    private List<Account> account;
    public DetailOrder() {
    }

    public DetailOrder(List<Product> product, List<Orders> order, List<Account> account) {
        this.product = product;
        this.order = order;
        this.account = account;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Orders> getOrder() {
        return order;
    }

    public void setOrder(List<Orders> order) {
        this.order = order;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    
}
