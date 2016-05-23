package com.sainsburys.webextractor.model;

import org.json.simple.JSONObject;
import java.math.BigDecimal;

/**
 * Model that holds product information
 */
public class ProductInfo {
    private String title;
    private BigDecimal size;
    private BigDecimal unitPrice;
    private String description;

    public ProductInfo(String title, BigDecimal size, BigDecimal unitPrice, String description) {
        this.title = title;
        this.size = size;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    /**
     *  Returns JSONObject representaion.
     *
     * @return JSONObject containing all the fields.
     */
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("title", title);
        ret.put("size", size + "kb");
        ret.put("unit_price", unitPrice);
        ret.put("description", description);

        return ret;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductInfo{" + "title=" + title + ", size=" + size + ", unitPrice=" + unitPrice + ", description=" + description + '}';
    }

}
