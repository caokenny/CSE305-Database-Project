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

@WebServlet(urlPatterns = "/ListCustomerOnFlightPage")
public class ListCustomerOnFlightPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            PrintWriter out = response.getWriter();

            out.println(
                    "<head> \n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                    "</head>"
            );

            out.println("<div style=\"text-align: center; margin: 0 auto;\">\n" +
                    "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                    "       <li><a href=\"Manager_index.jsp\">Home</a></li>" +
                    "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Manager</a></li>\n" +
                    "   </ul>");

            response.setContentType("text/html");
            String q = "Select * from airline";
            ResultSet airline = Database.executeQ(q);
            out.println("<div class=\"loggedInTabContent\"><form action = ListCustomerOnReserved method = get>");
            out.println("Flight Number: <input type =\"text\" name=flightnum>");
            out.println("<select name= airlineid>");
            while(airline.next())
            {
                String id = airline.getString("id");
                String name = airline.getString("name");
                String full = name+"("+id+")";
                out.println("<option value = "+id+">"+full+"</option><br>");
            }
            out.println("</select>");
            out.println("<input type =\"submit\" value= \"select\">");
            out.println("</form>");
            out.println("<br>");
            HomePage.goBackButton(out);
            out.println("</div></div>");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
