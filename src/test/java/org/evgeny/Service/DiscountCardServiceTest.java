package org.evgeny.Service;

import org.evgeny.DAO.DiscountCardDAO;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.Mapper.DiscountCardToDTOMapper;
import org.evgeny.Model.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @Mock
    private DiscountCardDAO discountCardDAO;
    @Mock
    private DiscountCardToDTOMapper discountCardToDTOMapper;

    @InjectMocks
    private DiscountCardService discountCardService;

    @Test
    void findByCardCodeIsSuccessful() {
        String cardCode = "12345";
        DiscountCard card = DiscountCard.builder()
                .id(1)
                .code(cardCode)
                .build();

        FindByCardDTO dto = FindByCardDTO.builder()
                .id(1)
                .code(cardCode)
                .build();

        Mockito.when(discountCardDAO.findByCardCode(cardCode)).thenReturn(Optional.of(card));
        Mockito.when(discountCardToDTOMapper.map(card)).thenReturn(dto);


        Optional<FindByCardDTO> result = discountCardService.findByCardCode(cardCode);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());

    }


    @Test
    void findByCardCodeIsIncorrectCard() {
        String cardCode = "test";
        DiscountCard card = DiscountCard.builder()
                .id(1)
                .code(cardCode)
                .build();

        Mockito.when(discountCardDAO.findByCardCode(cardCode)).thenReturn(Optional.empty());

        Optional<FindByCardDTO> result = discountCardService.findByCardCode(cardCode);
        assertFalse(result.isPresent());
    }
}