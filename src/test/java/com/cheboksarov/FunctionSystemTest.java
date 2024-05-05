package com.cheboksarov;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionSystemTest {
    private final BigDecimal precision = BigDecimal.valueOf(0.0000001);
    private final Double tolerance = 0.00001;

    @Test
    public void functionSystemBasicTest(){
        Logger logger = Logger.getLogger(FunctionSystem.class.getName());
        FunctionSystem functionSystem = new FunctionSystem();
        BigDecimal x = BigDecimal.valueOf(-0.5);

        BigDecimal res = functionSystem.calculate(x, precision);
        logger.info(String.valueOf(res));

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
