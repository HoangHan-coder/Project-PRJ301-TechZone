/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dto.OrderItemDTO;
import java.util.List;

/**
 *
 * @author letan
 */
public class DetailOrder {
    private List<OrderItemDTO> product;
    private List<Orders> order;
    private List<Accounts> account;
    public DetailOrder() {
    }

    public DetailOrder(List<OrderItemDTO> product, List<Orders> order, List<Accounts> account) {
        this.product = product;
        this.order = order;
        this.account = account;
    }

    public List<OrderItemDTO> getProduct() {
        return product;
    }

    public void setProduct(List<OrderItemDTO> product) {
        this.product = product;
    }

    public List<Orders> getOrder() {
        return order;
    }

    public void setOrder(List<Orders> order) {
        this.order = order;
    }

    public List<Accounts> getAccount() {
        return account;
    }

    public void setAccount(List<Accounts> account) {
        this.account = account;
    }

    
}
