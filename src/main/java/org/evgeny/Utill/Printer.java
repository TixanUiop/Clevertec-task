package org.evgeny.Utill;


import lombok.experimental.UtilityClass;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.DTO.FindByIdProductDTO;
import org.evgeny.Discount.DiscountType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@UtilityClass
public class Printer {

    private static final String STORE_NAME = "ОАО ЧТУП \"Иванович\"";
    private static final String STORE_PHONE = "+375 (29) 123-23-78";
    private static final DecimalFormat df = new DecimalFormat("#0.00");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void printReceipt(HashMap<Integer, FindByIdProductDTO> productMapResult, Optional<FindByCardDTO> card) {

        BigDecimal finalTotalPriceInReceipt = BigDecimal.ZERO;
        BigDecimal finalPriceDiscountSale = BigDecimal.ZERO;

        System.out.println("\n========== Чек ==========");
        System.out.println(STORE_NAME);
        System.out.println("Телефон: " + STORE_PHONE);
        System.out.println("Дата: " + LocalDate.now());
        System.out.println("Время: " + LocalTime.now().format(timeFormatter));
        System.out.println("==========================\n");

        System.out.printf("%-8s %-25s %-10s %-20s%n", "Кол-во", "Продукт", "Цена", "Сумма");

        for (Map.Entry<Integer, FindByIdProductDTO> entry : productMapResult.entrySet()) {
            Integer quantity = entry.getKey();
            FindByIdProductDTO product = entry.getValue();

            BigDecimal price = product.getSalePrice() != null ? product.getSalePrice() : product.getPrice();
            BigDecimal total = BigDecimal.valueOf(quantity).multiply(price);

            if (product.getSalePrice() != null && quantity > 5 && card.isPresent()) {
                BigDecimal discount = total.multiply(DiscountType.MORE_FIVE_POSITION_ON_SALE.getRate());
                finalPriceDiscountSale = finalPriceDiscountSale.add(discount);
                total = total.subtract(discount);
            }

            finalTotalPriceInReceipt = finalTotalPriceInReceipt.add(total);

            System.out.printf("%-8d %-25s %-10s %-20s%n",
                    quantity,
                    product.getDescription(),
                    df.format(price),
                    df.format(total)
            );
        }

        System.out.println("\n==========================");
        System.out.println("Итого к оплате : " + df.format(finalTotalPriceInReceipt));
        System.out.println("Карта: " + (card.isPresent() ? "предъявлена" : "не предъявлена"));
        System.out.println("Скидка: -" + df.format(finalPriceDiscountSale));
        System.out.println("==========================");

    }

}
