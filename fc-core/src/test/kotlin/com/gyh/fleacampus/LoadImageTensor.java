package com.gyh.fleacampus;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoadImageTensor {
    @Test
    public void test() {
        System.out.println(new BigDecimal(7).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP).toPlainString());
    }
}