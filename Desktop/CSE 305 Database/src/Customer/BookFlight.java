package Customer;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/BookFlight")
public class BookFlight extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String airlineID = request.getParameter("AirlineID");
        String flightNo = request.getParameter("FlightNo");
        String legNo = request.getParameter("LegNo");
        String depart = request.getParameter("DepTime");
        String stringFare = request.getParameter("Fare");
        String departAirport = request.getParameter("DepAirport");
        String arrAirport = request.getParameter("ArrAirport");
        String arrTime = request.getParameter("ArrTime");
        String classType = request.getParameter("Class");


        //Search Customer for users account number and ID
        ResultSet getAccountNo = Database.executeQ(
                "SELECT AccountNo, Id FROM Customer WHERE Username='" + request.getSession().getAttribute("loggedInUser") + "'"
        );

        getAccountNo.next();
        String accountNo = getAccountNo.getString(1);

        String userID = getAccountNo.getString(2);

        //Search Flight table for the number of seats on the flight
        ResultSet getNoOfSeats = Database.executeQ(
                "SELECT NoOfSeats FROM Flight WHERE AirlineID='" + airlineID + "' AND FlightNo='" + flightNo + "'"
        );

        getNoOfSeats.next();

        int numOfSeats = getNoOfSeats.getInt(1);

        PrintWriter out = response.getWriter();

        out.println("<h1>Please complete the form</h1>");
        out.println("<h3>Choose a seat:</h3>");


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

        //Create a form of radio buttons to allow the user to pick a seat
        String form = "<form action='ConfirmBooking' method='post'>";

        char seatLetter = 65;
        int seatNumber = 1;
        String seatNumString;
        String seatLetterString;

        String seat;

        //Create a view to get the airlineID and flight number of the right flight the user wants to reserve
        String createView = "CREATE OR REPLACE VIEW PassengersOnFlight(SeatNo, ResrNo, AirlineID, FlightNo) AS " +
                "SELECT rp.SeatNo, i.ResrNo, AirlineID, FlightNo FROM Includes i, ReservationPassenger rp " +
                "WHERE i.ResrNo=rp.ResrNo AND i.AirlineID='" + airlineID + "' AND i.FlightNo='" + flightNo + "'";

        Database.executeU(createView);


        //Get the number of passengers on that flight
        ResultSet something = Database.executeQ("SELECT COUNT(*) FROM passengersonflight");

        something.next();

        //If there are no passengers all seats are available
        if (something.getInt(1) == 0) {
            form += "<table><tr>";
            for (int i = 0; i < numOfSeats; i++) {
                if (i % 6 == 0 && i != 0) {
                    seatLetter = 65;
                    seatNumber++;
                    form += "</tr>";
                }
                //Turn the integer into a String
                seatNumString = Integer.toString(seatNumber);
                //Turn the character into a string
                seatLetterString = Character.toString(seatLetter);
                //Combine the number and letter together to form the Seat
                seat = seatNumString + seatLetterString;
                form += "<td><input type='radio' name='SeatNo' value='" + seat + "'>" + seat + "</input></td>";
                seatLetter++;
            }
            form += "</table>";
        } else {
            //Get a resultSet of all passengers on the flight
            ResultSet resultSet = Database.executeQ(
                    "SELECT * FROM passengersonflight"
            );

            boolean seatTaken;
            form += "<table><tr>";
            for (int i = 0; i < numOfSeats; i++) {
                seatTaken = false;
                if (i % 6 == 0 && i != 0) {
                    seatLetter = 65;
                    seatNumber++;
                    form += "</tr>";
                }
                seatNumString = Integer.toString(seatNumber);
                seatLetterString = Character.toString(seatLetter);
                seat = seatNumString + seatLetterString;
                //If the seat is taken already we won't allow the user to choose that seat
                while (resultSet.next()) {
                    if (seat.equals(resultSet.getString(1))) {
                        seatTaken = true;
                        break;
                    }
                }
                if (seatTaken == true) {
                    resultSet.beforeFirst();
                    seatLetter++;
                    continue;
                } else {
                    form += "<td><input type='radio' name='SeatNo' value='" + seat + "'>" + seat + "</input></td>";
                    seatLetter++;
                }
                resultSet.beforeFirst();
            }
            form += "</table>";
        }

        form += "<br/><br/>" +
                "Meal Preference: <input type='text' name='Meal' required/><br/><br/>" +
                "Account Number: <input type='text' name='AccountNo' value='" + accountNo + "' readonly/><br/><br/>" +
                "User ID: <input type='text' name='UserID' value='" + userID + "' readonly/><br/><br/>" +
                "Airline: <input type='text' name='AirlineID' value='" + airlineID + "' readonly/><br/><br/>" +
                "Flight Number: <input type='text' name='FlightNo' value='" + flightNo + "' readonly/><br/><br/>" +
                "Leg Number: <input type='text' name='LegNo' value='" + legNo + "' readonly/><br/><br/>" +
                "Depart From: <input type='text' name='DepAirport' value='" + departAirport + "' readonly/><br/><br/>" +
                "Arrive At: <input type='text' name='ArrAirport' value='" + arrAirport + "' readonly/><br/><br/>" +
                "Arrival Time: <input type='text' name='ArrTime' value='" + arrTime + "' readonly/><br/><br/>" +
                "Departure Time: <input type='text' name='DepTime' value='" + depart + "' readonly/><br/><br/>" +
                "Class: <input type='text' name='Class' value='" + classType + "' readonly/><br/><br/>" +
                "Fare: <input type='text' name='Fare' value='$" + stringFare + "' readonly/><br/><br/>" +
                "<input type='submit' value='Submit'/>";


        out.println(form);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
