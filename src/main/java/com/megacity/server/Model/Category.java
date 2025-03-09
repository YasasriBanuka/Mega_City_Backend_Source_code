package com.megacity.server.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document( collection = "category")
public class Category {
    @Id
    private String categoryId;

    private String categoryName;

    private Double pricePerKm;
}
