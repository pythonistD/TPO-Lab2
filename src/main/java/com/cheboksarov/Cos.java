package com.cheboksarov;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

public class Cos extends AbstractMathFunction{
    private Sin sin;

    public Cos(){
       super();
       sin = new Sin();
    }
    public Cos(int iterations){
        super();
        sin = new Sin(iterations);
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        final BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));

        if (correctedX.compareTo(ZERO) == 0) {
            return ONE;
        }

        final BigDecimal result =
                sin.calculate(
                        BigDecimalMath.pi(mc)
                                .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                                .subtract(correctedX),
                        precision);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
