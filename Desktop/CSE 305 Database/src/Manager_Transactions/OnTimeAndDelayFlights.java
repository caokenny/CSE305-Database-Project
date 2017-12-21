package Manager_Transactions;

import Database.Database;
import Header.Header;
import Utils.TableCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/OnTimeAndDelayFlights")
public class OnTimeAndDelayFlights extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            PrintWriter out = response.getWriter();
            out.println(Header.getManagerHeader());
            response.setContentType("text/html");
            out.println("<style>" +
                    "ontime{color: green;}" +
                    "late{ color: red;}"+
                    "</style>");
            String ontimeq ="SELECT l.*, a.ArrTime AS \"Actual ArrTime\"\n" +
                    "FROM ActualTime a, Leg l\n" +
                    "WHERE a.AirlineID = l.AirlineID AND a.FlightNo = l.FlightNo AND a.ArrTime <= l.ArrTime AND a.LegNo = l.LegNo AND a.ArrAirportID = l.ArrAirportID;\n";
            String lateq="SELECT l.*, a.ArrTime AS \"Actual ArrTime\"\n" +
                    "FROM ActualTime a, Leg l\n" +
                    "WHERE a.AirlineID = l.AirlineID AND a.FlightNo = l.FlightNo AND a.ArrTime > l.ArrTime AND a.LegNo = l.LegNo AND a.ArrAirportID = l.ArrAirportID;\n";

            ResultSet lateSet = Database.executeQ(lateq);
            ResultSet ontimeSet = Database.executeQ(ontimeq);
            TableCreator table =  new TableCreator(new String[]{"AirlineID","FlightNo","Leg No","Departure Airport ID","Arrival Airport ID",
                    "Expected Arrival Time", "Actual Arrival Time"});
            while(ontimeSet.next())
            {
                String airlineid = ontimeSet.getString("airlineID");
                String flightno = ontimeSet.getString("flightno");
                String legNo = ontimeSet.getString("legno");
                String dep = ontimeSet.getString("depairportid");
                String arr = ontimeSet.getString("arrairportid");
                String arrtime = ontimeSet.getString("arrtime");
                String actual = "<ontime>"+ontimeSet.getString("actual arrtime")+"</ontime>";
                table.addRow(new String[]{airlineid,flightno,legNo,dep,arr,arrtime,actual});
            }
            while(lateSet.next())
            {
                String airlineid = lateSet.getString("airlineID");
                String flightno = lateSet.getString("flightno");
                String legNo = lateSet.getString("legno");
                String dep = lateSet.getString("depairportid");
                String arr = lateSet.getString("arrairportid");
                String arrtime = lateSet.getString("arrtime");
                String actual = "<late>"+lateSet.getString("actual arrtime")+"</late>";
                table.addRow(new String[]{airlineid,flightno,legNo,dep,arr,arrtime,actual});
            }
            out.println(table.returnTable());
            HomePage.goBackButton(out);
            out.println(Header.getFooter());

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
