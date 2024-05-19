package com.cheboksarov;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Log extends AbstractMathFunction{
    //private static final Ln ln = new Ln();
    private final Integer base;
    private final Ln ln1;
    private final Ln ln2;
    public Log(Integer base){
       super();
       this.base = checkBase(base);
       this.ln1 = new Ln();
       this.ln2 = new Ln();
    }
    public Log(Integer base, Ln ln1, Ln ln2){
        super();
        this.base = checkBase(base);
        this.ln1 = ln1;
        this.ln2 = ln2;
    }

    private Integer checkBase(Integer base) {
        if (base == null || base <= 0) {
            throw new IllegalArgumentException("Base must be greater than 0");
        } else {
            return base;
        }
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        super.validate(x, precision);
        return ln1.calculate(x, precision)
                .divide(ln2.calculate(BigDecimal.valueOf(base), precision)
                        , precision.scale(), HALF_UP);
    }
}
