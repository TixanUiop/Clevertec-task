package org.evgeny.DAO;

import org.evgeny.Integration.H2DataBaseCreator;
import org.evgeny.Model.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardDAOTest extends H2DataBaseCreator {

    private DiscountCardDAO discountCardDAO;

    @BeforeEach
    void setUp() throws Exception {
        discountCardDAO = DiscountCardDAO.getINSTANCE();
    }

    @Test
    void findByCardCodeIfCardExists() {
        Optional<DiscountCard> byCardCode = discountCardDAO.findByCardCode("1000");
        assertTrue(byCardCode.isPresent());
        assertEquals(getDiscountCard(), byCardCode.get());
    }

    @Test
    void findByCardCodeIfCardDoesNotExists() {
        Optional<DiscountCard> byCardCode = discountCardDAO.findByCardCode("test");
        assertFalse(byCardCode.isPresent());
    }

    private DiscountCard getDiscountCard() {
        return DiscountCard.builder()
                .id(2)
                .code("1000")
                .build();
    }
}