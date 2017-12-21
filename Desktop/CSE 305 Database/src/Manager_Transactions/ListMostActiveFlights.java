package Manager_Transactions;

import Database.Database;
import Manager_Transactions.HomePage;
import Utils.ManagerHelper;
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
import java.sql.Struct;

@WebServlet("/ListMostActiveFlights")
public class ListMostActiveFlights extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String allFlights = "select * from mostactive where bitsset>=3 order by daysoperating desc";
            ResultSet result = Database.executeQ(allFlights);
            TableCreator table = new TableCreator(new String[]{"FlightNo","AirlineID","NoOfSeats","Days Of operation"});
            while(result.next()){
                String flightno = result.getString("flightno");
                String airlineid= result.getString("Airlineid");
                String noOfSeats = result.getString("noofseats");
                String days = ManagerHelper.parseDays(result.getString("daysoperating"));
                table.addRow(new String[]{flightno,airlineid,noOfSeats,days});
                }
            PrintWriter out = response.getWriter();
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


