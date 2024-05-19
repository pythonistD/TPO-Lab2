package com.cheboksarov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionSystemTest {
    private final BigDecimal precision = BigDecimal.valueOf(0.0000000001);
    private final Double tolerance = 0.01;

    @ParameterizedTest
    @CsvFileSource(resources = "/functionSystem.csv", numLinesToSkip = 1)
    public void functionSystemBasicTest(double val, double expected){
        Logger logger = Logger.getLogger(FunctionSystem.class.getName());
        FunctionSystem functionSystem = new FunctionSystem();
        BigDecimal x = BigDecimal.valueOf(val);

        BigDecimal res = functionSystem.calculate(x, precision);
        logger.info(String.valueOf(res));
        assertEquals(expected, res.doubleValue(), tolerance);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/functionSystemCallsFirstHalf.csv", numLinesToSkip = 1)
    public void functionSystemFirstHalfCallsTest(double val, int csc_t, int cos_t, int sin_t, int cot_t){
        BigDecimal x = BigDecimal.valueOf(val);

        Csc csc = Mockito.mock(Csc.class);
        Cos cos = Mockito.mock(Cos.class);
        Sin sin = Mockito.mock(Sin.class);
        Cot cot = Mockito.mock(Cot.class);
        Log log3 = Mockito.mock(Log.class);
        Log log2 = Mockito.mock(Log.class);
        Ln ln = Mockito.mock(Ln.class);
        Log log10 = Mockito.mock(Log.class);

        FunctionSystem functionSystem = new FunctionSystem(csc, cos, sin, cot, log3, log2, ln, log10);

        Mockito.when(csc.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(cos.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(sin.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(cot.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(log3.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(log2.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(ln.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(log10.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));

        functionSystem.calculate(x, precision);
        Mockito.verify(sin, Mockito.times(sin_t)).calculate(x, precision);
        Mockito.verify(cos, Mockito.times(cos_t)).calculate(x, precision);
        Mockito.verify(csc, Mockito.times(csc_t)).calculate(x, precision);
        Mockito.verify(cot, Mockito.times(cot_t)).calculate(x, precision);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/functionSystemCallsSecondHalf.csv", numLinesToSkip = 1)
    public void functionSystemSecondHalfCallsTest(double val, int log3_t, int log2_t, int ln_t, int log10_t){
        BigDecimal x = BigDecimal.valueOf(val);

        Csc csc = Mockito.mock(Csc.class);
        Cos cos = Mockito.mock(Cos.class);
        Sin sin = Mockito.mock(Sin.class);
        Cot cot = Mockito.mock(Cot.class);
        Log log3 = Mockito.mock(Log.class);
        Log log2 = Mockito.mock(Log.class);
        Ln ln = Mockito.mock(Ln.class);
        Log log10 = Mockito.mock(Log.class);

        FunctionSystem functionSystem = new FunctionSystem(csc, cos, sin, cot, log3, log2, ln, log10);

        Mockito.when(csc.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(cos.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(sin.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(cot.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(log3.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(log2.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(ln.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));
        Mockito.when(log10.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1));

        functionSystem.calculate(x, precision);
        Mockito.verify(log3, Mockito.times(log3_t)).calculate(x, precision);
        Mockito.verify(log2, Mockito.times(log2_t)).calculate(x, precision);
        Mockito.verify(ln, Mockito.times(ln_t)).calculate(x, precision);
        Mockito.verify(log10, Mockito.times(log10_t)).calculate(x, precision);
    }

    @Test
    public void functionSystemTestFullyIsolated(){
        BigDecimal x = BigDecimal.valueOf(1.0466);

        Csc csc = Mockito.mock(Csc.class);
        Cos cos = Mockito.mock(Cos.class);
        Sin sin = Mockito.mock(Sin.class);
        Cot cot = Mockito.mock(Cot.class);
        Log log3 = Mockito.mock(Log.class);
        Log log2 = Mockito.mock(Log.class);
        Ln ln = Mockito.mock(Ln.class);
        Log log10 = Mockito.mock(Log.class);

        FunctionSystem functionSystem = new FunctionSystem(csc, cos, sin, cot, log3, log2, ln, log10);

        Mockito.when(csc.calculate(x, precision)).thenReturn(BigDecimal.valueOf(1.1551));
        Mockito.when(cos.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.5005));
        Mockito.when(sin.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.8657));
        Mockito.when(cot.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.5781));
        Mockito.when(log3.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.0414));
        Mockito.when(log2.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.0657));
        Mockito.when(ln.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.0455));
        Mockito.when(log10.calculate(x, precision)).thenReturn(BigDecimal.valueOf(0.0197));

        assertEquals(0.00032587, functionSystem.calculate(x, precision).doubleValue(), tolerance);
    }
}
