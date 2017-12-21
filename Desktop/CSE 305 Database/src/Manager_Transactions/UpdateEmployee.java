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

@WebServlet(urlPatterns = "/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            PrintWriter out = response.getWriter();
            out.println(Header.getManagerHeader());
            response.setContentType("text/html");
            int id = Integer.parseInt(request.getParameter("id"));
            String firstname = request.getParameter("firstName");
            String lastname = request.getParameter("lastName");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            int zipcode = Integer.parseInt(request.getParameter("zipcode"));
            String phonenum =request.getParameter("phonenum");

            String q = "UPDATE person SET FirstName=\""+firstname+"\",lastname=\""+lastname+"\""+"" +
                    ",address=\""+address+"\", city=\""+city +"\",zipcode="+zipcode+",phone=\""+
                    phonenum+"\" where id="+id;
            Database.executeU(q);
            out.println("<h3>Employee Updated!</h3>");
            HomePage.goBackButton(out);
            out.println(Header.getFooter());

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
