package servlets;

import jms.Producer;
import jms.ProducerIntf;

import javax.ejb.EJB;

public class Index extends javax.servlet.http.HttpServlet {
    @EJB
    private ProducerIntf producer;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String message = request.getParameter("message");
        if (message != null)
            producer.produceMessage(message);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


}
