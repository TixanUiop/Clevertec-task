package org.evgeny.DAO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.Model.Product;
import org.evgeny.Utill.GetConnection;
import org.evgeny.Exception.ExceptionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDAO implements DAO<Product, Integer> {

    @Getter
    private final static ProductDAO INSTANCE = new ProductDAO();

    private static final String SQL_SELECT_BY_ID = """
               SELECT id, product.description, product.price, product.issale, product.sale_price 
               FROM product
               WHERE id = ?
            """;

    @Override
    public Optional<Product> findById(Integer id) {
        Optional<Product> product = Optional.empty();
        try (Connection connection = GetConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = Optional.of(Product.builder()
                        .id(resultSet.getInt("id"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getBigDecimal("price"))
                        .isSale(resultSet.getBoolean("issale"))
                        .salePrice(resultSet.getBigDecimal("sale_price"))
                        .build());
            }

        }
        catch (SQLException e) {
            log.error("failed attempt to query the database", e.getMessage());
            throw new ExceptionDAO(e.getMessage());
        }
        return product;
    }


    @Override
    public Product create(Product entity) {
        return null;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public void delete(Product entity) {

    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }
}
