package org.evgeny.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.DAO.DiscountCardDAO;
import org.evgeny.DAO.ProductDAO;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.FilterParams.GetProductFilter;
import org.evgeny.Mapper.DiscountCardToDTOMapper;
import org.evgeny.Mapper.ProductDAOToDTOMapper;
import org.evgeny.Model.ProductItem;
import org.evgeny.Service.DiscountCardService;
import org.evgeny.Service.ProductService;
import org.evgeny.Utill.PathJspConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//http://localhost:8080/check?itemId=1&quantity=2&itemId=3&quantity=5&itemId=7&quantity=1&card=1000

@Slf4j
@WebServlet("/check")
public class CheckServlet extends HttpServlet {

    private final GetProductFilter getProductFilter = GetProductFilter.getINSTANCE();
    private final DiscountCardService discountCardService = new DiscountCardService(
            DiscountCardDAO.getINSTANCE(),
            DiscountCardToDTOMapper.getINSTANCE()
    );
    private final ProductService productService = new ProductService(
            ProductDAO.getINSTANCE(),
            ProductDAOToDTOMapper.getINSTANCE()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<Integer, FindByIdProductDTO> productMapResult = new HashMap<>();
        String[] itemIds = req.getParameterValues("itemId");
        String[] quantities = req.getParameterValues("quantity");
        if (itemIds != null) {
            Optional<List<ProductItem>> filter = getProductFilter.filter(itemIds, quantities);

            String card = new String();

            if (req.getParameter("card") != null) {
                card = req.getParameter("card");
            }

            if (filter.isPresent()) {
                filter.get().forEach(product -> {
                    FindByIdProductDTO dto = productService.findById(product.getId());
                    productMapResult.put(product.getQuantity(), dto);
                });
                req.setAttribute("productMapResult", productMapResult);

                if (!card.isEmpty()) {
                    Optional<FindByCardDTO> byCardCode = discountCardService.findByCardCode(card);
                    if (byCardCode.isPresent()) {
                        req.setAttribute("card", true);
                    }
                    else {
                        req.setAttribute("card", false);
                    }
                }
            }
            else {
                log.info("No products found");
                req.setAttribute("noProductsFound", "Не удалось найти товары по запросу");
            }
        }

        req.getRequestDispatcher(PathJspConverter.getJspPath("check")).forward(req, resp);
    }
}
