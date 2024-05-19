package com.cheboksarov;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogFunctionsTest {
    private final BigDecimal precision = BigDecimal.valueOf(0.000000001);
    private final Double tolerance = 0.0001;
    private final Ln ln = new Ln();

    @Nested
    class LnTest{
        @Test
        public void negativeValueTest(){
            assertThrows(IllegalArgumentException.class, () -> ln.calculate(BigDecimal.valueOf(-1.0), precision));
        }

        @ParameterizedTest
        @ArgumentsSource(ArgumentSource.class)
        public void basicLnTest(List<List<Double>> data){
            for(List<Double> inOut : data){
                assertEquals(inOut.get(1),
                        ln.calculate(BigDecimal.valueOf(inOut.get(0)), precision).doubleValue(), tolerance);
            }

        }

        static class ArgumentSource implements ArgumentsProvider {
            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
                return Stream.of(
                        Arguments.of(
                                List.of(
                                        List.of(2.0, 0.6931),
                                        List.of(3.0, 1.0986),
                                        List.of(1.2, 0.1823),
                                        List.of(0.1, -2.3025),
                                        List.of(0.3, -1.2039),
                                        List.of(0.5, -0.6931)
                        )
                ));
            }
        }
    }

    @Nested
    class LogTest{
        @ParameterizedTest
        @CsvFileSource(resources = "/log10.csv", numLinesToSkip = 1)
        public void log10FullyIsolatedTest(double val, int base, double ln1_r, double ln2_r, double expectedResult){
            Ln ln1 = Mockito.mock(Ln.class);
            Ln ln2 = Mockito.mock(Ln.class);
            Mockito.when(ln1.calculate(BigDecimal.valueOf(val), precision)).thenReturn(BigDecimal.valueOf(ln1_r));
            Mockito.when(ln2.calculate(BigDecimal.valueOf(10), precision)).thenReturn(BigDecimal.valueOf(ln2_r));
            Log log = new Log(base, ln1, ln2);
            assertEquals(expectedResult, log.calculate(BigDecimal.valueOf(val), precision).doubleValue(), tolerance);
        }

        @ParameterizedTest
        @ValueSource(doubles = {1.0, 5.0, 3.0, 4.23, 2.221})
        public void logLnCallsTest(double val){
            Ln ln1 = Mockito.mock(Ln.class);
            Ln ln2 = Mockito.mock(Ln.class);
            Log log3 = new Log(3, ln1, ln2);

            Mockito.when(ln1.calculate(BigDecimal.valueOf(val), precision)).thenReturn(BigDecimal.ONE);
            Mockito.when(ln2.calculate(BigDecimal.valueOf(3), precision)).thenReturn(BigDecimal.ONE);
            log3.calculate(BigDecimal.valueOf(val), precision);

            Mockito.verify(ln1, Mockito.times(1)).calculate(BigDecimal.valueOf(val), precision);
            Mockito.verify(ln2, Mockito.times(1)).calculate(BigDecimal.valueOf(3), precision);

        }


        @ParameterizedTest
        @ArgumentsSource(LogArgumentSource.class)
        public void logBasicTestS(List<List<Double>> data){
            for(List<Double> d: data){
                int base = (int)d.get(1).doubleValue();
                Log log = new Log(base);
                assertEquals(d.get(2), log.calculate(BigDecimal.valueOf(d.get(0)), precision).doubleValue(), tolerance);
            }

        }
    }
    @Test
    public void negativeValueTest(){
        Log log3 = new Log(3);
        assertThrows(IllegalArgumentException.class, () -> log3.calculate(BigDecimal.valueOf(-1.0), precision));
    }
    @Test
    public void negativeBaseTest(){
        assertThrows(IllegalArgumentException.class, () -> new Log(-3));
    }
    static class LogArgumentSource implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(
                            // Base: 10
                            List.of(
                                    // inputValue base expectedResult
                                    List.of(2.0, 10.0, 0.3010),
                                    List.of(3.0, 10.0, 0.4771),
                                    List.of(1.2, 10.0, 0.0791),
                                    List.of(0.1, 10.0, -1.0),
                                    List.of(0.3, 10.0, -0.5228),
                                    List.of(0.5, 10.0, -0.3010)
                            ),
                            // Base: 2
                            List.of(
                                    // inputValue base expectedResult
                                    List.of(2.0, 2.0, 1.0),
                                    List.of(3.0, 2.0, 1.5849),
                                    List.of(1.2, 2.0, 0.2630),
                                    List.of(0.1, 2.0, -3.3219),
                                    List.of(0.3, 2.0, -1.7369),
                                    List.of(0.5, 2.0, -1)
                            )
                    ));
        }
    }
}
