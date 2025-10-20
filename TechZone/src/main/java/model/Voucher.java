/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author NgKaitou
 */
public class Voucher {

    private int voucherId;
    private String imgPath;
    private String code;
    private BigDecimal discountValue;
    private String discountType;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;
    private BigDecimal minOrderValue;
    private int maxUsage;
    private int currentUsage;

    public Voucher() {
    }

    public Voucher(int voucherId, String imgPath, String code, BigDecimal discountValue, String discountType, Timestamp startDate, Timestamp endDate, String status, BigDecimal minOrderValue, int maxUsage, int currentUsage) {
        this.voucherId = voucherId;
        this.imgPath = imgPath;
        this.code = code;
        this.discountValue = discountValue;
        this.discountType = discountType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.minOrderValue = minOrderValue;
        this.maxUsage = maxUsage;
        this.currentUsage = currentUsage;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getImgPath() {
        return "/" + imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscountValue() {
        if (this.discountType.equals("PERCENT")) {
            return discountValue + " %";
        } else {
            return discountValue + " VND";
        }
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(BigDecimal MinOrderValue) {
        this.minOrderValue = MinOrderValue;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }

    public int getCurrentUsage() {
        return currentUsage;
    }

    public void setCurrentUsage(int currentUsage) {
        this.currentUsage = currentUsage;
    }
    
    
}
    