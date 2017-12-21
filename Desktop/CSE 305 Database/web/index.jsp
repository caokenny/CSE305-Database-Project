<%@ page import="com.sun.org.apache.xpath.internal.operations.String" %><%--
  Created by IntelliJ IDEA.
  Person: KennyCao
  Date: 12/2/17
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="/css/homeStyle.css">
    <script src="js/homeFunction.js"></script>
    <title>Flight Hounds</title>
  </head>
  <body>
    <h1 style="text-align: center">Welcome to Flight Hounds</h1>
    <p style="text-align: center">${log}</p>
    <div style="text-align: center; margin: 0 auto;">
      <ul class="tab" style="text-align: center; margin: 0 auto;">
        <c:if test="${empty loggedInUser}">
          <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'Login')">User</a></li>
          <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'EmployeeLogin')">Employee</a></li>
          <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'ManagerLogin')">Manager</a></li>
        </c:if>
        <c:if test="${not empty loggedInUser}">
          <li><a href="javascript:void(0)" id="UserLoggedIn" class="tablinks">User</a></li>
          <li><a href="javascript:callServlet('CustomerLoggedOut')">Logout</a></li>
        </c:if>
      </ul>

      <c:if test="${not empty loggedInUser}">
        <div class="loggedInTabContent">
          <h3>Welcome to User Level Transactions</h3>
          <li><a href="${pageContext.request.contextPath}/searchFlights.jsp">Search Flights</a></li>
          <br/>
          <li><a href="${pageContext.request.contextPath}/viewReservations.jsp">View Reservations</a></li>
          <br/>
          <li><a href="ViewBestSellingFlights.jsp">View Best Selling Flights</a></li>
          <br/>
          <li><a href="ViewSuggestedFlights.jsp">View Suggested Flights</a></li>
          <br/>
          <li><a href="ViewUpdateInfo.jsp">View/Update Personal Info</a></li>
          <br/>
        </div>
      </c:if>

      <div id="Register" class="tabcontent">
        <h1>Hello! Please Register below</h1>
        <form action="AddPerson" method="post">
          <label>First Name: </label><input type="text" name="FirstName" required/><br/><br/>
          <label>Last Name:</label> <input type="text" name="LastName" required/><br/><br/>
          <label>Username:</label> <input type="text" id="usernameRegister" onkeyup="return forceLower(this)" name="Username" required/><br/><br/>
          <label>Password:</label> <input type="password" name="Password" pattern=".{5,}" required title="5 characters minimum"/><br/><br/>
          <label>E-mail:</label> <input type="text" name="Email" required/><br/><br/>
          <label>Address:</label> <input type="text" name="Address" required/><br/><br/>
          <label>State:</label> <input type="text" name="State" required/><br/><br/>
          <label>City:</label> <input type="text" name="City" required/><br/><br/>
          <label>Zip Code:</label> <input type="number" name="ZipCode" min="10000" oninput="javascript:if (this.value > 5) this.value = this.value.slice(0, 5);" required/><br/><br/>
          <label>Telephone:</label> <input type="number" name="Telephone" oninput="javascript: if (this.value > 10) this.value = this.value.slice(0, 10);" required/><br/><br/>
          <label>Credit Card Number:</label> <input type="number" name="CreditCardNum" required/><br/><br/>
          <input type="submit" value="Create Account"/>
        </form>
      </div>

      <c:if test="${empty loggedInUser}">
        <div id="Login" class="loggedInTabContent" style="margin: 0 auto;">
          <h1>Customer Login</h1>
          <form action="CustomerLogin" method="post">
            <label>Username:</label> <input type="text" id="usernameInput" onkeyup="return forceLower(this)" name="Username" required/><br/><br/>
            <label>Password:</label> <input type="password" name="Password" required/><br/><br/>
            <a href="javascript:openPage(event, 'Register')">Don't have an account? Click here to register!</a><br/><br/>
            <input type="submit" value="Log in"/>
          </form>
        </div>
      </c:if>

      <div id="ManagerLogin" class="tabcontent" style="margin: 0 auto;">
        <h1>Manager Login</h1>
        <form action="ManagerLogin" method = "post">
          <label>Username:</label> <input type = "text" name = "username" required/><br/><br/>
          <label>Password:</label> <input type ="password" name = "password" required/><br/><br/>
          <input type ="submit" value="Log in">
        </form>
      </div>

      <div id="EmployeeLogin" class="tabcontent" style="margin: 0 auto">
        <h1>Employee Login</h1>
        <form action ="EmployeeLogin" method = "get">
          <label>Username:</label> <input type="text" name="Username" required/><br/><br/>
          <label>Password:</label> <input type="password" name="Password" required/><br/><br/>
          <input type="submit" value="Log in">
        </form>
      </div>



    </div>
  </body>
</html>
