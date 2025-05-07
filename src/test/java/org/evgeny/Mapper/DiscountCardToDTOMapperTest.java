package org.evgeny.Mapper;

import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.Model.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardToDTOMapperTest {

    DiscountCardToDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = DiscountCardToDTOMapper.getINSTANCE();
    }

    @Test
    void shouldMapDiscountCardToFindByCardDTO() {
        FindByCardDTO map = mapper.map(getDiscountCard());
        assertEquals(getFindByCardDTO(), map);


    }

    private FindByCardDTO getFindByCardDTO() {
        return FindByCardDTO.builder()
                .id(1)
                .code("1000")
                .build();
    }

    private DiscountCard getDiscountCard() {
        return DiscountCard.builder()
                .id(1)
                .code("1000")
                .build();
    }

}