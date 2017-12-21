package Employee;

import Database.Database;
import Header.Header;
import Manager_Transactions.HomePage;
import Utils.TableCreator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ProductFlightSuggestions")

public class ProductFlightSuggestions extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String finalRes = "";
        try{
            String accNo = request.getParameter("AccNo");
            String flightSuggestions = "SELECT * FROM FlightReservation FR\n" +
                    "WHERE NOT EXISTS (\n" +
                    "       SELECT * FROM Reservation R, Includes I \n" +
                    "\n" +
                    "       WHERE R.ResrNo = I.ResrNo AND FR.AirlineID = I.AirlineID\n" +
                    "\n" +
                    "       AND FR.FlightNo = I.FlightNo AND R.AccountNo =" +accNo+
                    ")";
            ResultSet result = Database.executeQ(flightSuggestions);
            TableCreator table = new TableCreator(new String[]{"Airline Id", "Flight No","Reservation Count"});
            while(result.next()){
                String id = result.getString("airlineid");
                String flightno= result.getString("flightno");
                String num = result.getString("resrCount");
                table.addRow(new String[]{id,flightno,num});
            }
            PrintWriter out = response.getWriter();

            out.println(Header.getEmployeeHeader());
            response.setContentType("text/html");
            out.println(table.returnTable());
            out.println("<br>");
            HomePage.goBackButton(out);
            out.println(Header.getFooter());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
