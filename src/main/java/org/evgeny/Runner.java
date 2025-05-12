package org.evgeny;

import lombok.extern.slf4j.Slf4j;
import org.evgeny.DAO.DiscountCardDAO;
import org.evgeny.DAO.ProductDAO;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Mapper.DiscountCardToDTOMapper;
import org.evgeny.Mapper.ProductDAOToDTOMapper;
import org.evgeny.Service.DiscountCardService;
import org.evgeny.Service.ProductService;
import org.evgeny.Utill.Printer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Runner {

    private static final ProductService productService = new ProductService(
            ProductDAO.getINSTANCE(),
            ProductDAOToDTOMapper.getINSTANCE()
    );

    private static final DiscountCardService discountCardService = new DiscountCardService(
            DiscountCardDAO.getINSTANCE(),
            DiscountCardToDTOMapper.getINSTANCE()
    );
    private static final String REGEX_PARAM = "(\\d+)-(\\d+)|card-(\\d{4})$";
    private static Map<Integer, Integer> HashQuantityAndId = new HashMap<>();
    private static Optional<String> card = Optional.empty();


    public static void main(String[] args) {

        String atg = String.join(" ", args);
        System.out.println(atg);
        Optional<FindByCardDTO> cardResult = Optional.empty();
        HashMap<Integer, FindByIdProductDTO> productMapResult = new HashMap<>();
        Pattern pattern = Pattern.compile(REGEX_PARAM);
        Matcher matcher = pattern.matcher(atg);

        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null) {
                Integer productId = Integer.parseInt(matcher.group(1));
                Integer quantity = Integer.parseInt(matcher.group(2));

                HashQuantityAndId.put(productId, quantity);
            }
            else if (matcher.group(3) != null) {
                card = Optional.of(matcher.group(3));
            }
        }
        if (HashQuantityAndId.isEmpty()) {
            log.info("No products found");
            System.out.println("No products found");
        }
        else {

            HashQuantityAndId.entrySet().stream()
                .forEach(entry -> {
                    FindByIdProductDTO dto = productService.findById(entry.getKey());
                    productMapResult.put(entry.getValue(), dto);
                });

            if (card.isPresent()) {
                cardResult = discountCardService.findByCardCode(card.get());
            }
        }
        Printer.printReceipt(productMapResult, cardResult);
    }
}
