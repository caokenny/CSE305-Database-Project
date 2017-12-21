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

@WebServlet(urlPatterns = "/SearchSalesByCity")
public class SearchSalesByCity extends HttpServlet {
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
            String city = request.getParameter("city");
            String q="SELECT DISTINCT r.*\n" +
                    "FROM Reservation r, Includes i, Leg l, Airport a\n" +
                    "WHERE r.ResrNo = i.ResrNo AND i.FlightNo = l.FlightNo AND " +
                    "l.ArrAirportID = a.Id AND a.City =\""+city+"\"AND i.AirlineID = l.AirlineID";
            ResultSet res = Database.executeQ(q);
            TableCreator table = new TableCreator(new String[]{"Customer AccountNo","Reservation no","Reservation Date",
            "BookingFee","Total Fare"});
            double total=0;

            if(res.next()== false)
            {

                RequestDispatcher req = request.getRequestDispatcher("SearchSalesPage");
                req.include(request,response);
                out.println("<font color=\"red\">City not found !</font><br>");
            }
            else {
                out.println(Header.getManagerHeader());
                res.beforeFirst();
                out.print("<h3>Sales Summary for:"+city+"</h3>");
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
                HomePage.goBackButton(out);
                out.println(Header.getFooter());
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
