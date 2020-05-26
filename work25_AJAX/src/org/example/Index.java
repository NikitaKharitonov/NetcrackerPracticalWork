package org.example;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Index extends HttpServlet {

    @EJB
    private DatabaseEJB databaseEJB;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ename = request.getParameter("ename");
        if (ename != null) {
            if (ename.equals(""))
                throw new ServletException("Вы не ввели имя");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("");
            List<List<String>> tableData = databaseEJB.getByName(ename);
            out.print("<table><thead><tr>");
            List<String> header = tableData.get(0);
            for (String cell: header) {
                out.print("<th>");
                out.print(cell);
                out.print("</th>");
            }
            out.print("</tr></thead><tbody>");
            for(int i = 1; i < tableData.size(); ++i) {
                out.print("<tr>");
                for(String cell: tableData.get(i)) {
                    out.print("<th>");
                    out.print(cell);
                    out.print("</th>");
                }
                out.print("</tr>");
            }
            out.print("</tbody></table>");
        }
    }
}
