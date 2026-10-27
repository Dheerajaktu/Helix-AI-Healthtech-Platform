package com.healthcare.helix.experimental.test;

import com.healthcare.helix.experimental.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void addTest(){

        Calculator calculator = new Calculator();
        int actualResult = calculator.add(10,20);

        assertEquals(30, actualResult);

    }

}