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

@WebServlet(urlPatterns = "/DeleteEmployeePage")
public class DeleteEmployeePage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request , HttpServletResponse response) throws  ServletException, IOException
    {
        try{
            PrintWriter out = response.getWriter();

            out.println(
                    "<head> \n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                    "</head>"
            );

            out.println(
                    "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                            "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                            "       <li><a href=\"Manager_index.jsp\">Home</a></li>" +
                            "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Manager</a></li>\n" +
                            "   </ul>"
            );

            out.println("<div class=\"loggedInTabContent\">");


            response.setContentType("text/html");
            String q = "SELECT * FROM employee E,Person P WHERE P.id = E.id";
            String [] header = {"ID","Name","address","Phone Number","Update", "Delete"};
            TableCreator table = new TableCreator(header);

            ResultSet res = Database.executeQ(q);
            while(res.next())
            {
                String name = res.getString("FirstName")+ " "
                        + res.getString("LastName");
                String address = res.getString("Address")+", "
                        +res.getString("city")+", "+
                        res.getString("zipcode");
                String id = res.getString("id");
                String phoneNo = res.getString("phone");
                String updateButton = "<form action =\"UpdateEmployeePage\" method =\"get\">\n"+
                    "<input type=\"hidden\" name=\"id\" value ="+id+">"+
                    "<input type=\"submit\" value=\"Update\">\n"+
                    "</form>";

                String deleteButton = "<form action= \"DeleteEmployee\" method=\"get\">\n" +
                        "<input type=\"hidden\" name=\"id\" value ="+id+">"+
                        "<input type=\"submit\" value=\"Delete\">\n"+
                        "</form>";
                String [] cell = {id,name,address,phoneNo,updateButton,deleteButton};
                table.addRow(cell);
            }
                out.println(table.returnTable());
                out.println("</div></div>");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
