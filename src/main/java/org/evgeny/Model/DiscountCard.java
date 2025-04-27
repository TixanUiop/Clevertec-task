package org.evgeny.Model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DiscountCard {
    private Integer id;
    private String code;
}
