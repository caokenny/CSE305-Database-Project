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
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/SearchResByName")
public class SearchResByName extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            String firstname = request.getParameter("firstName");
            String lastname = request.getParameter("lastname");

            String q = "select * from reservation r, reservationpassenger rp, person p " +
                    "where r.resrno = rp.resrno AND rp.id = p.id AND p.firstname =\"" + firstname +
                    "\" and p.lastname =\"" + lastname + "\"";
            ResultSet res = Database.executeQ(q);

            TableCreator table = new TableCreator(new String[]{"Customer Name", "Reservation Number", "Reservation Date","SeatNo", "Booking Fee", "Total Fare",
                    "RepSSN", "Customer AccountNo"/*,"Email"*/});
            String fullname = firstname + " " + lastname;

            if(res.next() == false)
            {

                RequestDispatcher req = request.getRequestDispatcher("SearchReservation");
                req.include(request,response);
                out.println("<font color=\"red\">No reservations found !</font><br>");
            }
            else{
                res.beforeFirst();
                out.println(Header.getManagerHeader());
                while (res.next()) {
                    String reserNo = res.getString("ResrNo");
                    String totalfare = res.getString("totalfare");
                    String reserdate = res.getString("resrDate");
                    String bookingfee = res.getString("bookingfee");
                    String repssn = res.getString("repssn");
                    String seatno = res.getString("seatno");
                    String accountNo = "\"" + res.getString("accountNo") + "\"";
                    table.addRow(new String[]{fullname, reserNo, reserdate, seatno, bookingfee, totalfare, repssn, accountNo});
                }
                out.println(table.returnTable());
                HomePage.goBackButton(out,"back");
                out.println(Header.getFooter());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}