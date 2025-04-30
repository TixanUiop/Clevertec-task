package org.evgeny.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FindByCardDTO {
    private Integer id;
    private String code;
}
