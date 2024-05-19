package com.cheboksarov;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathFunctionTest {

    @ParameterizedTest
    @ArgumentsSource(MathFunctionArgumentProvider.class)
    public void nullArgPrecisionTest(MathFunction mathFunction) {
        BigDecimal val = null;
        BigDecimal precision = null;
        assertThrows(NullPointerException.class, () -> mathFunction.calculate(val, precision));
    }

    @ParameterizedTest
    @ArgumentsSource(illegalPrecisionTestArgumentProvider.class)
    public void illegalPrecisionTest(Double precision, MathFunction mathFunction) {
        BigDecimal val = BigDecimal.valueOf(1.5);
        assertThrows(IllegalArgumentException.class, () -> mathFunction.calculate(val, BigDecimal.valueOf(precision)));
    }

    static class illegalPrecisionTestArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(-0.0001, new Cos()),
                    Arguments.of(-0.0001, new Sin()),
                    Arguments.of(-0.0001, new Cot()),
                    Arguments.of(-0.0001, new Csc()),
                    Arguments.of(-0.0001, new Ln()),
                    Arguments.of(-0.0001, new Log(4)),
                    Arguments.of(-0.0001, new FunctionSystem()),

                    Arguments.of(2.2, new Cos()),
                    Arguments.of(2.2, new Sin()),
                    Arguments.of(2.2, new Cot()),
                    Arguments.of(2.2, new Csc()),
                    Arguments.of(2.2, new Ln()),
                    Arguments.of(2.2, new Log(4)),
                    Arguments.of(2.2, new FunctionSystem())
            );
        }
    }
    static class MathFunctionArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(new Cos()),
                    Arguments.of(new Sin()),
                    Arguments.of(new Cot()),
                    Arguments.of(new Csc()),
                    Arguments.of(new Ln()),
                    Arguments.of(new Log(4)),
                    Arguments.of(new FunctionSystem())
            );
        }
    }
}
