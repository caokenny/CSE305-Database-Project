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
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.time.LocalDateTime;

@WebServlet("/RecordReservation")
public class RecordReservation extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String resrNo = request.getParameter("resrNo");
        //LocalDateTime now = LocalDateTime.now();
        String empid = request.getParameter("empid");
        String AccNo = request.getParameter("AccountNo");

        String airlineId = request.getParameter("airlineid");
        String flightnum = request.getParameter("flightnum");
        String legno = request.getParameter("legno");
        String SeatNo = request.getParameter("SeatNo");
        String classw = request.getParameter("Class");
        String meal = request.getParameter("Meal");

        String empssn = "select * from employee where id="+empid;
        String getid = "select * from customer where accountno="+AccNo;


//        double bookingFeeNum = Double.parseDouble(TotalFee);
//
//        bookingFeeNum = bookingFeeNum * 0.10;
//
//        String BookingFee = Double.toString(bookingFeeNum);

        String getFare = "SELECT Fare FROM Fare WHERE AirlineID='" + airlineId + "' AND FlightNo='" + flightnum + "' AND " +
                "Class='" + classw + "'";

        ResultSet set = Database.executeQ(getFare);

        set.next();

        String TotalFee = set.getString(1);

        double bookingFeeNum = Double.parseDouble(TotalFee);
        bookingFeeNum = bookingFeeNum * 0.10;

        String BookingFee = Double.toString(bookingFeeNum);


        try{
            String resnoQ = "select max(resrno) from reservation;";
            ResultSet resgetter =Database.executeQ(resnoQ);
            resgetter.next();
            int resrNo = Integer.parseInt(resgetter.getString("max(resrno)"));
            resrNo++;

            String addinclude = "Insert into includes VALUES(" +resrNo+",\""+airlineId+"\","+flightnum+","+legno+", "+
                    "curdate())";

            ResultSet res = Database.executeQ(empssn);
            res.next();
            ResultSet idgetter = Database.executeQ(getid);
            idgetter.next();
            String ssn = res.getString("ssn");
            String id = idgetter.getString("id");

            String recordReservation = "INSERT INTO Reservation Values ("+resrNo+", curdate() "+
                    ", '"+BookingFee+"', '"+TotalFee+"', '"+ssn+"', '"+AccNo+"')";
            String passengerReservation = "INSERT INTO ReservationPassenger VALUES" +
                    "('"+resrNo+"', '"+id+"', '"+AccNo+"', '"+SeatNo+"', '"+classw+"', '"+meal+"')";
            String removeseat = "select * from flight where airlineid =\""+airlineId+"\" AND flightno ="+flightnum;
            ResultSet flight =Database.executeQ(removeseat);
            flight.next();
            int seatnum = Integer.parseInt(flight.getString("noofseats"));
            seatnum -= 1;

            String seatno ="UPDATE flight set noofseats="+seatnum+" where airlineid=\""+ airlineId+"\" and flightno="
                    +flightnum;
            /*System.out.println(recordReservation);
            System.out.println(passengerReservation);
            System.out.println(addinclude);
            System.out.println(seatno);*/

            Database.executeU(recordReservation);
            Database.executeU(passengerReservation);
            Database.executeU(addinclude);
            Database.executeU(seatno);

            PrintWriter out = response.getWriter();

            out.println(Header.getEmployeeHeader());

            out.println("<h3>Total Fare</h3>");
            out.println("<h3>" + TotalFee + "</h3>");


            out.println("<form action=\"EmployeeIndex.jsp\" method =\"get\">\n" +
                    "         <input type =\"submit\"   value=\"Back\">");

            out.println(Header.getFooter());




        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
