package com.cheboksarov;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.math.RoundingMode.*;


public class Csc extends AbstractMathFunction{
    private Sin sin;
    static Logger logger = Logger.getLogger(AbstractMathFunction.class.getName());

    Csc(){
        super();
        sin = new Sin();
    }

    Csc(int iterations){
        sin = new Sin(iterations);
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);

        BigDecimal sinRes = sin.calculate(x, BigDecimal.valueOf(0.0001));
        if (sinRes.compareTo(BigDecimal.ZERO) == 0){
            throw new ArithmeticException("sin(x) = 0");
        }
        logger.info("Sin: " + sinRes);
        return BigDecimal.ONE.divide(sinRes,precision.scale() ,  DOWN);
    }
}
