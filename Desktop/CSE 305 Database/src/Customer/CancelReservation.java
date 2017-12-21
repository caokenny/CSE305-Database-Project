package Customer;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/CancelReservation")
public class CancelReservation extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resrNo = request.getParameter("ResNo");
        String airlineID = request.getParameter("AirlineID");
        String flightNo = request.getParameter("FlightNo");
        String flightDate = request.getParameter("FlightDate");
        String resDate = request.getParameter("ResDate");
        String fare = request.getParameter("Fare");
        fare = fare.substring(1);

        String username = (String) request.getSession().getAttribute("loggedInUser");
        ResultSet getAccountNo = Database.executeQ("SELECT AccountNo FROM Customer WHERE Username='" + username + "'");
        getAccountNo.next();
        String accountNo = getAccountNo.getString(1);

        String query = "DELETE FROM Reservation WHERE ResrNo='" + resrNo + "' AND ResrDate='" + resDate + "' AND TotalFare='" +
                fare + "' AND AccountNo='" + accountNo + "'";

        Database.executeU(query);

        RequestDispatcher dispatcher = request.getRequestDispatcher("viewReservations.jsp");
        dispatcher.forward(request, response);

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
