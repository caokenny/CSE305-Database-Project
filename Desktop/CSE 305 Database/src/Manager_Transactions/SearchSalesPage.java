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
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/SearchSalesPage")
public class SearchSalesPage extends HttpServlet {
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
            out.println("<form action=\"SearchSalesByCity\" method=\"get\">\n" +
                    "    <b><u>Search Sales By City</u></b><br><br>\n" +
                    "    City:\n" +
                    "    <input type =\"text\" name=\"city\">\n" +
                    "    <input type=\"submit\" value=\"search\">\n" +
                    "</form>\n" +
                    "<form action=\"SearchSalesByCustomer\" method=\"get\">\n" +
                    "    <b><u>Search Sales By Customer</u></b><br><br>\n" +
                    "    First Name:\n" +
                    "    <input type=\"text\" name=\"firstname\">\n" +
                    "    Last Name\n" +
                    "    <input type = \"text\" name=\"lastname\">\n" +
                    "    <input type=\"submit\" value=\"search\">\n" +
                    "</form>\n" +
                    "<form action=\"SearchSalesByFlight\" method=\"get\">\n" +
                    "    <b><u>Search Sales By Flight</u></b><br><br>\n" +
                    "    Flight No:\n" +
                    "    <input type=\"text\"name=\"flightno\">\n") ;
                    String q = "Select * from airline";
                    ResultSet airline = Database.executeQ(q);
                    out.println("<select name= airlineid>");
                    while(airline.next())
                    {
                        String id = airline.getString("id");
                        String name = airline.getString("name");
                        String full = name+"("+id+")";
                        out.println("<option value = "+id+">"+full+"</option><br>");
                    }
                    out.println("</select>");
                    //"    Airline Id: <input type=\"text\" name=\"airlineid\">\n" +
                    out.println("    <input type=\"submit\" value=\"search\">\n" +
                    "</form>");
                    out.println("<br>");
                    HomePage.goBackButton(out);
                    out.println(Header.getFooter());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
