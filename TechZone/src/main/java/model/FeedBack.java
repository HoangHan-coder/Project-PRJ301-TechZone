package model;

import java.sql.Timestamp;
import java.util.Date;
import model.Account;
import model.Orders;
import model.Product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author letan
 */
public class FeedBack {

    private Account account;
    Product product;
    private int feedbackId;
    private String subject;
    private String message;
    private int rating;
    private boolean isPublic;
    private String status;
    private Date createAt;
    private Timestamp updateAt;
    private int responseByAccountId;
    private String responseMessage;
    private Date responseAt;

    public FeedBack() {
    }

    public FeedBack(int feedbackId, Product product, String message, int rating, Date createAt, String responseMessage) {
        this.product = product;
        this.feedbackId = feedbackId;
        this.message = message;
        this.rating = rating;
        this.createAt = createAt;
        this.responseMessage = responseMessage;

    }

    public FeedBack(Account account, Product product, int feedbackId, String message, int rating, boolean isPublic, String status, String responseMessage, Date responseAt) {
        this.account = account;
        this.product = product;
        this.feedbackId = feedbackId;
        this.message = message;
        this.rating = rating;
        this.isPublic = isPublic;
        this.status = status;
        this.responseMessage = responseMessage;
        this.responseAt = responseAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public int getResponseByAccountId() {
        return responseByAccountId;
    }

    public void setResponseByAccountId(int responseByAccountId) {
        this.responseByAccountId = responseByAccountId;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Date getResponseAt() {
        return responseAt;
    }

    public void setResponseAt(Date responseAt) {
        this.responseAt = responseAt;
    }

}
