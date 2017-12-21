package Employee;

import Database.Database;
import Header.Header;
import Manager_Transactions.HomePage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/DeleteCustomer")
public class DeleteCustomer extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Id = request.getParameter("Id");

        try{
            String deleteCustomer = "DELETE FROM Customer WHERE Id ="+Id+";";
            String checkcust = "select * from customer where id="+Id;
            ResultSet res = Database.executeQ(checkcust);
            PrintWriter out = response.getWriter();

            response.setContentType("text/html");
            if(res.next()== false)
            {
                out.println("<font color=\"red\">Customer does not exist !</font><br>");
                HomePage.goBackButton(out,"make back button");
            }else {
                out.println(Header.getEmployeeHeader());
                Database.executeU(deleteCustomer);
                out.println("<h3>Customer deleted !</h3>");
                HomePage.goBackButton(out,"Delete another customer");
                out.println(Header.getFooter());
            }

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
