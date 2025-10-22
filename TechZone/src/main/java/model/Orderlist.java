package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author letan
 */
public class Orderlist {
    private int orderid;
    private String ordercode;
    private String fullname;
    private double totalamount;
    private String payment;
    private String status;

    public Orderlist() {
    }

    public Orderlist(int orderid, String ordercode, String fullname, double totalamount, String payment, String status) {
        this.orderid = orderid;
        this.ordercode = ordercode;
        this.fullname = fullname;
        this.totalamount = totalamount;
        this.payment = payment;
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
         
}
