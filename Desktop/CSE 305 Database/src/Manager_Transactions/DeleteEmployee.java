package Manager_Transactions;

import Database.Database;
import Header.Header;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
        try{
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            String q = "DELETE FROM EMPLOYEE WHERE id="+id;
            Database.executeU(q);
            response.sendRedirect("DeleteEmployeePage");

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
