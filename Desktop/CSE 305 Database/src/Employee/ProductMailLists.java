package Employee;

import Database.Database;
import Header.Header;
import Manager_Transactions.HomePage;
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

@WebServlet("/ProductMailLists")
public class ProductMailLists extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String finalRes = "";
        try{
            String produceMail ="select * from person p, customer c where c.id =p.id";

            ResultSet result = Database.executeQ(produceMail);
            result.beforeFirst();
            TableCreator table = new TableCreator(new String[] {"Name","Email","Address"});
            while(result.next()){
                String name ="";
                name += result.getString("firstname")+ " "+ result.getString("lastname");
                String email = result.getString("email");
                String address= result.getString("address")+", "+result.getString("city")
                        +", "+ result.getString("state");
                table.addRow(new String [] {name,email,address});
            }
            PrintWriter out = response.getWriter();
            out.println(Header.getEmployeeHeader());

            out.println(table.returnTable());
            out.println("<br>");
            HomePage.goBackButton(out);
            out.println(Header.getFooter());
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
