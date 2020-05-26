package org.example;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Index extends HttpServlet {

    @EJB
    private final DatabaseEJB databaseEJB = new DatabaseEJB();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query != null) {
            switch (query) {
                case "getbyempno":
                    String empno = request.getParameter("empno");
                    if (empno.equals(""))
                        throw new ServletException("Вы не ввели номер");
                    try {
                        databaseEJB.getByEmpno(Integer.parseInt(empno));
                    } catch (NumberFormatException e) {
                        throw new ServletException(e.getMessage());
                    }
                    break;
                case "getbyename":
                    String ename = request.getParameter("ename");
                    if (ename.equals(""))
                        throw new ServletException("Вы не ввели имя");
                    databaseEJB.getByName(ename);
                    break;
                case "getall":
                    databaseEJB.getAll();
                    break;
            }
            request.setAttribute("ejb", databaseEJB);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
