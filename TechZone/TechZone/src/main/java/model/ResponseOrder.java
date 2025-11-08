/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author letan
 */
public class ResponseOrder {
    private int responseOrderId;
    private String reason;
    private Orders orderId;
    private Date createAt = new Date();

    public ResponseOrder() {
    }

    public ResponseOrder(int responseOrderId, String reason, Orders orderId, Date createAt) {
        this.responseOrderId = responseOrderId;
        this.reason = reason;
        this.orderId = orderId;
        this.createAt = createAt;
    }

    public int getResponseOrderId() {
        return responseOrderId;
    }

    public void setResponseOrderId(int responseOrderId) {
        this.responseOrderId = responseOrderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
}
