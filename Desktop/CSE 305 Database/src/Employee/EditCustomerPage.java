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

@WebServlet("/EditCustomerPage")
public class EditCustomerPage extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountno = request.getParameter("accno");
        String infoQ = "select * from customer c, person p where c.id = p.id and c.accountno="+accountno;
        String checkcusQ ="select * from customer where accountno="+accountno;
        try{
            PrintWriter out = response.getWriter();
            out.println(Header.getEmployeeHeader());
            response.setContentType("text/html");
            ResultSet restemp = Database.executeQ(checkcusQ);
            if (restemp.next() == false)
            {
                //RequestDispatcher dispatcher = request.getRequestDispatcher("");
                out.println("<font color=\"red\">Customer not found !</font><br>");
                HomePage.goBackButton(out);
                out.println(Header.getFooter());
            }
            else {

                ResultSet res = Database.executeQ(infoQ);
                TableCreator table = new TableCreator(new String[]{"First Name", "Last Name", "Address", "City", "State"
                        , "Zipcode", "Phone Number", "Email", "Credit Card Num", "Rating", ""});
                while (res.next()) {
                    String firstname = "<input type = text style =\"width: 100%\" name =firstname value= " + res.getString("firstname") + ">";
                    String lastname = "<input type = text style =\"width: 100%\" name=lastname value= " + res.getString("lastname") + ">";
                    String creditcard = "<input type = text style =\"width: 100%\" name =creditcardnum value= " + res.getString("CreditCardNo") + ">";
                    String email = "<input type = text style =\"width: 100%\" name=email value= " + res.getString("Email") + ">";
                    String rating = "<input type = text style =\"width: 100%\" name= rating value= " + res.getString("rating") + ">";
                    String address = "<input type = text style =\"width: 100%\" name= address value= " + res.getString("address") + ">";
                    String city = "<input type = text style =\"width: 100%\" name= city value= " + res.getString("City") + ">";
                    String state = "<input type = text style =\"width: 100%\" name= state value= " + res.getString("state") + ">";
                    String zipcode = "<input type = text style =\"width: 100%\" name = zipcode value= " + res.getString("zipcode") + ">";
                    String phone = "<input type = text style =\"width: 100%\" name = phone value= " + res.getString("phone") + ">";
                    String submit = "<input type = submit value =submit  changes> ";
                    String id = res.getString("id");
                    table.addFormRow(new String[]{firstname, lastname, address, city, state, zipcode, phone,
                            email, creditcard, rating, submit, "<input type = hidden name=id value=" + id + ">"}, "EditCustomer");
                }

                out.println(table.returnTable());
                out.println("<br>");
                HomePage.goBackButton(out, "need to make a back button that goes back. Im in editcustomerpage");
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
