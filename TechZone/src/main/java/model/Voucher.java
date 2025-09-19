/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author NgKaitou
 */
public class Voucher {
    private String imgPath;
    private String code;
    private String discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String condition;
    private String maxUsage;
    private String minUsage;
    private String currentUsage;

    public Voucher() {
    }

    public Voucher(String imgPath, String code, String discountValue, LocalDateTime startDate, LocalDateTime endDate, String status, String condition, String maxUsage, String currentUsage) {
        this.imgPath = "/" + imgPath;
        this.code = code;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.condition = condition;
        this.currentUsage = currentUsage;
    }
    
    public Voucher(String imgPath, String code, String discountValue, LocalDateTime startDate, LocalDateTime endDate, String status, String condition, String maxUsage, String minUsage, String currentUsage) {
        this.imgPath = "/" + imgPath;
        this.code = code;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.condition = condition;
        this.maxUsage = maxUsage;
        this.minUsage = minUsage;
        this.currentUsage = currentUsage;
    }

    public String getImgPath() {
        return imgPath;
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
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(String maxUsage) {
        this.maxUsage = maxUsage;
    }

    public String getMinUsage() {
        return minUsage;
    }

    public void setMinUsage(String minUsage) {
        this.minUsage = minUsage;
    }

    public String getCurrentUsage() {
        return currentUsage;
    }

    public void setCurrentUsage(String currentUsage) {
        this.currentUsage = currentUsage;
    }
    
    
}
