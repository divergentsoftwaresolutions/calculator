package com.andy.calculator;

public class Logger {
	private enum LoggingLevel {
		TRACE(0),
		DEBUG(1),
		INFO(2),
		WARN(3),
		ERROR(4);
		
		protected int levelNum;
		
		private LoggingLevel(int levelNum) {
			this.levelNum = levelNum;
		}
	}
	
	private static Logger instance;
	private LoggingLevel level;
	
	public static Logger newInstance(String level) {
		if (instance == null) {
			instance = new Logger(level);
		}
		return instance;
	}
	
	public Logger(String level) {
		super();
		this.level = LoggingLevel.valueOf(level);
	}

	public void log(String text, LoggingLevel level) {
		if (this.level.levelNum <= level.levelNum) {
			System.out.println(level + ": " + text);
		}
	}
	
	public void trace(String text) {
		log(text, LoggingLevel.TRACE);
	}
	
	public void debug(String text) {
		log(text, LoggingLevel.DEBUG);
	}
	
	public void info(String text) {
		log(text, LoggingLevel.INFO);
	}
	
	public void warn(String text) {
		log(text, LoggingLevel.WARN);
	}
	
	public void error(String text) {
		log(text, LoggingLevel.ERROR);
	}
	
	public static Logger getLogger() {
		if (instance == null) {
			instance = new Logger("DEBUG");
		}
		return instance;
	}
}
