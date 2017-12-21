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

@WebServlet(urlPatterns = "/ShowFlights")
public class ShowFlights extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException
    {
        String query ="select * from flight F, airline A where A.id =F.airlineid";
        try{
            ResultSet result =Database.executeQ(query);
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

            response.setContentType("text/html; charset= UTF-8");
            TableCreator table = new TableCreator(new String[]{"AirlineId","Airline Name","Flight No","Days of operation","No of Seats"});
            while(result.next()) {
                String airlineid = result.getString("AirlineId");
                String flightNo = result.getString("flightNo");
                String airlineName = result.getString("name");
                String optdays = result.getString("daysOperating");
                String seatNum = result.getString("NoOfSeats");
                String operatingDays = " ";

                if (Character.toString(optdays.charAt(0)).equals("1"))
                    operatingDays += "Monday, ";
                if (Character.toString(optdays.charAt(1)).equals("1"))
                    operatingDays += "Tuesday, ";
                if (Character.toString(optdays.charAt(2)).equals("1"))
                    operatingDays += "Wednesday, ";
                if (Character.toString(optdays.charAt(3)).equals("1"))
                    operatingDays += "Thursday, ";
                if (Character.toString(optdays.charAt(4)).equals("1"))
                    operatingDays += "Friday, ";
                if (Character.toString(optdays.charAt(5)).equals("1"))
                    operatingDays += "Saturday, ";
                if (Character.toString(optdays.charAt(6)).equals("1"))
                    operatingDays += "Sunday ";
                if(Character.toString(operatingDays.charAt(operatingDays.length()-2)).equals(","))
                    operatingDays= operatingDays.substring(0,operatingDays.length()-2);
                table.addRow(new String []{airlineid,airlineName,flightNo,operatingDays,seatNum});
            }

            out.println(table.returnTable());
            out.println("<br>");

            out.println("</div></div>");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
