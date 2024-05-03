package com.cheboksarov;

import java.math.BigDecimal;

public class Ln extends AbstractMathFunction{

    public Ln(Integer iteration, BigDecimal x, BigDecimal precision) {
        super(x, precision);
        super.iterations = iteration;
    }

    public Ln(BigDecimal x, BigDecimal precision) {
        super(x, precision);
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        return null;
    }
}
