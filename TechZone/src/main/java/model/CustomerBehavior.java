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
public class CustomerBehavior {

    private int behaviorId;         
    private Account account;         
    private String productCode;      
    private int quantity;            
    private String actionType;       
    private Date actionTime;    
    private boolean isDeleted = false;       

    public CustomerBehavior() {
    }

    public CustomerBehavior(int behaviorId, Account account, String productCode, int quantity,
            String actionType, Date actionTime, boolean isDeleted) {
        this.behaviorId = behaviorId;
        this.account = account;
        this.productCode = productCode;
        this.quantity = quantity;
        this.actionType = actionType;
        this.actionTime = actionTime;
        this.isDeleted = isDeleted;
    }

    // ===== Getter & Setter =====
    public int getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
