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

@WebServlet(urlPatterns = "/SearchSalesByCustomer")
public class SearchSaleByCustomer extends HttpServlet {
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
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String q ="SELECT IFNULL(r.ResrNo, \"Total\") AS ResrNo, SUM(r.TotalFare)\n" +
                    "FROM Reservation r, Person p, ReservationPassenger rp\n" +
                    "WHERE r.ResrNo = rp.ResrNo AND rp.Id = p.Id AND p.FirstName =\""+firstname
                    +"\"AND p.LastName =\""+lastname+"\"GROUP BY r.ResrNo WITH ROLLUP;";
            ResultSet res  = Database.executeQ(q);
            TableCreator table = new TableCreator(new String [] {"Reservation No","Total Fare"});
            if(res.next()== false)
            {

                RequestDispatcher req = request.getRequestDispatcher("SearchSalesPage");
                req.include(request,response);
                out.println("<font color=\"red\">Customer not found !</font><br>");
            }
            else {
                out.println(Header.getManagerHeader());
                res.beforeFirst();
                while (res.next()) {
                    String resno = res.getString("resrno");
                    String total = res.getString("SUM(r.TotalFare)");
                    table.addRow(new String[]{resno, total});
                }
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
