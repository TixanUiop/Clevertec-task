package org.evgeny.Service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.DAO.ProductDAO;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Exception.ExceptionDAO;
import org.evgeny.Exception.ExceptionService;
import org.evgeny.Mapper.Mapper;
import org.evgeny.Mapper.ProductDAOToDTOMapper;
import org.evgeny.Model.Product;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {

    private ProductDAO productDAO;
    private ProductDAOToDTOMapper mapper;

    public ProductService(ProductDAO productDAO, ProductDAOToDTOMapper mapper) {
        this.productDAO = productDAO;
        this.mapper = mapper;
    }
    public FindByIdProductDTO findById(Integer id) {
        try {
            Optional<Product> product = productDAO.findById(id);
            if (product.isPresent()) {
                return mapper.map(product.get());
            }
            else {
                log.error("Product not found");
                throw new ExceptionService("Product with id " + id + " not found");
            }
        }
        catch (ExceptionDAO e) {
            log.error("Failed to retrieve product by id: {}", id, e);
            throw new ExceptionService(e.getMessage());
        }
    }
}
