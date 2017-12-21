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
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ListCustomerOnReserved")
public class ListCustomerOnReserved extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flightNo = request.getParameter("flightnum");
        String airlineid = request.getParameter("airlineid");

        try{
            String allCustomer = "SELECT DISTINCT p.Id, p.FirstName, p.LastName, rp.SeatNo\n" +
                    "FROM Person p, ReservationPassenger rp, Includes i\n" +
                    "WHERE i.FlightNo = '"+flightNo+"' AND i.ResrNo = rp.ResrNo AND rp.SeatNo IS NOT NULL AND rp.Id = p.Id\n" +
                    "AND i.airlineid=\""+airlineid+"\"";

            ResultSet result = Database.executeQ(allCustomer);
            response.setContentType("text/html");
            TableCreator table = new TableCreator(new String[]{"ID","First Name", "Last Name","SeatNo"});
            PrintWriter out = response.getWriter();

            if(result.next() == false)
            {
                RequestDispatcher req = request.getRequestDispatcher("ListCustomerOnFlightPage");
                req.include(request,response);
                out.println("<font color=\"red\">No customer/Flight dont exist !</font><br>");
            }
            else {
                out.println(Header.getManagerHeader());
                result.beforeFirst();
                out.println("<h3> Customers on Flight "+ flightNo+"</h3>");
                while (result.next()) {
                    String id = result.getString("id");
                    String first = result.getString("firstname");
                    String last = result.getString("lastname");
                    String seatNo = result.getString("seatno");
                    table.addRow(new String[]{id, first, last, seatNo});
                }
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
