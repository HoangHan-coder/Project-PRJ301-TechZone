/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author letan
 */
public class AllCategory {
    private String name;
    private int sales;
    private int sumquantity;
    private double allprice;

    public AllCategory() {
    }

    public AllCategory(String name, int sales, int sumquantity, double allprice) {
        this.name = name;
        this.sales = sales;
        this.sumquantity = sumquantity;
        this.allprice = allprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getSumquantity() {
        return sumquantity;
    }

    public void setSumquantity(int sumquantity) {
        this.sumquantity = sumquantity;
    }

    public double getAllprice() {
        return allprice;
    }

    public void setAllprice(double allprice) {
        this.allprice = allprice;
    }

   
    
}
