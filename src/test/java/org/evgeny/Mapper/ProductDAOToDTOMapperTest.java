package org.evgeny.Mapper;

import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOToDTOMapperTest {


    private ProductDAOToDTOMapper mapper;
    @BeforeEach
    void setUp() {
        mapper = ProductDAOToDTOMapper.getINSTANCE();
    }

    @Test
    public void shouldMapProductDAOToDTOMapper() {
        FindByIdProductDTO map = mapper.map(getProduct());
        assertEquals(getFindByIdProductDTO(), map);
    }


    public FindByIdProductDTO getFindByIdProductDTO() {
        return FindByIdProductDTO.builder()
                .id(1)
                .description("description")
                .price(BigDecimal.ONE)
                .isSale(false)
                .salePrice(BigDecimal.ONE)
                .build();
    }

    public Product getProduct() {
        return Product.builder()
                .id(1)
                .description("description")
                .price(BigDecimal.ONE)
                .isSale(false)
                .salePrice(BigDecimal.ONE)
                .build();
    }

}