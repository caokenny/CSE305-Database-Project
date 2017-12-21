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


@WebServlet(urlPatterns = "/SearchResByFlightNo")
public class SearchResByFlightNo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try{
            PrintWriter out = response.getWriter();
            int flightno = Integer.parseInt(request.getParameter("flightno"));
            String airlineid = request.getParameter("airlineid");
            String q= "select * from includes I, Reservation R where r.resrno = i.resrno AND I.flightno="+flightno+
                    " and airlineid=\""+airlineid+"\"";
            response.setContentType("text/html");
            ResultSet res = Database.executeQ(q);
            out.println("<h3><u>Rerservation info for flightNo: "+ flightno+"</u></h3>");
            TableCreator table = new TableCreator(new String[]{"Reservation Number","Reservation Date","Airline ID","Leg", " Flight Date",
            "RepSSN","Customer AccountNo","Customer Name"/*,"Email"*/});
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
                    //String airlineid= res.getString("airlineid");
                    String reserdate = res.getString("resrDate");
                    String leg = res.getString("legno");
                    String flightdate = res.getString("date");
                    String repssn = res.getString("repssn");
                    String accountNo = res.getString("accountNo");

                    String tempQ = "Select * from customer p, person t where p.id = t.id and accountno="
                            + Integer.parseInt(accountNo);
                    ResultSet tempRes = Database.executeQ(tempQ);
                    tempRes.next();
                    String name = tempRes.getString("firstname") + " " + tempRes.getString("lastname");
                    String email = tempRes.getString("email");

                    table.addRow(new String[]{reserNo, reserdate, airlineid, leg, flightdate, repssn, accountNo, name,/*email*/});
                }
                out.println(table.returnTable());
                out.println("<form action=\"SearchReservation\" method =\"get\">\n" +
                        "         <input type =\"submit\"   value=\"Back\">");
                out.println("<br>");
                out.println(Header.getFooter());
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }
}
