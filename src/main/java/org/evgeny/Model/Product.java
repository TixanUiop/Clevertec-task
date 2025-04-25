package org.evgeny.Model;


import lombok.*;


@AllArgsConstructor
@Value
@Builder
public class Product {
    private Long id;
    private String name;
    private double price;
    private double salePrice;
}
