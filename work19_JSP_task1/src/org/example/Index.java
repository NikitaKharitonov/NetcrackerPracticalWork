package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Index extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String operation = request.getParameter("operation");
        String firstOperandString = request.getParameter("firstOperand");
        String secondOperandString = request.getParameter("secondOperand");
        if (firstOperandString.equals("") || secondOperandString.equals(""))
            throw new ServletException("Вы не ввели числа");
        double firstOperand = Double.parseDouble(firstOperandString);
        double secondOperand = Double.parseDouble(secondOperandString);
        double answer = 0;
        switch (operation) {
            case "+":
                answer = firstOperand + secondOperand;
                break;
            case "-":
                answer = firstOperand - secondOperand;
                break;
            case "*":
                answer = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand == 0)
                    throw new ServletException("На ноль делить нельзя");
                answer = firstOperand / secondOperand;
                break;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstOperandString).append(" ").append(operation).append(" ");
        if (secondOperand < 0)
            stringBuilder.append("(").append(secondOperandString).append(")");
        else
            stringBuilder.append(secondOperandString);
        stringBuilder.append(" = ").append(answer);
        request.setAttribute("answer", stringBuilder.toString());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
