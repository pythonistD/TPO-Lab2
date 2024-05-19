package com.cheboksarov;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

public class FunctionSystem extends AbstractMathFunction{
    private Csc csc;
    private Cos cos;
    private Sin sin;
    private Cot cot;
    private Log log3;
    private Log log2;
    private Ln ln;
    private Log log10;


    public FunctionSystem(){
        csc = new Csc();
        cos = new Cos();
        sin = new Sin();
        cot = new Cot();
        log3 = new Log(3);
        log2 = new Log(2);
        ln = new Ln();
        log10 = new Log(10);
    }

    public FunctionSystem(Csc csc, Cos cos, Sin sin, Cot cot, Log log3, Log log2, Ln ln, Log log10){
        this.csc = csc;
        this.cos = cos;
        this.sin = sin;
        this.cot = cot;
        this.log3 = log3;
        this.log2 = log2;
        this.ln = ln;
        this.log10 = log10;
    }
    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        validate(x, precision);
        if(x.compareTo(BigDecimal.ZERO) <= 0){
            return (((csc.calculate(x, precision).add(cos.calculate(x, precision))).divide(cos.calculate(x, precision), HALF_UP)).pow(6))
                    .divide(
                            sin.calculate(x, precision).multiply(
                                    (cot.calculate(x, precision).divide(csc.calculate(x, precision), HALF_UP))
                            ), HALF_UP
                    ).setScale(4, HALF_UP);
            /*return (((csc.calculate(x, precision).add(cos.calculate(x, precision)))
                    .divide(cos.calculate(x, precision), 5, HALF_UP)
                    ).pow(3).pow(2))
                    .divide((sin.calculate(x, precision)
                            .multiply((cot.calculate(x, precision).
                                    divide(csc.calculate(x, precision), 5, HALF_UP)))), 5, HALF_UP);*/
        } else {
            return log3.calculate(x, precision).multiply(log2.calculate(x, precision)).multiply(log3.calculate(x, precision))
                    .divide(ln.calculate(x, precision).add(log10.calculate(x, precision)), 4, HALF_UP).subtract(log10.calculate(x, precision)).pow(2).setScale(4, HALF_UP);
        }
    }
}
