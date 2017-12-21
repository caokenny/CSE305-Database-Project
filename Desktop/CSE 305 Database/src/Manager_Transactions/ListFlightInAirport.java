package Manager_Transactions;

import Database.Database;
import Header.Header;
import Utils.TableCreator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ListFlightInAirport")
public class ListFlightInAirport extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String AirportID = request.getParameter("airport");

        try{
            String allFlights = "SELECT DISTINCT f.*\n" +
                    "FROM Flight f, Leg l\n" +
                    "WHERE (l.DepAirportID = '"+AirportID+"' OR l.ArrAirportID = '"+AirportID+"') AND f.FlightNo = l.FlightNo AND f.AirlineID = l.AirlineID;\n";
            ResultSet result = Database.executeQ(allFlights);
            TableCreator table = new TableCreator(new String[]{"Airline ID","Flight No","No of Seats"});
            PrintWriter out = response.getWriter();
            if(result.next() == false)
            {

                RequestDispatcher req = request.getRequestDispatcher("ListFlightByAirportPage");
                req.include(request,response);
                out.println("<font color=\"red\"> No flights at this airport !</font><br>");
            }
            else {
                result.beforeFirst();
                while (result.next()) {
                    String id = result.getString("airlineid");
                    String flightno = result.getString("flightno");
                    String seatnum = result.getString("noofseats");
                    table.addRow(new String[]{id, flightno, seatnum});
                }
                out.println(Header.getManagerHeader());
                out.println(table.returnTable());
                out.println("<br>");
                HomePage.goBackButton(out);
                out.println(Header.getFooter());
            }
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
