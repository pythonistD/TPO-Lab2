package com.cheboksarov;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        Map<String, MathFunction> t2func = Map.of(
                "sin", new Sin(),
                "cos", new Cos(),
                "cot", new Cot(),
                "csc", new Csc()
        );
        MathFunction func = t2func.get(args[0]);
        if (func == null) {
            throw new IllegalArgumentException("Unknown function");
        }

        BigDecimal start = BigDecimal.valueOf(Double.parseDouble(args[1]));
        BigDecimal end = BigDecimal.valueOf(Double.parseDouble(args[2]));
        BigDecimal step = BigDecimal.valueOf(Double.parseDouble(args[3]));
        BigDecimal precision = BigDecimal.valueOf(Double.parseDouble(args[4]));

        String fileName = "csv/" + args[0] + ".csv";
        CsvWriter.write(fileName, func, start, end, step, precision);

    }
}