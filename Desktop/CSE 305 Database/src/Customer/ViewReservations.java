package Customer;

import Database.Database;
import Header.Header;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@SuppressWarnings("Duplicates")
@WebServlet("/ViewReservations")
public class ViewReservations extends HttpServlet {
    @SuppressWarnings("Duplicates")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();

        out.println(Header.getUserHeader());

        //Get currently logged in user's username
        String username = (String) request.getSession().getAttribute("loggedInUser");

        //Using their username get their Account Number
        ResultSet resultSet = Database.executeQ("SELECT AccountNo FROM Customer WHERE Username='" + username + "'");

        //Set cursor to first row
        resultSet.next();

        //Get their account number from the returned Result Set
        String accountNo = resultSet.getString(1);

        //Get all reservations associated with the user's account number
        String query = "SELECT r.ResrNo, r.ResrDate, TotalFare, r.RepSSN FROM Reservation r, Includes i WHERE AccountNo='" + accountNo + "' AND r.ResrNo=i.ResrNo AND " +
                "Date > NOW()";
        ResultSet reservation = Database.executeQ(query);

        //Some css stuff to make table look nice
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


        out.println("<h3>Current Reservations</h3>");
        //Html to create a table with following headers
        String str = "<table width='100%'><tr><th>Cancel</th><th>Reservation #</th><th>Reservation Date</th><th>Fare</th><th>AirlineID</th><th>Flight Number</th>" +
                "<th>From</th><th>To</th><th>Flight Date</th><th>Customer Rep ID</th></tr>";

        //If reservation query returned a non-null Result Set
        if (reservation.next()) {

            //Set cursor to before first row
            reservation.previous();

            //String array of names associated with input style=\"width: 100%\", used to retrieve info about reservation in case of cancellation
            String[] names = new String[8];
            names[0] = "ResNo";
            names[1] = "ResDate";
            names[2] = "Fare";
            names[3] = "AirlineID";
            names[4] = "FlightNo";
            names[5] = "From";
            names[6] = "To";
            names[7] = "FlightDate";

            //Iterate through reservation Result set
            while (reservation.next()) {
                str += "<tr><form action='CancelReservation' method='post'>";
                str += "<td><input style=\"width: 100%\" type='submit' value='Cancel'/></td>";

                for (int i = 1; i <= 3; i++) {
                    //If at the 3rd column, it's the Fare column, print $
                    if (i == 3)
                        str += "<td><input style=\"width: 100%\" name='" + names[i-1] + "' value='$" + reservation.getString(i) + "' readonly/></td>";
                    else //Else print readonly input style=\"width: 100%\" field with reservation info
                        str += "<td><input style=\"width: 100%\" name='" + names[i-1] + "' value='" + reservation.getString(i) + "' readonly/></td>";
                }
                //Search Includes tables for more info associated with the user's reservation number
                ResultSet includes = Database.executeQ("SELECT AirlineID, FlightNo, Date FROM Includes WHERE ResrNo='" + reservation.getString(1) + "'");
                includes.next();
                for (int i = 1; i <= 2; i++) {
                    str += "<td><input style=\"width: 100%\" name'" + names[i+2] + "' value='" + includes.getString(i) + "' readonly/></td>";
                }

                //Search Leg table for Departure and Arrival info
                ResultSet leg = Database.executeQ("SELECT DepAirportID, ArrAirportID FROM Leg WHERE AirlineID" +
                        "='" + includes.getString(1) + "' AND FlightNo='" + includes.getString(2) + "'");
                leg.next();
                for (int i = 1; i <= 2; i++) {
                    str += "<td><input style=\"width: 100%\" name='" + names[i+4] + "' value='" + leg.getString(i) + "' readonly/></td>";
                }

                ResultSet custRepSet = Database.executeQ(
                        "SELECT Id FROM Employee WHERE SSN='" + reservation.getString("RepSSN") + "'"
                );
                custRepSet.next();
                String custRepID = custRepSet.getString(1);

                str += "<td><input style=\"width: 100%\" name='" + names[7] + "' value='" + includes.getString(3) + "' readonly/></td>";
                str += "<td><input style=\"width: 100%\" style=\"width: 100%\" name='CustomerRep' value='" + custRepID + "' readonly/>";
                str += "</form></tr>";
            }

            str += "</table>";

            out.println(str);

        } else {
            //If Result Set was null no reservations were found
            out.println("<h3>No reservations were found</h3>");
        }

        out.println("<hr>");

        query = "SELECT r.ResrNo, r.ResrDate, TotalFare, r.RepSSN FROM Reservation r, Includes i WHERE AccountNo='" + accountNo + "' AND r.ResrNo=i.ResrNo AND " +
                "Date < NOW()";

        reservation = Database.executeQ(query);

        out.println("<h3>Previous Reservations</h3>");

        String previousReservations = "<table width='100%'><tr><th>Reservation #</th><th>Reservation Date</th><th>Fare</th><th>AirlineID</th><th>Flight Number</th>" +
                "<th>From</th><th>To</th><th>Flight Date</th><th>Customer Rep ID</th></tr>";

        //If reservation query returned a non-null Result Set
        //noinspection Duplicates
        if (reservation.next()) {

            //Set cursor to before first row
            reservation.previous();

            //String array of names associated with input style=\"width: 100%\", used to retrieve info about reservation in case of cancellation
            String[] names = new String[8];
            names[0] = "ResNo";
            names[1] = "ResDate";
            names[2] = "Fare";
            names[3] = "AirlineID";
            names[4] = "FlightNo";
            names[5] = "From";
            names[6] = "To";
            names[7] = "FlightDate";

            //Iterate through reservation Result set
            while (reservation.next()) {
                previousReservations += "<tr><form>";

                for (int i = 1; i <= 3; i++) {
                    //If at the 3rd column, it's the Fare column, print $
                    if (i == 3)
                        previousReservations += "<td><input style=\"width: 100%\" name='" + names[i-1] + "' value='$" + reservation.getString(i) + "' readonly/></td>";
                    else //Else print readonly input style=\"width: 100%\" field with reservation info
                        previousReservations += "<td><input style=\"width: 100%\" name='" + names[i-1] + "' value='" + reservation.getString(i) + "' readonly/></td>";
                }
                //Search Includes tables for more info associated with the user's reservation number
                ResultSet includes = Database.executeQ("SELECT AirlineID, FlightNo, Date FROM Includes WHERE ResrNo='" + reservation.getString(1) + "'");
                includes.next();
                for (int i = 1; i <= 2; i++) {
                    previousReservations += "<td><input style=\"width: 100%\" name'" + names[i+2] + "' value='" + includes.getString(i) + "' readonly/></td>";
                }

                //Search Leg table for Departure and Arrival info
                ResultSet leg = Database.executeQ("SELECT DepAirportID, ArrAirportID FROM Leg WHERE AirlineID" +
                        "='" + includes.getString(1) + "' AND FlightNo='" + includes.getString(2) + "'");
                leg.next();
                for (int i = 1; i <= 2; i++) {
                    previousReservations += "<td><input style=\"width: 100%\" name='" + names[i+4] + "' value='" + leg.getString(i) + "' readonly/></td>";
                }


                ResultSet custRepSet = Database.executeQ(
                        "SELECT Id FROM Employee WHERE SSN='" + reservation.getString("RepSSN") + "'"
                );
                custRepSet.next();
                String custRepID = custRepSet.getString(1);

                previousReservations += "<td><input style=\"width: 100%\" name='" + names[7] + "' value='" + includes.getString(3) + "' readonly/></td>";
                previousReservations += "<td><input style=\"width: 100%\" name='CustomerRep' value='" + custRepID + "' readonly/>";
                previousReservations += "</form></tr>";
            }

            previousReservations += "</table>";

            out.println(previousReservations);

        } else {
            //If Result Set was null no reservations were found
            out.println("<h3>No reservations were found</h3>");
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
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
