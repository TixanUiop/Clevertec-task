package org.evgeny.Service;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.DAO.DiscountCardDAO;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.Mapper.DiscountCardToDTOMapper;
import org.evgeny.Model.DiscountCard;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountCardService {


    private DiscountCardDAO discountCardRepository;
    private DiscountCardToDTOMapper discountCardToDTOMapper;

    public DiscountCardService(DiscountCardDAO discountCardRepository, DiscountCardToDTOMapper discountCardToDTOMapper) {
        this.discountCardRepository = discountCardRepository;
        this.discountCardToDTOMapper = discountCardToDTOMapper;
    }

    public Optional<FindByCardDTO> findByCardCode(String cardCode) {
        Optional<DiscountCard> byCardCode = discountCardRepository.findByCardCode(cardCode);

        if (byCardCode.isPresent()) {
            FindByCardDTO map = discountCardToDTOMapper.map(byCardCode.get());
            return Optional.of(map);
        }
        else {
            log.info("No DiscountCard found for card code in service" + cardCode);
            return Optional.empty();
        }
    }
}
