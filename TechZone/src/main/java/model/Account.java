/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pc
 */
public class Account {

    private int accountId;
    private String userName;
    private String passWord;
    private String fullName;
    private String email;
    private String phone;
    private String roleName;
    private boolean isDeleted;

    public Account() {
    }

    public Account(int accountId, String userName, String fullName, String phone, boolean isDeleted) {
        this.accountId = accountId;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.isDeleted = isDeleted;
    }
    
    

    public Account(int accountId, String userName, String passWord, String fullName, String email, String phone, String roleName, boolean isDeleted) {
        this.accountId = accountId;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roleName = roleName;
        this.isDeleted = isDeleted;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
    
}
