package org.evgeny.Model;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductItem {
    private Integer id;
    private Integer quantity;
}
