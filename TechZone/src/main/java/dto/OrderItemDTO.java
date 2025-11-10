/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Data Transfer Object representing a product line within an order context.
 *
 * <p>Encapsulates product snapshot data (name, price, attributes, stock, etc.)
 * alongside order-related fields such as quantity and creation time for
 * rendering order detail pages or APIs.</p>
 *
 * <p>Immutable fields should be treated as snapshots of product state at the
 * time of ordering.</p>
 *
 * @author letan
 */
public class OrderItemDTO {

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
    private int stock;

    /**
     * No-args constructor.
     *
     * <p>Creates an empty DTO to be populated via setters.</p>
     */
    public OrderItemDTO() {
    }

    /**
     * Full constructor for convenient population of all fields.
     *
     * @param productId product identifier
     * @param linkImg relative image path
     * @param productName product display name
     * @param productPrice unit price at snapshot time
     * @param productAttributes arbitrary product attributes (e.g., color, size)
     * @param categoryId category identifier
     * @param isDeleted product deletion flag at snapshot time
     * @param timeCreate creation timestamp
     * @param quantity ordered quantity
     * @param descriptionProduct product short description
     * @param stock available stock at snapshot time
     */
    public OrderItemDTO(int productId, String linkImg, String productName, BigDecimal productPrice, Map<String, String> productAttributes, int categoryId, boolean isDeleted, LocalDateTime timeCreate, int quantity, String descriptionProduct, int stock) {
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
        this.stock = stock;
    }

    /**
     * Gets current stock snapshot.
     *
     * @return stock quantity
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock snapshot.
     *
     * @param stock stock quantity
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets product description.
     *
     * @return description text
     */
    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    /**
     * Sets product description.
     *
     * @param descriptionProduct description text
     */
    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    /**
     * Gets product attributes map.
     *
     * @return key-value attribute pairs
     */
    public Map<String, String> getProductAttributes() {
        return productAttributes;
    }

    /**
     * Sets product attributes map.
     *
     * @param productAttributes key-value attribute pairs
     */
    public void setProductAttributes(Map<String, String> productAttributes) {
        this.productAttributes = productAttributes;
    }

    /**
     * Gets product identifier.
     *
     * @return product id
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets product identifier.
     *
     * @param productId product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets image path with leading slash for web context usage.
     *
     * @return normalized image path
     */
    public String getLinkImg() {
        return "/" + linkImg; // ensure returned path starts with '/' for resource resolution
    }

    /**
     * Sets relative image path.
     *
     * @param linkImg relative path without leading '/'
     */
    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    /**
     * Gets product name snapshot.
     *
     * @return product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets product name snapshot.
     *
     * @param productName product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets unit price snapshot.
     *
     * @return price per unit
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * Sets unit price snapshot.
     *
     * @param productPrice price per unit
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Gets category identifier.
     *
     * @return category id
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category identifier.
     *
     * @param categoryId category id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Indicates if the product was marked deleted at snapshot time.
     *
     * @return true if deleted
     */
    public boolean isIsDeleted() {
        return isDeleted;
    }

    /**
     * Sets deletion flag snapshot.
     *
     * @param isDeleted deletion flag
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Gets creation time snapshot.
     *
     * @return creation timestamp
     */
    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    /**
     * Sets creation time snapshot.
     *
     * @param timeCreate creation timestamp
     */
    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    /**
     * Gets quantity ordered.
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity ordered.
     *
     * @param quantity quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates total price = unit price * quantity.
     *
     * @return total amount, or {@link BigDecimal#ZERO} if price is null
     */
    public BigDecimal getTotal() {
        if (productPrice != null) {
            return productPrice.multiply(BigDecimal.valueOf(quantity)); // compute total cost
        }
        return BigDecimal.ZERO;
    }
}
