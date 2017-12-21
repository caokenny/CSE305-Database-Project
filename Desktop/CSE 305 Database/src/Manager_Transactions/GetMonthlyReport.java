package Manager_Transactions;

import Database.Database;
import Utils.TableCreator;
import Header.Header;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/GetMonthlyReport")
public class GetMonthlyReport extends HttpServlet {
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
            int month = Integer.parseInt(request.getParameter("month"));
            String q ="select * from reservation where month(resrDate)="+month;
            ResultSet res = Database.executeQ(q);
            TableCreator table = new TableCreator(new String[]{"Customer Acc No","ResrNo","ResrDate","Booking Fee","Total Fare"});
            double totalSales= 0;
            if(res.next()== false)
            {
                RequestDispatcher dis = request.getRequestDispatcher("MonthlySaleReportPage");
                dis.include(request,response);
                out.println("<font style=\"text-align: center;\" color=\"red\">No report for this month ! </font>");

            }
            else {
                out.println(Header.getManagerHeader());
                res.beforeFirst();
                while (res.next()) {

                    String resNo = res.getString("resrNo");
                    String resDate = res.getString("resrDate");
                    String bookingfee = res.getString("bookingfee");
                    String totalfare = res.getString("totalfare");
                    String accno = res.getString("accountno");
                    table.addRow(new String[]{accno, resNo, resDate, bookingfee, totalfare});
                    totalSales += Double.parseDouble(totalfare);
                }
                table.addRow(new String[]{"", "", "", "Total sales", Double.toString(totalSales)});
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
