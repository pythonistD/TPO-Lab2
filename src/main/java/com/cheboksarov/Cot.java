package com.cheboksarov;

import java.math.BigDecimal;
import java.math.RoundingMode;

//ctg
public class Cot extends AbstractMathFunction{

    private Sin sin;
    private Cos cos;

    public Cot(){
        super();
        sin = new Sin();
        cos = new Cos();
    }
    public Cot(int iterations){
        super();
        sin = new Sin(iterations);
        cos = new Cos(iterations);
    }
    public Cot(Sin sin, Cos cos){
        super();
        this.sin = sin;
        this.cos = cos;
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        super.validate(x, precision);
        BigDecimal cosResult = cos.calculate(x, BigDecimal.valueOf(0.0001));
        BigDecimal sinResult = sin.calculate(x, precision);
        if(sinResult.compareTo(BigDecimal.ZERO) == 0){
            throw new ArithmeticException("sin(x) = 0");
        }
        return cosResult.divide(sinResult, 10, RoundingMode.HALF_UP);
    }
}
