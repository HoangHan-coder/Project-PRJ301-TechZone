/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author letan
 */
public class Product {
    private int productId;
    private String linkImg;
    private String productName;
    private BigDecimal productPrice;
    private Map<String, String> productAttributes;
    private int categoryId;
    private boolean isDeleted;
    private LocalDateTime timeCreate;
    private int quantity;
    private String descriptionProduct;

    public Product() {
    }

    public Product(int productId, String linkImg, String productName, BigDecimal productPrice, Map<String, String> productAttributes, int categoryId, boolean isDeleted, LocalDateTime timeCreate, int quantity,  String descriptionProduct) {
        this.productId = productId;
        this.linkImg = linkImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAttributes = productAttributes;
        this.categoryId = categoryId;
        this.isDeleted = isDeleted;
        this.timeCreate = timeCreate;
        this.quantity = quantity;
        this.descriptionProduct = descriptionProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public Map<String, String> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Map<String, String> productAttributes) {
        this.productAttributes = productAttributes;
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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
