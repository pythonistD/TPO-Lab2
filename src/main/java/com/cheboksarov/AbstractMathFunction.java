package com.cheboksarov;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AbstractMathFunction implements MathFunction{

    private static final int MAX_ITERATIONS = 1000;
    protected BigDecimal x;
    protected BigDecimal precision;

    protected int iterations;

    protected AbstractMathFunction(BigDecimal x, BigDecimal precision) {
        this.iterations = MAX_ITERATIONS;
        this.x = x;
        this.precision = precision;
    }

    protected void validate(BigDecimal x, BigDecimal precision) {
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(precision, "precision is null");
        if(precision.compareTo(BigDecimal.ZERO) <= 0 || precision.compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("precision must be positive");
        }
    }

}
