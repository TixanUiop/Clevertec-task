package org.evgeny.Mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Model.Product;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDAOToDTOMapper implements Mapper<Product, FindByIdProductDTO>{

    @Getter
    private static final ProductDAOToDTOMapper INSTANCE = new ProductDAOToDTOMapper();


    @SneakyThrows
    @Override
    public FindByIdProductDTO map(Product from) {
        return FindByIdProductDTO.builder()
                .id(from.getId())
                .price(from.getPrice())
                .description(from.getDescription())
                .isSale(from.isSale())
                .salePrice(from.getSalePrice())
                .build();
    }
}
