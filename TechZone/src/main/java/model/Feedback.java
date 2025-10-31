package model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private Account account;             
    private Product product;              
    private Order order;                 
    private String subject;
    private String message;
    private Integer rating;
    private boolean isPublic;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Account responseBy;          
    private String responseMessage;
    private Timestamp responseAt;

    public Feedback() {}

    public Feedback(int feedbackId, Account account, Product product, Order order,
                    String subject, String message, Integer rating, boolean isPublic,
                    String status, Timestamp createdAt, Timestamp updatedAt,
                    Account responseBy, String responseMessage, Timestamp responseAt) {
        this.feedbackId = feedbackId;
        this.account = account;
        this.product = product;
        this.order = order;
        this.subject = subject;
        this.message = message;
        this.rating = rating;
        this.isPublic = isPublic;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.responseBy = responseBy;
        this.responseMessage = responseMessage;
        this.responseAt = responseAt;
    }

    // --- GETTER & SETTER ---
    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public Account getResponseBy() { return responseBy; }
    public void setResponseBy(Account responseBy) { this.responseBy = responseBy; }

    public String getResponseMessage() { return responseMessage; }
    public void setResponseMessage(String responseMessage) { this.responseMessage = responseMessage; }

    public Timestamp getResponseAt() { return responseAt; }
    public void setResponseAt(Timestamp responseAt) { this.responseAt = responseAt; }
}
