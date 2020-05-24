package org.tempuri;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Index extends HttpServlet {
    @EJB
    private final CalculatorIntf calculator = new Calculator();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String operation = request.getParameter("operation");
        String firstOperandString = request.getParameter("firstOperand");
        String secondOperandString = request.getParameter("secondOperand");
        if (firstOperandString.equals("") || secondOperandString.equals(""))
            throw new ServletException("Вы не ввели числа");
        double firstOperand = Double.parseDouble(firstOperandString);
        double secondOperand = Double.parseDouble(secondOperandString);
        calculator.setFirstOperand(firstOperand);
        calculator.setSecondOperand(secondOperand);
        switch (operation) {
            case "+":
                calculator.add();
                break;
            case "-":
                calculator.subtract();
                break;
            case "*":
                calculator.multiply();
                break;
            case "/":
                if (secondOperand == 0)
                    throw new ServletException("На ноль делить нельзя");
                calculator.divide();
                break;
            case "MSR":
                calculator.saveResultToMemory();
                break;
            case "M1":
                calculator.extractMemoryToFirstOperand();
                break;
            case "M2":
                calculator.extractMemoryToSecondOperand();
                break;
            case "MC":
                calculator.clearMemory();
                break;
        }
        if (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(firstOperandString).append(" ").append(operation).append(" ");
            if (secondOperand < 0)
                stringBuilder.append("(").append(secondOperandString).append(")");
            else
                stringBuilder.append(secondOperandString);
            stringBuilder.append(" = ").append(calculator.getResult());
            calculator.setOutput(stringBuilder.toString());
        }
        request.setAttribute("calculator", calculator);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("calculator", calculator);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
