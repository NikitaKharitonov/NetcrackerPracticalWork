package org.tempuri;

import javax.ejb.Local;

@Local
public interface CalculatorIntf {

    double getFirstOperand();

    void setFirstOperand(double firstOperand);

    double getSecondOperand();

    void setSecondOperand(double secondOperand);

    double getResult();

    void setResult(double result);

    double getMemory();

    void setMemory(double memory);

    String getOutput();

    void setOutput(String output);

    void add();

    void subtract();

    void multiply();

    void divide();

    void saveResultToMemory();

    void extractMemoryToFirstOperand();

    void extractMemoryToSecondOperand();

    void clearMemory();

}
