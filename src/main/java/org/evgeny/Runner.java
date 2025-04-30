package org.evgeny;

import lombok.extern.slf4j.Slf4j;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Model.Product;
import org.evgeny.Service.DiscountCardService;
import org.evgeny.Service.ProductService;
import org.evgeny.Utill.Printer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Runner {

    //java -cp build/classes/java/main org.evgeny.Runner
    //java CheckRunner 3-1 2-5 5-1 card-1234
    /*должен сформировать и
        вывести в консоль чек содержащий в себе наименование товара с id=3 в
        количестве 1шт, то же самое с id=2 в количестве 5 штук, id=5 - одна штука и т. д. Card-1234 означает, что была предъявлена скидочная карта с номером 1234. Необходимо вывести в консоль сформированный чек (вариант на рисунке),
        содержащий в себе список товаров и их количество с ценой, а также
        рассчитанную сумму с учетом скидки по предъявленной карте (если она есть).
    */

    private static final ProductService productService = ProductService.getINSTANCE();
    private static final DiscountCardService discountCardService = DiscountCardService.getINSTANCE();


    private static final String REGEX_PARAM = "(\\d+)-(\\d+)|card-(\\d{4})$";
    private static Map<Integer, Integer> HashQuantityAndId = new HashMap<>();
    private static Optional<String> card = Optional.empty();

    public static void main(String[] args) {


        //String atg = args.toString();
        //System.out.println(atg);
        String test = "2-10 5-15 4-3 card-1000";
        //List<FindByIdProductDTO> productListResult = new ArrayList<>();
        Optional<FindByCardDTO> cardResult = Optional.empty();
        HashMap<Integer, FindByIdProductDTO> productMapResult = new HashMap<>();
        Pattern pattern = Pattern.compile(REGEX_PARAM);
        Matcher matcher = pattern.matcher(test);

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
