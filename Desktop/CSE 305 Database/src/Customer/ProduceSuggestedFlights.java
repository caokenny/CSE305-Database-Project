package Customer;

import Database.Database;
import Header.Header;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ProduceSuggestedFlights")
public class ProduceSuggestedFlights extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = (String) request.getSession().getAttribute("loggedInUser");
        String getAccountNo = "SELECT AccountNo FROM Customer WHERE Username='" + username + "'";
        ResultSet accountRS = Database.executeQ(getAccountNo);
        accountRS.next();
        String accountNo = accountRS.getString(1);

        PrintWriter out = response.getWriter();
        out.println(Header.getUserHeader());
        out.println("<h1>Suggested Flights</h1>");

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

        String createView = "CREATE OR REPLACE VIEW FlightReservation(AirlineID, FlightNo, ResrCount)\n" +
                "AS SELECT I.AirlineID, I.FlightNo, COUNT(DISTINCT I.ResrNo)\n" +
                "FROM Includes I\n" +
                "GROUP BY I.AirlineID, I.FlightNo;";

        Database.executeU(createView);

        String query = "SELECT * FROM FlightReservation FR\n" +
                "WHERE NOT EXISTS (\n" +
                "SELECT * FROM Reservation R, Includes I\n" +
                "WHERE R.ResrNo=I.ResrNo AND FR.AirlineID = I.AirlineID\n" +
                "AND FR.FlightNo=I.FlightNo AND R.AccountNo ='" + accountNo + "'\n" +
                ")\n" +
                "ORDER BY FR.ResrCount DESC;";

        ResultSet resultSet = Database.executeQ(query);

        String print = "<table width=\"100%\"><tr><th>Airline</th><th>Flight Number</th><th>Number of reservations</th></tr>";

        while (resultSet.next()) {
            print += "<tr>";
            for (int i = 1; i <= 3; i++) {
                print += "<td>" + resultSet.getString(i) + "</td>";
            }
            print += "</tr>";
        }

        print += "</table>";

        out.println(print);
        out.println(Header.getFooter());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
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
