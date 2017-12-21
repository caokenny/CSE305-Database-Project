package Employee;

import Database.Database;
import Header.Header;
import Manager_Transactions.HomePage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/EditCustomer")
public class EditCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request ,HttpServletResponse response)
    {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String creditcard = request.getParameter("creditcardnum");
        String email = request.getParameter("email");
        String rating = request.getParameter("rating");
        String address =request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipcode = request.getParameter("zipcode");
        String phone = request.getParameter("phone");
        String id = request.getParameter("id");
        String updatePersonq ="Update person set firstname=\""+firstname+"\",lastname= \""+ lastname + "\"," +"address=\""
        +address+"\", city= \""+ city+"\", state=\""+state+"\", zipcode= "+zipcode+",phone=\""+phone+"\" WHERE id="+id;
        String updateCustomerq="update customer set creditcardno="+creditcard+", email = \"" +email+"\", rating="+rating
                +" where id="+id;
        System.out.println(updatePersonq);
        System.out.println(updateCustomerq);
        try{
            Database.executeU(updatePersonq);
            Database.executeU(updateCustomerq);
            PrintWriter out = response.getWriter();
            out.println(Header.getEmployeeHeader());
            response.setContentType("text/html");
            out.println("<h3>Customer Updated!</h3>");
            HomePage.goBackButton(out,"Back");
            out.println(Header.getFooter());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
