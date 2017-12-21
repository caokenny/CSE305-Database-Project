package Manager_Transactions;

import Database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/ShowEmployee")
public class ShowEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String q = "SELECT * FROM employee E,Person P WHERE P.id = E.id";
        try
        {
            ResultSet res = Database.executeQ(q);
            PrintWriter out = response.getWriter();

            out.println(
                    "<head> \n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                    "</head>"
            );

            out.println(
                    "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                    "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                    "       <li><a href=\"Manager_index.jsp\">Home</a></li>" +
                    "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Manager</a></li>\n" +
                    "   </ul>"
            );

            out.println("<div class=\"loggedInTabContent\">");

            while(res.next())
            {
                out.println("<b>");
                out.println("Name: " + res.getString("FirstName")
                        + " "+ res.getString("LastName") + "</b><br>" );
                out.println("ID: "+ res.getString("id")+"<br>");
                out.println("Start Date: "+ res.getString("StartDate")+"<br>");
                out.println("Address:" + res.getString("Address")+" "
                        + res.getString("city") +" " +res.getString("zipcode")
                        + "<br>");
                out.println("Phone number: "+ res.getString("phone")+"<br>");
                out.println("HourlyRate: "+res.getString("HourlyRate")+"<br><br>");
            }

            out.println("</div></div>");

            //String referer = request.getHeader("Referer");
            //response.sendRedirect(referer);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
