package com.dpensky.desafioanotaai.domain.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dpensky.desafioanotaai.domain.category.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    public Product(ProductDTO productData) {
        this.title = productData.title();
        this.description = productData.description();
        this.ownerId = productData.ownerId();
        this.price = productData.price();
        // this.category = productData.category();
    }
    @Id
    private String id;
    private String title;
    private String description;
    private Category category;
    private Integer price;
    private String ownerId;
}
