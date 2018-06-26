package com.andy.calculator;

import com.andy.calculator.ExpressionLang.IndexIncrement;

public class Expression {
    private Logger logger = Logger.getLogger();
    private ExpressionLang expLang;
    
    public Expression(String expressions) {
        this.expLang = new ExpressionLang(expressions);
    }

    public Integer process() {
        Integer result = process(expLang.getFirstFunction());
        return result;
    }

    private void processLetVar(Function currentFunction) {
        processLetVar(expLang.getIndex(), currentFunction);
    }
    
    private void processLetVar(int startIndex, Function currentFunction) {
        String varName = expLang.substring(startIndex, IndexIncrement.BEFORE);
        currentFunction.setLetVarName(varName);
        startIndex = expLang.incrementIndex();
        String arg = expLang.substring(startIndex, ",");
        Integer varValue;
        try {
            varValue = Integer.parseInt(arg);
            Function.addVar(varName, varValue);
            expLang.incrementIndex();
        } catch (NumberFormatException nfe) {
            expLang.setIndex(startIndex);
            varValue = process(startIndex, currentFunction);
        }

        Function.addVar(varName, varValue);     // caches the var name and value in a static map
        logger.debug("processLetVar() - varName=" + varName + ", varValue=" + varValue);
    }

    private Integer process(Function currentFunction) {
        return process(expLang.getIndex(), currentFunction);
    }
   
    private Integer process(int startIndex, Function currentFunction) {
        // determine the let var and value initially
        if (currentFunction.isInitializeLetVar()) {
            processLetVar(currentFunction);
            startIndex = expLang.getIndex();
        }
        
        do {
            // a function
            if (expLang.isFunction()) {
                String functionType = expLang.substring(startIndex, IndexIncrement.AFTER);
                Function nextFunction = new Function(functionType);
                
                // Don't set the args for a let function, just return the result
                if (currentFunction.isLet()) {
                    return process(nextFunction);
                }
                
                // set the args for the current function (arg1, then arg2)
                if (currentFunction.getArg1() == null) {
                    currentFunction.setArg1(process(nextFunction));
                } else if (currentFunction.getArg2() == null) {
                    currentFunction.setArg2(process(nextFunction));
                }
                
                startIndex = expLang.getIndex();
            } else if (expLang.isFirstArg()) {
                String arg = expLang.substring(startIndex, IndexIncrement.AFTER);
                currentFunction.setArg1(arg);
                startIndex = expLang.getIndex();
            } else if (expLang.isSecondArg()) {
                String arg = expLang.substring(startIndex, IndexIncrement.AFTER);
                currentFunction.setArg2(arg);
                startIndex = expLang.incrementIndexForNextFunction();
            }
            
            if (currentFunction.isCalcReady()) {
                return currentFunction.calc();
            }
            
            expLang.incrementIndex();
        } while(expLang.isIndexMax());

    return -1; // this is an error condition
}}
