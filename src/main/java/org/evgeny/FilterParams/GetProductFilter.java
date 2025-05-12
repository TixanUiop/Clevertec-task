package org.evgeny.FilterParams;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.Model.ProductItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class GetProductFilter {
    @Getter
    private static final GetProductFilter INSTANCE = new GetProductFilter();

    public Optional<List<ProductItem>> filter(String[] itemIds, String[] quantities) {
        List<ProductItem> result = new ArrayList<>();
        for (int i = 0; i < itemIds.length & i < quantities.length; i++) {
            try {

                int id = Integer.parseInt(itemIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                result.add(ProductItem.builder()
                        .id(id)
                        .quantity(quantity)
                        .build());

            }
            catch (NumberFormatException nfe) {
                log.error("Skipped both: " + itemIds[i] + " and " + quantities[i], nfe.getMessage());
            }
        }
        return Optional.of(result);
    }

}
