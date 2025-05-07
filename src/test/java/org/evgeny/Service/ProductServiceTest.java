package org.evgeny.Service;

import org.evgeny.DAO.ProductDAO;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Exception.ExceptionDAO;
import org.evgeny.Exception.ExceptionService;
import org.evgeny.Mapper.ProductDAOToDTOMapper;
import org.evgeny.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;
    @Mock
    private ProductDAOToDTOMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void findByIdIsSuccessful() {
        Optional<Product> product = getOptionalProduct();
        FindByIdProductDTO findByIdProductDTO = getFindByIdProductDTO();

        Mockito.when(productDAO.findById(product.get().getId())).thenReturn(product);
        Mockito.when(mapper.map(product.get())).thenReturn(findByIdProductDTO);

        FindByIdProductDTO expected = getFindByIdProductDTO();
        FindByIdProductDTO result = productService.findById(1);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void findByIdThrowsExceptionWhenProductNotFound() {
        Optional<Product> product = getOptionalProduct();

        Mockito.when(productDAO.findById(product.get().getId())).thenReturn(Optional.empty());

        ExceptionService exceptionService = assertThrows(ExceptionService.class, () -> productService.findById(1));
        assertEquals("Product with id 1 not found", exceptionService.getMessage());
    }

    @Test
    void findByIdThrowsExceptionWhenDAOFails() {
        Mockito.when(productDAO.findById(1)).thenThrow(new ExceptionDAO("DB error"));

        ExceptionService exceptionService = assertThrows(ExceptionService.class, () -> productService.findById(1));
        assertEquals("DB error", exceptionService.getMessage());
    }


    private Optional<Product> getOptionalProduct() {
        return Optional.of(Product.builder()
            .id(1)
            .description("test")
            .price(BigDecimal.ONE)
            .isSale(false)
            .salePrice(BigDecimal.ONE)
            .build());
    }

    private FindByIdProductDTO getFindByIdProductDTO() {
        return FindByIdProductDTO.builder()
                .id(1)
                .description("test")
                .price(BigDecimal.ONE)
                .isSale(false)
                .salePrice(BigDecimal.ONE)
                .build();
    }
}