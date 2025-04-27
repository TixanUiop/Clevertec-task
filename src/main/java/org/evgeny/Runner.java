package org.evgeny;

import lombok.extern.slf4j.Slf4j;
import org.evgeny.Model.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

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

    private static final String REGEX_PARAM = "(\\d+)-(\\d+)|card-(\\d+)";
    private static List<Product> products;
    private static List<Long> cards;


    private static Map<Long, Integer> resultProductsHash = new HashMap<>();

    private static Optional<String> card;

    public static void main(String[] args) {

        //String atg = args.toString();
        //System.out.println(atg);
        String test = "2-10 2-15 4-3 card-1233";

        Pattern pattern = Pattern.compile(REGEX_PARAM);
        Matcher matcher = pattern.matcher(test);

        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null) {
                Long productId = Long.parseLong(matcher.group(1));
                Integer quantity = Integer.parseInt(matcher.group(2));
                resultProductsHash.put(productId, quantity);
            }
            else if (matcher.group(3) != null) {
                card = Optional.of(matcher.group(3));
            }
        }
        if (resultProductsHash.isEmpty()) {
            log.info("No products found");
            System.out.println("No products found");
        }


        printReceipt(findProducts());
    }

    private static void printReceipt(Map<Long, Product> products)
    {

        System.out.println("CASH RECEIPT");
        System.out.println("SUPERMARKET 123");
        System.out.println("12, MILKWAY Galaxy/ Earth");
        System.out.println("tel : 123-456-789");
        System.out.println("CASHIER: #1520          DATE: " + LocalDate.now());
        System.out.println("                        TIME: " + LocalTime.now());
        System.out.println("------------------------------------------------------------------------");
        System.out.println(String.format("%-10s %-30s %-10s %-10s", "Кол-во", "Название", "Цена", "Итоговая стоимость"));
        System.out.println("------------------------------------------------------------------------");
        double totalPrice = 0.0;
        for (Map.Entry<Long, Product> entry : products.entrySet()) {

            double itemTotalPrice = entry.getValue().getSalePrice() * entry.getKey();

            System.out.println(String.format("%-10d %-30s %-10.2f %-10.2f",
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getKey() > 5
                            ? entry.getValue().getSalePrice() - (entry.getValue().getSalePrice() / 100) * 10
                            : entry.getValue().getSalePrice(),
                    itemTotalPrice));

            totalPrice += itemTotalPrice;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.println("TOTAL: " + totalPrice);

    }


    private static Map<Long, Product>  findProducts() {
        Map<Long, Product> resultProductsAndQuantity = new HashMap<>();

        products.stream()
                .filter(x -> resultProductsHash.containsKey(x.getId()))
                .forEach(x -> {
                    Integer quantity = resultProductsHash.get(x.getId());
                    resultProductsAndQuantity.put(quantity.longValue(), x);
                });

        return resultProductsAndQuantity;
    }


    static {
        products = new ArrayList<>();
        cards = new ArrayList<>();
        initArrayProducts();
        initArrayCards();
    }

    private static void initArrayCards() {
        cards.add(1234L);
        cards.add(1233L);
        cards.add(3221L);
        cards.add(9999L);
        cards.add(1000L);
    }
    private static void initArrayProducts() {
        products.add(new Product(1L, "Беспроводные наушники с шумоподавлением WH-1000XM4", 320.50, 320.50));
        products.add(new Product(2L, "Умные часы Apple Watch Series 7", 449.99,449.99));
        products.add(new Product(3L, "Ноутбук Apple MacBook Pro 16", 2399.00, 2399.00));
        products.add(new Product(4L, "Смартфон Samsung Galaxy S21", 899.00, 899.00));
        products.add(new Product(5L, "Электрическая зубная щетка Philips Sonicare", 99.99, 99.99));
        products.add(new Product(6L, "Планшет Samsung Galaxy Tab S7", 649.00, 600.00));
        products.add(new Product(7L, "Фотоаппарат Canon EOS 90D", 1299.00,1299.00));
        products.add(new Product(8L, "Игровая консоль PlayStation 5", 499.00, 499.00));
        products.add(new Product(9L, "Кофеварка De'Longhi Magnifica", 599.99,599.99));
        products.add(new Product(10L, "Портативная колонка JBL Charge 5", 149.99, 149.99));
    }
}
