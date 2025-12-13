package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
@CompoundIndexes({
        @CompoundIndex(name = "text_index", def = "{ 'name':'text', 'description':'text'}")
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    private String id;
    @TextIndexed
    private String name;
    @TextIndexed
    private String description;
    private String image;
    private double price;
    private String currency = "INR";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
