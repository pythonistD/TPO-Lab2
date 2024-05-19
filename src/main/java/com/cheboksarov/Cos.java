package com.cheboksarov;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

public class Cos extends AbstractMathFunction{
    private final Sin sin;
    static ArrayList<Double> values = new ArrayList<>();

    public Cos(){
       super();
       sin = new Sin();
    }
    public Cos(int iterations){
        super();
        sin = new Sin(iterations);
    }

    public Cos(Sin sin){
        super();
        this.sin = sin;
    }
    protected BigDecimal invokeSin(BigDecimal x, BigDecimal precision) {
        return sin.calculate(x, precision);
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        final BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));

        if (correctedX.compareTo(ZERO) == 0) {
            return ONE;
        }
        BigDecimal t =
                BigDecimalMath.pi(mc)
                        .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                        .subtract(correctedX).setScale(10, HALF_EVEN);

        values.add(t.doubleValue());

        /*final BigDecimal result =
                sin.calculate(
                        BigDecimalMath.pi(mc)
                                .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                                .subtract(correctedX),
                        precision);
        return result.setScale(precision.scale(), HALF_EVEN);*/
        return invokeSin(t, precision);
    }
}
