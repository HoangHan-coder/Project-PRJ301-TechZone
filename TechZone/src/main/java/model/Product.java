package model;

import java.sql.Timestamp;

public class Product {
    private int productId;
    private String linkImg;
    private String productName;
    private double productPrice;
    private String productAttributes;
    private int categoryId;
    private int stock;
    private int quantitySold;
    private String descriptionProduct;
    private boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private byte[] rowVersion;

    // Constructors
    public Product() {
    }

    public Product(int productId, String linkImg, String productName, double productPrice, String productAttributes, int categoryId, int stock, int quantitySold, String descriptionProduct, boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, byte[] rowVersion) {
        this.productId = productId;
        this.linkImg = linkImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAttributes = productAttributes;
        this.categoryId = categoryId;
        this.stock = stock;
        this.quantitySold = quantitySold;
        this.descriptionProduct = descriptionProduct;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rowVersion = rowVersion;
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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(String productAttributes) {
        this.productAttributes = productAttributes;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public byte[] getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(byte[] rowVersion) {
        this.rowVersion = rowVersion;
    }

}