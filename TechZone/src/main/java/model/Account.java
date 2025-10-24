/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author pc
 */
public class Account {

    private int accountId;
    private String userName;
    private String passWordHarh;
    private String fullName;
    private String email;
    private String phone;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private String roleName;

    public Account() {
    }

    public Account(int accountId, String userName, String passWordHarh, String fullName, String email, String phone, boolean isDeleted, LocalDateTime createdAt, LocalDateTime updateAt, String roleName) {
        this.accountId = accountId;
        this.userName = userName;
        this.passWordHarh = passWordHarh;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.roleName = roleName;
    }

    public Account(int accountId, String userName, String fullName, String email, String phone, String roleName) {
        this.accountId = accountId;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roleName = roleName;
    }

    public Account(int accountId, String userName, String passWordHarh, String fullName, String email, String phone, String roleName) {
        this.accountId = accountId;
        this.userName = userName;
        this.passWordHarh = passWordHarh;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roleName = roleName;
    }

    
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWordHarh() {
        return passWordHarh;
    }

    public void setPassWordHarh(String passWordHarh) {
        this.passWordHarh = passWordHarh;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
