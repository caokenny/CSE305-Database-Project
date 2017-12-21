package Customer;

import Database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@SuppressWarnings("ALL")
@WebServlet("/SearchFlightsRoundtrip")
public class SearchFlightsRoundtrip extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String to = (String) request.getSession().getAttribute("From");
        String from = (String) request.getSession().getAttribute("To");
        String returnDate = (String) request.getSession().getAttribute("returnDate");


        request.getSession().removeAttribute("From");
        request.getSession().removeAttribute("To");
        request.getSession().removeAttribute("returnDate");

        PrintWriter out = response.getWriter();

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

        //Search Leg for flights starting at from location given and arrives at to location given with the given depart date
        ResultSet depSet = Database.executeQ("SELECT * FROM Leg WHERE DepAirportID='" + from + "'");

        ResultSet arrSet = Database.executeQ("SELECT * FROM Leg WHERE ArrAirportID='" + to + "'");

        if (depSet.next()) {
            depSet.previous();

            out.println("<h3>Your reservation will be made upon clicking the Purchase button</h3>");

            String str = "<table width='100%'><tr><th>Select</th><th>AirlineID </th><th>Flight Number </th><th>Leg</th><th>Departing From </th>" +
                    "<th>Arriving At</th><th>Departure Time</th><th>Arrival Time</th><th>No. Of Stops</th><th>Class</th><th>Fare</th></tr>";

            boolean ranAlready = false;
            ResultSet fareSet = null;


            while (depSet.next()) {
                ranAlready = false;
                while (arrSet.next()) {
                    if (depSet.getString("AirlineID").equals(arrSet.getString("AirlineID"))
                            && depSet.getString("FlightNo").equals(arrSet.getString("FlightNo"))) {
                        int numStops = arrSet.getInt("LegNo") - depSet.getInt("LegNo");
                        str += "<tr><form action='BookFlight' method='post'><td><input type='submit' value='Purchase'/></td>";
                        str += "<td><input style='width: 100%' type='text' name='AirlineID' value='" + depSet.getString("AirlineID") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='FlightNo' value='" + depSet.getString("FlightNo") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='LegNo' value='" + depSet.getString("LegNo") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='DepAirport' value='" + depSet.getString("DepAirportID") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='ArrAirport' value='" + arrSet.getString("ArrAirportID") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='DepTime' value='" + depSet.getString("DepTime") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='ArrTime' value='" + arrSet.getString("ArrTime") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='NoStops' value='" + numStops + "' readonly/>";


                        if (!ranAlready) {
                            //Search the Fare table for the price of the flight
                            String getFare = "SELECT Class, Fare FROM Fare WHERE AirlineID='" + depSet.getString("AirlineID") + "' AND" +
                                    " FlightNo='" + depSet.getString("FlightNo") + "'";
                            fareSet = Database.executeQ(getFare);
                            fareSet.last();
                            int fareRows = fareSet.getRow();
                            fareSet.beforeFirst();
                            fareSet.next();
                        }
                        str += "<td><input style='width: 100%' type='text' name='Class' value='" + fareSet.getString("Class") + "' readonly/>" +
                                "<td><input style='width: 100%' type='text' name='Fare' value='" + fareSet.getString("Fare") + "' readonly/></tr>";

                        if (fareSet.next()) {
                            arrSet.previous();
                            continue;
                        }
                    }
                }
                arrSet.beforeFirst();
            }
            str += "</table>";

            out.println(str);
        } else {
            out.println("<h3>No flights were found, please try a different search.</h3>");
            out.println("<h3>Click <a href=\"javascript:void(0)\" onclick=\"window.history.back()\">here</a> to go back.");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
