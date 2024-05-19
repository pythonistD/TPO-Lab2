package com.cheboksarov;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Logger;

import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

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
        public void cosBasicTest(double val, double expectedResult, double sinMockResult) {
            assertEquals(expectedResult, cos.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }
        @ParameterizedTest
        @ValueSource(doubles = {3.14159, 6.28318, 1.2343, 5.291})
        public void cosSinCallsTest(double val) {
            final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
            final BigDecimal correctedX = BigDecimal.valueOf(val).remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));

            BigDecimal t =
                    BigDecimalMath.pi(mc)
                            .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                            .subtract(correctedX).setScale(10, HALF_EVEN);

            Sin sin = Mockito.mock(Sin.class);
            Cos cos_c = new Cos(sin);
            cos_c.calculate(BigDecimal.valueOf(val), precision);
            Mockito.verify(sin, Mockito.times(1)).calculate(t, precision);
        }

    }
    @Nested
    class CscTest{
        @ParameterizedTest
        @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1)
        public void cscFullyIsolatedTest(double val, double expectedResult, double sinMockResult) {
            Sin sin = Mockito.mock(Sin.class);
            Mockito.when(sin.calculate(BigDecimal.valueOf(val), BigDecimal.valueOf(0.0001))).thenReturn(BigDecimal.valueOf(sinMockResult));
            Csc csc_c = new Csc(sin);
            assertEquals(expectedResult, csc_c.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
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

        @ParameterizedTest
        @ValueSource(doubles = {3.14159, 6.28318, 1.2343, 5.291})
        public void cscSinCallsTest(double val) {
            Sin sin = Mockito.mock(Sin.class);
            Csc csc_c = new Csc(sin);
            Mockito.when(sin.calculate(BigDecimal.valueOf(val), BigDecimal.valueOf(0.0001))).thenReturn(BigDecimal.ONE);
            csc_c.calculate(BigDecimal.valueOf(val), precision);
            Mockito.verify(sin, Mockito.times(1)).calculate(BigDecimal.valueOf(val), BigDecimal.valueOf(0.0001));
        }
    }
    @Nested
    class CotTest{

        @ParameterizedTest
        @ValueSource(doubles = {0, 3.14159, 6.28318})
        public void sinZeroCotTest(double val) {
            assertThrows(ArithmeticException.class, () -> csc.calculate(BigDecimal.valueOf(val), precision));
        }
        @ParameterizedTest
        @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1)
        public void cotFullyIsolatedTest(double val, double expectedResult,double cosMockResult , double sinMockResult) {
            Sin sin = Mockito.mock(Sin.class);
            Cos cos = Mockito.mock(Cos.class);
            Mockito.when(sin.calculate(BigDecimal.valueOf(val), precision)).thenReturn(BigDecimal.valueOf(sinMockResult));
            Mockito.when(cos.calculate(BigDecimal.valueOf(val), BigDecimal.valueOf(0.0001))).thenReturn(BigDecimal.valueOf(cosMockResult));
            Cot cot_c = new Cot(sin, cos);
            assertEquals(expectedResult, cot_c.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }


        @ParameterizedTest
        @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1)
        public void cotBasicTest(double val, double expectedResult) {
            assertEquals(expectedResult, cot.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

        @ParameterizedTest
        @ValueSource(doubles = {3.14159, 6.28318})
        public void cotSinCosCallsTest(double val) {
            Sin s_sin = Mockito.spy(sin);
            Cos s_cos = Mockito.spy(cos);
            Cot cot = new Cot(s_sin, s_cos);

            cot.calculate(BigDecimal.valueOf(val), precision);
            Mockito.verify(s_sin, Mockito.times(1)).calculate(any(BigDecimal.class), any(BigDecimal.class));
            Mockito.verify(s_cos, Mockito.times(1)).calculate(any(BigDecimal.class), any(BigDecimal.class));
        }


    }
}
