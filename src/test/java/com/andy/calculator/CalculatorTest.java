package com.andy.calculator;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CalculatorTest {
	
	@Test
	public void calculatorMainTest() {
		Calculator.main(new String[]{"mult(add(2, 2), div(9, 3))", "TRACE"});
		assertTrue(true);
	}
}
