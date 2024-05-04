package com.cheboksarov;

import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        Double d = 10.0;
        Integer base = d.intValue();
        Log log = new Log(base);
    }
}