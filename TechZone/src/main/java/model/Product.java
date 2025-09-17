/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author letan
 */
public class Product {
    private int productId;
    private String linkImg;
    private String productName;
    private String productPrice;
    private String productIdDetail;
    private String categoryId;
    private String isDeleted;
    private String timeCreate;

    public Product() {
    }

    public Product(int productId, String linkImg, String productName, String productPrice, String productIdDetail, String categotyId, String isDeleted, String timeCreate) {
        this.productId = productId;
        this.linkImg = linkImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productIdDetail = productIdDetail;
        this.categoryId = categotyId;
        this.isDeleted = isDeleted;
        this.timeCreate = timeCreate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductIdDetail() {
        return productIdDetail;
    }

    public void setProductIdDetail(String productIdDetail) {
        this.productIdDetail = productIdDetail;
    }

    public String getCategotyId() {
        return categoryId;
    }

    public void setCategotyId(String categotyId) {
        this.categoryId = categotyId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }
    
}
