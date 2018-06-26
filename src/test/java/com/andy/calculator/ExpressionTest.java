package com.andy.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExpressionTest {
	private Logger logger = Logger.newInstance("DEBUG");
    
    private Integer runTest(String functionAndExpressions) {
        logger.debug("Test: " + functionAndExpressions);
        Expression exp = new Expression(functionAndExpressions);
        Integer result = exp.process();
        return result;
    }
	
	@Test
	public void multAddDivTest() {
		Integer result = runTest("mult(add(2, 2), div(9, 3))");
		assertEquals(12, result.intValue());
	}
	
	@Test
	public void simpleAddTest() {
		Integer result = runTest("add(1, 2)");
		assertEquals(3, result.intValue());
	}
	
	@Test
	public void complexArithmeticTest() {
        Integer result = runTest("add(mult(add(5,2), div(100,10)), 10)");
		assertEquals(80, result.intValue());
	}
    
    @Test
    public void complexArithmeticTest2() {
        Integer result = runTest("add(mult(5,2), 10)");
        assertEquals(20, result.intValue());
    }
    
    @Test
    public void complexArithmeticTest3() {
        Integer result = runTest("add(mult(add(5,2), 3), 10)");
        assertEquals(31, result.intValue());
    }
	
	@Test
	public void simpleLetTest() {
        Integer result = runTest("let(a, 5, add(a, a))");
		assertEquals(10, result.intValue());
	}
	
	@Test
	public void moreComplicatedLetTest() {
	    Integer result = runTest("let(a, 5, let(b, mult(a, 10), add(b, a)))");
	    // a = 5, b = 50
		assertEquals(55, result.intValue());
	}
	
	@Test
	public void complexLetTest1() {
        Integer result = runTest("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))");
		assertEquals(40, result.intValue());
		// a = let(b, 10, add(b, b)) => a = (b = 10)
	}
    
    @Test
    public void complexLetTest2() {
        Integer result = runTest("add(101, let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))))");
        assertEquals(141, result.intValue());
    }
    
    @Test
    public void complexLetTest3() {
        Integer result = runTest("sub(141, add(101, let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))))))");
        assertEquals(0, result.intValue());
    }
    
    @Test
    public void complexLetTest4() {
        Integer result = runTest("sub(add(101, let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))))), 141)");
        assertEquals(0, result.intValue());
    }

}

