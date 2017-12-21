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

@WebServlet(urlPatterns = "/SearchSalesByFlight")
public class SearchSalesByFlight extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            String airlineid = request.getParameter("airlineid");
            String flightno = request.getParameter("flightno");
            TableCreator table = new TableCreator(new String[]{"Customer AccountNo","Reservation no","Reservation Date",
                    "BookingFee","Total Fare"});
            String q = "SELECT DISTINCT r.*\n" +
                    "FROM Reservation r, Includes i\n" +
                    "WHERE r.ResrNo = i.ResrNo AND i.FlightNo =" +flightno+""+
                    " AND i.AirlineID =\""+airlineid+"\"";
            //System.out.println(q);
            ResultSet res = Database.executeQ(q);
            double total=0;if(res.next()== false)
            {

                RequestDispatcher req = request.getRequestDispatcher("SearchSalesPage");
                req.include(request,response);
                out.println("<font color=\"red\">Flight not found !</font><br>");
            }
            else {
                out.println(Header.getManagerHeader());
                res.beforeFirst();
                while (res.next()) {
                    String resno = res.getString("resrno");
                    String resDate = res.getString("resrDate");
                    String bookingfee = res.getString("bookingfee");
                    String totalfare = res.getString("totalFare");
                    String accno = res.getString("accountno");
                    total += Double.parseDouble(totalfare);
                    table.addRow(new String[]{accno, resno, resDate, bookingfee, totalfare});
                }
                table.addRow(new String[]{"", "", "", "Total Sales", Double.toString(total)});
                out.println(table.returnTable());
                HomePage.goBackButton(out, "Search Again");
                out.println("<br><br>");
                out.println(Header.getFooter());
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
