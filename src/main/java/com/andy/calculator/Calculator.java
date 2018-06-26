package com.andy.calculator;

public class Calculator {
	private static Logger logger;
	
	public static void main(String[] args) {
		if (args == null || args.length != 2) {
			System.out.println("ERROR: Please enter one argument expression, and one log level (TRACE,DEBUG,INFO,WARN,ERROR)");
            System.out.println("Example: java com.andy.calculator.Calculator \"add(3,4)\" TRACE");
			return;
		}
		
		logger = Logger.newInstance(args[1]);
		logger.trace("Let the calculations begin");
		logger.info("Expressions=" + args[0]);

		Expression exp = new Expression(args[0]);
		
		logger.trace("Processing expressions...");
		
		Integer result = exp.process();		

		logger.info("Result=" + result);
		logger.trace("Calculations are finished");
	}
}
