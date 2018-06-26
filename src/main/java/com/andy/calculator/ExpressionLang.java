package com.andy.calculator;

public class ExpressionLang {
    private String expressions;
    private int index;
    private Function firstFunction;
    private Logger logger = Logger.getLogger();
    
    public enum IndexIncrement {
        BEFORE, AFTER;
    }

    public ExpressionLang(String expressions) {
         String cleanedUp = expressions.replaceAll("\\s", "");
         logger.debug("Calculating " + cleanedUp);
         this.expressions = cleanedUp;
         this.index = 0;
         this.firstFunction = nextFunction(0);
    }

    public Function nextFunction(int startIndex) {
        this.index = expressions.indexOf('(');
        String functionType = expressions.substring(startIndex, index);
        index++;
        return new Function(functionType);
    }

    public Function getFirstFunction() {
        return firstFunction;
    }
    
    public String substring(int startIndex) {
         return expressions.substring(startIndex, index);
    }
    
    public String substring(int startIndex, IndexIncrement indexIncrement) {
        if (indexIncrement == IndexIncrement.BEFORE) {
            index++;
        }
        
        String s = expressions.substring(startIndex, index);
        
        if (indexIncrement == IndexIncrement.AFTER) {
            index++;
        }
        
        return s;
    }
    
    public String substring(int startIndex, String search) {
        updateIndex(startIndex, search);
        return expressions.substring(startIndex, index);
    }
    
    public int incrementIndex() {
        index++;
        return index;
    }

    public int incrementIndexForNextFunction() {
        while (index < expressions.length() && (isFirstArg() || isSecondArg())) {
            index++;
        }
        return index;
    }

    public int updateIndex(int startIndex, String search) {
        index = expressions.indexOf(search, startIndex);
        return index;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public char getChar() {
        return expressions.charAt(index);
    }
    
    public boolean isFunction() {
        return getChar() == '(';
    }
    
    public boolean isFirstArg() {
        return getChar() == ',';
    }
    
    public boolean isSecondArg() {
        return getChar() == ')';
    }
    
    public boolean isIndexMax() {
        return index < expressions.length();
    }
}
