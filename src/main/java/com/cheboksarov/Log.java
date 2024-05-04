package com.cheboksarov;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Log extends AbstractMathFunction{
    private static final Ln ln = new Ln();
    private Integer base;

    public Log(Integer base){
       super();
       this.base = base;
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        return ln.calculate(x, precision)
                .divide(ln.calculate(BigDecimal.valueOf(base), precision)
                        , precision.scale(), HALF_UP);
    }
}
