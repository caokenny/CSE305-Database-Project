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

@WebServlet(urlPatterns = "/ListFlightByAirportPage")
public class ListFlightByAirportPage extends HttpServlet {
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

            String q = "select * from airport";
            ResultSet res = Database.executeQ(q);
            response.setContentType("text/html");
            out.println("<div class=\"loggedInTabContent\"><form action = ListFlightInAirport method = get>");
            out.println("Airport name:");
            out.println("<select name= airport>");
            while(res.next())
            {
                String id = res.getString("id");
                String name = res.getString("name");
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
