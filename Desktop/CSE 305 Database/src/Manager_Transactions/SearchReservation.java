package Manager_Transactions;

import Header.Header;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/SearchReservation")
public class SearchReservation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        try{
            PrintWriter out = response.getWriter();
            out.println(Header.getManagerHeader());
            response.setContentType("text/html");
            out.println(
                    "\n" +
                    "    <form action=\"SearchResByFlightNo\" method=\"get\">\n" +
                    "        <h3><u>Search by FlightNo</u></h3>\n" +
                    "        Enter Flight No: <input type = \"text\" name=\"flightno\">\n" +
                    "        Enter Airline ID:<input type =\"text\" name=\"airlineid\">\n"+
                    "        <input type =\"Submit\" value =\"Search\">\n" +
                    "    </form>\n" +
                    "\n" +
                    "    <form action =\"SearchResByName\" method=\"get\">\n" +
                    "        <h3><u>Search By Customer Name</u></h3>\n" +
                    "        First Name: <input type =\"text\" name=\"firstName\">\n" +
                    "        Last Name: <input type =\"text\" name=\"lastname\">\n" +
                    "        <input type = \"submit\" value =\"Search\">\n" +
                    "    </form>\n" +
                    "\n");
            HomePage.HomePage(out);
            out.println(Header.getFooter());

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
