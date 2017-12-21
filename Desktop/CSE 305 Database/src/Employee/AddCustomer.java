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
import java.time.LocalDateTime;

@WebServlet("/AddCustomer")
public class AddCustomer extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //String Id = request.getParameter("ID");
        //String AccountNo = request.getParameter("AccountNo");

        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");
        String address = request.getParameter("Address");
        String city = request.getParameter("City");
        String state = request.getParameter("State");
        String zipcode = request.getParameter("ZipCode");
        String phone = request.getParameter("Telephone");
        String CreditNo = request.getParameter("CreditCardNum");
        String Email = request.getParameter("Email");
        LocalDateTime now = LocalDateTime.now();
        try{
            String IdTemp = "";
            String getId = "Select MAX(Id) From Person;";
            ResultSet resultId = Database.executeQ(getId);
            resultId.beforeFirst();
            while(resultId.next()){
                IdTemp = resultId.getString("MAX(Id)");
            }
            int Id = Integer.parseInt(IdTemp);
            Id++;
            //System.out.println(Id);

            String accNoTemp = "";
            String getAccNo = "Select MAX(AccountNo) From Customer;";
            ResultSet resultNo = Database.executeQ(getAccNo);
            resultNo.beforeFirst();
            while(resultNo.next()){
                accNoTemp = resultNo.getString("MAX(AccountNo)");
            }
            int AccountNo = Integer.parseInt(accNoTemp);
            AccountNo++;


            String addPerson = "INSERT INTO Person(Id, FirstName, LastName, Address, City, State, ZipCode, Phone) VALUES" +
                    "('"+Id+"', '"+firstName+"', '"+lastName+"', '"+address+"', '"+city+"', '"+state+"', '"+zipcode+"', '"+phone+"');";
            String addCustomer = "INSERT INTO Customer(Id, AccountNo, CreditCardNo, Email, CreationDate, Rating, username, password) VALUES" +
                    "('"+Id+"', '"+AccountNo+"', '"+CreditNo+"', '"+Email+"', '"+now+"', 5 , username=\""+username +"\"" +
                    " ,password = \""+password+"\" )";
            Database.executeU(addPerson);
            Database.executeU(addCustomer);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        PrintWriter out = response.getWriter();
        out.println(Header.getEmployeeHeader());
        response.setContentType("text/html");
        out.println("DONE! <br>");
        HomePage.goBackButton(out);
        out.println(Header.getFooter());

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
