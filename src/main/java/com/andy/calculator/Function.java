package com.andy.calculator;

import java.util.HashMap;
import java.util.Map;

public class Function {
    private String type;
    private Object arg1;
    private Object arg2;
    private boolean let;
    private String letVarName;
    private static Map<String,Integer> varNameAndValue = new HashMap<>();

    private Logger logger = Logger.getLogger();
    
    public Function(String type) {
        this.type = type;
        this.let = "let".equals(type);
    }

    public void setLetVarName(String letVarName) {
        this.letVarName = letVarName;
    }

    public boolean isLet() {
        return let;
    }

    public boolean isInitializeLetVar() {
        return let && letVarName == null;
    }
    
    public Integer calc() {
        Integer x = arg1 instanceof Integer ? (Integer)arg1 : varNameAndValue.get(arg1);
        Integer y = arg2 instanceof Integer ? (Integer)arg2 : varNameAndValue.get(arg2);
        Integer result;
        
        switch (type) {
            case "add":
                result = x + y;
                break;
            case "mult":
                result = x * y;
                break;
            case "sub":
                result = x - y;
                break;
            case "div":
                result = x / y;
                break;
            default:
                throw new RuntimeException("Unknown arithmetic function " + type);
        }
        
        logger.debug(String.format("Function calc() - %1$s(%2$s,%3$s) %4$s - Result=%5$s", type, arg1, arg2, varNameAndValue, result));
        return result;
    }
    
    public Object getArg1() {
        return arg1;
    }

    public Object getArg2() {
        return arg2;
    }
    
    public void setArg1(Integer arg1) {
        this.arg1 = arg1;
    }

    public void setArg1(String arg) {
        try {
            Integer argValue = Integer.parseInt(arg);
            this.arg1 = argValue;
        } catch (NumberFormatException nfe) {
            if (arg.length() == 1) {
                // let function with a var
                this.arg1 = arg;  
            } else {
                // Expression needs to be calculated.
                Integer argValue = calc();
                this.arg1 = argValue;
            }
        }
    }
    
    public void setArg2(Integer arg2) {
        this.arg2 = arg2;
    }
    
    public void setArg2(String arg) {
        try {
            this.arg2 = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            if (arg.length() == 1) {
                this.arg2 = arg;
            }
        }
    }
    
    public boolean isCalcReady() {
        return !isLet() && arg1 != null && arg2 != null;
    }
    
    public static void addVar(String name, Integer value) {
        varNameAndValue.put(name, value);
    }
}
