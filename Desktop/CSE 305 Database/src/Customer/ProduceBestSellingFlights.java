package Customer;

import Database.Database;
import Header.Header;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ProduceBestSellingFlights")
public class ProduceBestSellingFlights extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        out.println(Header.getUserHeader());
        out.println("<h1>Best Selling Flights</h1>");
        out.println("<h3>Ordered from most booked to least</h3>");


        out.println("<style>\n" +
                "table {\n" +
                "    font-family: arial, sans-serif;\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "    border: 1px solid #dddddd;\n" +
                "    text-align: left;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "    background-color: #dddddd;\n" +
                "}\n" +
                "</style>");

        //Search Includes and Leg and all flights and reservations
        String query = "SELECT i.AirlineId, i.FlightNo, l.DepAirportID, l.ArrAirportID,\n" +
                "COUNT(distinct resrNo) AS NumOfReservations\n" +
                "FROM Includes i, Leg l \n" +
                "WHERE i.AirlineId=l.AirlineId AND i.FlightNo=l.FlightNo AND i.LegNo=l.LegNo\n" +
                "GROUP BY AirlineId, FlightNo, DepAirportID, ArrAirportID\n" +
                "ORDER BY NumOfReservations DESC;";

        ResultSet resultSet = Database.executeQ(query);

        //Print a table with the Airline, Departure Destination, Arrival Destination, and Number of reservations made
        String print = "<table width=\"100%\"><tr><th>Airline</th><th>Flight Number</th><th>Flying From</th><th>Flying To</th><th>Number of reservations</th></tr>";
        while (resultSet.next()) {
            print += "<tr>";
            for (int i = 1; i <= 5; i++) {
                print += "<td>" + resultSet.getString(i) + "</td>";
            }
            print += "</tr>";
        }
        print += "</table>";

        out.println(print);
        out.println(Header.getFooter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
