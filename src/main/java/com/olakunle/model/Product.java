package com.olakunle.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UUID.randomUUID();
    @Column(name = "name")
    private String productName;
    private Double price;
    @Column(columnDefinition="TEXT")
    private String description;
}
