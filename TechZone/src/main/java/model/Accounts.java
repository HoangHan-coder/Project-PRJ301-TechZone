/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pc
 */
public class Accounts {

    private int id;
    private String userName;
    private String passWord;
    private String fullName;
    private String email;
    private String phone;
    private boolean isDeleted;
    private String roleName;

    public Accounts() {
    }

//    public Accounts(int id, String userName, String passWord, String fullName, String email, String phone) {
//        this.id = id;
//        this.userName = userName;
//        this.passWord = passWord;
//        this.fullName = fullName;
//        this.email = email;
//        this.phone = phone;
//    }

    public Accounts(int id, String userName, String fullName, String email, String phone, String roleName) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roleName = roleName;
    }

    public Accounts(int id, String userName, String passWord, String fullName, String email, String phone, boolean isDeleted, String roleName) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isDeleted = isDeleted;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Accounts{" + "id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + '}';
    }

}
