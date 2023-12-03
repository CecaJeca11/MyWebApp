package com.shop.models;

import java.time.LocalDate;

public class Sale {
    private int saleId;
    private int productId;
    private int buyerId;
    private LocalDate saleDate;

    public Sale() {
        // Prazan konstruktor
    }

    public Sale(int saleId, int productId, int buyerId, LocalDate saleDate) {
        this.saleId = saleId;
        this.productId = productId;
        this.buyerId = buyerId;
        this.saleDate = saleDate;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", productId=" + productId +
                ", buyerId=" + buyerId +
                ", saleDate=" + saleDate +
                '}';
    }
}
