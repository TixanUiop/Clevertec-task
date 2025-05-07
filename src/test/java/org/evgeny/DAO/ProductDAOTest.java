package org.evgeny.DAO;

import lombok.SneakyThrows;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Integration.H2DataBaseCreator;
import org.evgeny.Model.Product;
import org.evgeny.Utill.GetConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOTest extends H2DataBaseCreator {
    private final String CREATE_PRODUCT_SQL = """
        INSERT INTO product
        VALUES
        (100, 'test', 5.000, false, 5.000);
    """;
    private final String DELETE_PRODUCT_SQL = """
        DELETE FROM product WHERE id = 100
    """;
    ProductDAO productDAO;



    @BeforeEach
    void setUp() {
        productDAO = ProductDAO.getINSTANCE();
        CreateProduct();
    }
    @AfterEach
    void tearDown() {
        DeleteProduct();
    }


    @Test
    void findByIdIfProductExists() {

        Optional<Product> byId = productDAO.findById(100);
        assertTrue(byId.isPresent());
        assertEquals(0, byId.get().getPrice().compareTo(getProduct().getPrice()));
        assertEquals(0, byId.get().getSalePrice().compareTo(getProduct().getSalePrice()));
        assertEquals(getProduct().getId(), byId.get().getId());
        assertEquals(getProduct().isSale(), byId.get().isSale());

    }

    @Test
    void findByIdIfProductDoesNotExists() {

        assertTrue(DeleteProductBeforeCallFindByIdIfProductDoesNotExists());

        Optional<Product> byId = productDAO.findById(100);
        assertFalse(byId.isPresent());

    }


    @SneakyThrows
    private void CreateProduct() {
        try (Connection connection = GetConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_PRODUCT_SQL);
        }
    }
    @SneakyThrows
    private void DeleteProduct() {
        try (Connection connection = GetConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DELETE_PRODUCT_SQL);
        }
    }

    @SneakyThrows
    private boolean DeleteProductBeforeCallFindByIdIfProductDoesNotExists() {
        try (Connection connection = GetConnection.getConnection();
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(DELETE_PRODUCT_SQL);
            return rowsAffected > 0;
        }
    }

    public Product getProduct() {
        return Product.builder()
                .id(100)
                .description("test")
                .price(BigDecimal.valueOf(5.000))
                .isSale(false)
                .salePrice(BigDecimal.valueOf(5.000))
                .build();
    }
}