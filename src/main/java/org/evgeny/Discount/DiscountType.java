package org.evgeny.Discount;

import java.math.BigDecimal;

public enum DiscountType {

    MORE_FIVE_POSITION_ON_SALE(BigDecimal.valueOf(0.10)),;

    final BigDecimal rate;

    DiscountType(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

}