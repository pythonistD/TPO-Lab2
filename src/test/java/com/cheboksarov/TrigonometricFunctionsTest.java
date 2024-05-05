package com.cheboksarov;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrigonometricFunctionsTest {
    private final BigDecimal precision = BigDecimal.valueOf(0.0000001);
    private final Double tolerance = 0.1;
    private final Sin sin = new Sin(1000);
    private final Cos cos = new Cos();
    private final Csc csc = new Csc(10000);
    private final Cot cot = new Cot();

    @Nested
    class SinTest {

        @ParameterizedTest
        @CsvFileSource(resources = "/sin.csv", numLinesToSkip = 1)
        public void sinBasicTest(double val, double expectedResult) {
            assertEquals(expectedResult, sin.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

    }
    @Nested
    class CosTest{

        @ParameterizedTest
        @CsvFileSource(resources = "/cos.csv", numLinesToSkip = 1)
        public void cosFullyIsolatedTest(double val, double expectedResult, double sinMockResult) {
            Sin sin = Mockito.mock(Sin.class);
            Mockito.when(sin.calculate(BigDecimal.valueOf(val), precision)).thenReturn(BigDecimal.valueOf(sinMockResult));
            assertEquals(expectedResult, cos.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/cos.csv", numLinesToSkip = 1)
        public void cosBasicTest(double val, double expectedResult, double sinMockResult) {
            assertEquals(expectedResult, cos.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }
    }
    @Nested
    class CscTest{
        @ParameterizedTest
        @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1)
        public void cscFullyIsolatedTest(double val, double expectedResult, double sinMockResult) {
            Sin sin = Mockito.mock(Sin.class);
            Mockito.when(sin.calculate(BigDecimal.valueOf(val), precision)).thenReturn(BigDecimal.valueOf(sinMockResult));
            assertEquals(expectedResult, csc.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1)
        public void cscBasicTest(double val, double expectedResult, double sinMockResult) {
            assertEquals(expectedResult, csc.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

        @ParameterizedTest
        @ValueSource(doubles = {0, 3.14159, 6.28318})
        public void cscZeroSinTest(double val) {
            assertThrows(ArithmeticException.class, () -> csc.calculate(BigDecimal.valueOf(val), precision));
        }
    }
    @Nested
    class CotTest{

        @ParameterizedTest
        @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1)
        public void cotBasicTest(double val, double expectedResult) {
            assertEquals(expectedResult, cot.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }
    }
}
