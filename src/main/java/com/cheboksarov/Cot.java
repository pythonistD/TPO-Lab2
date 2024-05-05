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
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        return cos.calculate(x, precision).divide(sin.calculate(x, precision), 10, RoundingMode.HALF_UP);
    }
}
