package com.cheboksarov;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

public class Sin extends AbstractMathFunction{

    public Sin(){
        super();
    }

    public Sin(int iterations){
        super();
        super.iterations = iterations;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        super.validate(x, precision);
        int iterations = 0;
        BigDecimal curr = new BigDecimal(0);
        while(iterations <= super.iterations){
            curr = curr.add(BigDecimal.valueOf(-1).pow(iterations).multiply(x.pow(2*iterations+1))
                    .divide(factorialUsingIteration(2*iterations+1), precision.scale(), HALF_UP)
            );

            iterations++;
        }
        return curr;
    }
}
