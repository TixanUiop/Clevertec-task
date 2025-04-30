package org.evgeny.Mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.evgeny.DTO.FindByCardDTO;
import org.evgeny.Model.DiscountCard;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountCardToDTOMapper implements Mapper<DiscountCard, FindByCardDTO>{

    @Getter
    private static final DiscountCardToDTOMapper INSTANCE = new DiscountCardToDTOMapper();


    @Override
    @SneakyThrows
    public FindByCardDTO map(DiscountCard from) {
        return FindByCardDTO.builder()
                .id(from.getId())
                .code(from.getCode())
                .build();
    }


}
