package Manager_Transactions;

import Database.Database;
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

@WebServlet("/DetermineMostRevenue")
public class DetermineMostRevenue extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String allFlights = "SELECT c.Id, p.FirstName, p.LastName, cr.TotalRevenue\n" +
                    "FROM Customer c, Person p, CustomerRevenue cr\n" +
                    "WHERE cr.TotalRevenue >= (SELECT MAX(cr1.TotalRevenue) FROM CustomerRevenue cr1)\n" +
                    "AND c.Id = p.Id AND cr.Id = p.Id;\n";
            ResultSet result = Database.executeQ(allFlights);
            TableCreator table = new TableCreator(new String[]{"ID","First Name","Last Name","Total Revenue"});
            while(result.next()){
               String id = result.getString("id");
               String firstname = result.getString("firstname");
               String lastname = result.getString("lastname");
               String rev =  result.getString("totalRevenue");
               table.addRow(new String[]{id,firstname,lastname,rev});
            }
            PrintWriter out = response.getWriter();
            out.println("<br>\n"+table.returnTable());
            out.println("<br>");
            HomePage.goBackButton(out);
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
