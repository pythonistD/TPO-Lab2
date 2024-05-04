package com.cheboksarov;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Ln extends AbstractMathFunction{

    public Ln(Integer iteration) {
        super();
        super.iterations = iteration;
    }

    public Ln() {
        super();
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        BigDecimal prev;
        BigDecimal curr = BigDecimal.ZERO;
        int iteration = 1;
        if(x.compareTo(BigDecimal.ONE) == 0){
            return BigDecimal.ZERO;
        }
        if(x.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("x must be positive");
        }
        super.validate(x, precision);

        if (x.compareTo(BigDecimal.ZERO) > 0 && x.compareTo(BigDecimal.valueOf(2)) < 0) {
             do{
                prev = curr;
                curr = curr.add(BigDecimal.valueOf(-1).pow(iteration+1)
                        .multiply(x.subtract(BigDecimal.ONE).pow(iteration))
                        .divide(BigDecimal.valueOf(iteration), precision.scale(), HALF_UP));

                iteration++;
            }while (iteration < super.iterations);
        } else if(x.compareTo(BigDecimal.valueOf(2)) >= 0) {
            do{
                prev = curr;
                curr = curr.add(BigDecimal.valueOf(-1).pow(iteration)
                        .multiply(BigDecimal.ONE.divide(x,precision.scale(), HALF_UP).subtract(BigDecimal.ONE).pow(iteration))
                        .divide(BigDecimal.valueOf(iteration), precision.scale(), HALF_UP));
                iteration++;
            }while (iteration < super.iterations);

        }
        return curr;
    }
}
