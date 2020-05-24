package org.tempuri;

import javax.ejb.*;

@Stateful
public class Calculator implements CalculatorIntf {

    private double firstOperand;
    private double secondOperand;
    private double result;
    private double memory;
    private String output;

    public double getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(double firstOperand) {
        this.firstOperand = firstOperand;
    }

    public double getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(double secondOperand) {
        this.secondOperand = secondOperand;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void add() {
        result = firstOperand + secondOperand;
    }

    public void subtract() {
        result = firstOperand - secondOperand;
    }

    public void multiply() {
        result = firstOperand * secondOperand;
    }

    public void divide() {
        result = firstOperand / secondOperand;
    }

    public void saveResultToMemory() {
        memory = result;
    }

    public void extractMemoryToFirstOperand() {
        firstOperand = memory;
    }

    public void extractMemoryToSecondOperand() {
        secondOperand = memory;
    }

    public void clearMemory() {
        memory = 0;
    }
}
