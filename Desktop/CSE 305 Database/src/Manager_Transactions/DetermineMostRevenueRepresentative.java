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

@WebServlet(urlPatterns = "/DetermineMostRevenueRepresentative")
public class DetermineMostRevenueRepresentative extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String allFlights = "SELECT e.Id, p.FirstName, p.LastName, e.TotalRevenue\n" +
                    "FROM EmployeeTotalRevenue e, Person p\n" +
                    "WHERE e.TotalRevenue >= (SELECT MAX(e1.TotalRevenue) FROM EmployeeTotalRevenue e1)\n" +
                    "AND e.Id = p.Id;\n";
            ResultSet result = Database.executeQ(allFlights);
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            TableCreator table = new TableCreator(new String[]{"Id","Name","Total Revenue"});
            while(result.next()){
                String id = result.getString("id");
                String name = result.getString("firstname")+" "+ result.getString("lastName");
                String total = result.getString("TotalRevenue");
                table.addRow(new String[]{id,name,total});
            }
            out.println(table.returnTable());
            HomePage.HomePage(out);
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
