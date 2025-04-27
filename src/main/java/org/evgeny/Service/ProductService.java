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
    @Getter
    private final static ProductService INSTANCE = new ProductService();
    private final static ProductDAO productDAO = ProductDAO.getINSTANCE();
    private final ProductDAOToDTOMapper mapper = ProductDAOToDTOMapper.getINSTANCE();

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
