package org.evgeny.Model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Product {
    private Integer id;
    private String description;
    private BigDecimal price;
    private boolean isSale;
    private BigDecimal salePrice;
}
