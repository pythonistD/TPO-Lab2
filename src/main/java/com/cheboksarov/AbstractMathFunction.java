package com.cheboksarov;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AbstractMathFunction implements MathFunction{

    private static final int MAX_ITERATIONS = 1000;

    protected int iterations;

    protected AbstractMathFunction() {
        this.iterations = MAX_ITERATIONS;
    }

    protected BigDecimal factorialUsingIteration(int n) throws IllegalArgumentException {
        if (n < 0){
            throw new IllegalArgumentException("n must be positive");
        }
        BigDecimal result = BigDecimal.valueOf(1);
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }

    protected void validate(BigDecimal x, BigDecimal precision) {
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(precision, "precision is null");
        if(precision.compareTo(BigDecimal.ZERO) <= 0 || precision.compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("precision must be positive");
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
