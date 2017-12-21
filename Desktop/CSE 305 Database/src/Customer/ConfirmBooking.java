package Customer;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ConfirmBooking")
public class ConfirmBooking extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String airlineID = request.getParameter("AirlineID");
        String flightNo = request.getParameter("FlightNo");
        String legNo = request.getParameter("LegNo");
        String depart = request.getParameter("DepTime");
        String stringFare = request.getParameter("Fare");
        String returnDate = (String) request.getAttribute("returnDate");
//        String departAirport = request.getParameter("DepAirport");
//        String arrAirport = request.getParameter("ArrAirport");
//        String arrTime = request.getParameter("ArrTime");
        String classType = request.getParameter("Class");
        stringFare = stringFare.substring(1);
        String accountNo = request.getParameter("AccountNo");
        double fare = Double.parseDouble(stringFare);

        String userID = request.getParameter("UserID");

        String seatNo = request.getParameter("SeatNo");

        String meal = request.getParameter("Meal");

        double bookingFee = fare * 0.10;

        double totalFare = fare + bookingFee;
        String reservationNumber;

        ResultSet resrNo = Database.executeQ("SELECT COUNT(*) FROM Reservation");
        resrNo.next();
        if (resrNo.getInt(1) == 0) {
            reservationNumber = "1";
        } else {
            resrNo = Database.executeQ("SELECT MAX(ResrNo) FROM Reservation");

            resrNo.next();

            int x = Integer.parseInt(resrNo.getString(1));
            x++;
            reservationNumber = Integer.toString(x);
        }

        String query = "INSERT INTO Reservation VALUES" +
                "('" + reservationNumber + "', NOW(), '" + bookingFee + "', '" + totalFare + "', '00000000000', '" + accountNo + "')";

        Database.executeU(query);

        query = "INSERT INTO Includes VALUES" +
                "('" + reservationNumber + "', '" + airlineID + "', '" + flightNo + "', '" + legNo + "', '" + depart + "')";

        Database.executeU(query);

        query = "INSERT INTO ReservationPassenger VALUES(" +
                "'" + reservationNumber + "', '" + userID + "', '" + accountNo + "', '" + seatNo + "', '" + classType + "', '" + meal + "')";

        Database.executeU(query);

        PrintWriter out = response.getWriter();

        if (request.getSession().getAttribute("returnDate") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("SearchFlightsRoundtrip");
            dispatcher.forward(request, response);
        } else {
            out.println("<h1>Reservation Successfully Booked!</h1>");
            out.println("<h3><a href=\"viewReservations.jsp\">Click Here To View Your Reservations</a></h3>");
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
