package Manager_Transactions;

import Database.Database;
import Utils.TableCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/UpdateEmployeePage")
public class UpdateEmployeePage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            String id = request.getParameter("id");
            TableCreator table = new TableCreator(new String[]{"First Name","Last Name", "Address",
            "City","State","Zip Code","Phone Number"," "});

            String q = "Select * From Person p, Employee E where p.id = e.id and p.id =\""+id +"\"";
            ResultSet res = Database.executeQ(q);
            res.next();
            String FirstName = "<input type =\"text\" name =\"firstName\" value=\""+ res.getString("firstName")+"\">";
            String lastName = "<input type =\"text\" name =\"lastName\" value=\""+ res.getString("lastName")+"\">";
            String address = "<input type =\"text\" name =\"address\" value=\""+res.getString("Address")+"\">";
            String city =  "<input type =\"text\" name =\"city\" value=\""+res.getString("city")+"\">";
            String state = "<input type =\"text\" name =\"state\" value=\""+res.getString("State")+"\">";
            String zipcode = "<input type =\"text\" name =\"zipcode\" oninput=\"javascript:if (this.value > 5) this.value = this.value.slice(0, 5);\" value=\""+res.getString("zipcode")+"\">";
            String phoneNum = "<input type =\"text\" name =\"phonenum\" oninput=\"javascript:if (this.value > 10) this.value = this.value.slice(0, 10);\" value=\""+res.getString("phone")+"\">";
            String hiddenid = "<input type =\"hidden\" name =\"id\" value=\""+id+"\">";
            phoneNum+="\n"+ hiddenid;

            table.addFormRow(new String[]{FirstName,lastName,address,city
            ,state,zipcode,phoneNum,"<input type=\"submit\" value= \"Apply Updates\">"},"UpdateEmployee");
            out.println(table.returnTable());

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
