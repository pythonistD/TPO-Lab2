package com.cheboksarov;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrigonometricFunctionsTest {
    private final BigDecimal precision = BigDecimal.valueOf(0.0000001);
    private final Double tolerance = 0.1;
    private final Sin sin = new Sin(1000);

    @Nested
    class SinTest {

        @ParameterizedTest
        @CsvFileSource(resources = "/sin.csv", numLinesToSkip = 1)
        public void sinBasicTest(double val, double expectedResult) {
            assertEquals(expectedResult, sin.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

    }
}
