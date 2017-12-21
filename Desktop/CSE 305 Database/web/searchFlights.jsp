<%@ page import="com.sun.org.apache.xpath.internal.operations.String" %><%--
  Created by IntelliJ IDEA.
  Person: KennyCao
  Date: 12/2/17
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="/css/homeStyle.css">
    <script src="js/homeFunction.js"></script>
    <title>Flight Hounds</title>
  </head>
  <body>
    <sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
      url = "jdbc:mysql://localhost:3306/CSE305_PROJECT?autoReconnect=true&useSSL=false"
      user = "root" password = "cse3051"/>

    <sql:query dataSource = "${snapshot}" var = "result">
      SELECT City, Id, Name FROM Airport ORDER BY City;
    </sql:query>

    <h1 style="text-align: center">Welcome to Flight Hounds</h1>
    <p style="text-align: center">${log}</p>
      <div style="text-align: center; margin: 0 auto;">
        <ul class="tab" style="text-align: center; margin: 0 auto;">
          <li><a href="${pageContext.request.contextPath}/index.jsp" class="tablinks">Home</a></li>
          <li><a href="javascript:void(0)" class="tablinks active" onclick="openPage(event, 'Roundtrip')">Roundtrip</a></li>
          <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'Oneway')">One way</a></li>
        </ul>

        <div id="Roundtrip" class="loggedInTabContent">
          <form action="SearchFlightsOneway" method="post">
            Flying From:
            <select name="From">
              <c:forEach var="row" items="${result.rows}">
                <option value="${row.Id}">${row.City} (${row.Id}-${row.Name})</option>
              </c:forEach>
            </select>
            Flying To:
            <select name="To">
              <c:forEach var="row" items="${result.rows}">
                <option value="${row.Id}">${row.City} (${row.Id}-${row.Name})</option>
              </c:forEach>
            </select>
            <br/><br/>
            Departing Date: <input type="date" name="Depart"/>
            Returning Date: <input type="date" name="Return"/><br/><br/>
            <input type="submit" value="Search"/>
          </form>
        </div>

        <div id="Oneway" class="tabcontent">
          <form action="SearchFlightsOneway" method="post">
            Flying From:
            <select name="From">
              <c:forEach var="row" items="${result.rows}">
                <option value="${row.Id}">${row.City} (${row.Id}-${row.Name})</option>
              </c:forEach>
            </select>
            Flying To:
            <select name="To">
              <c:forEach var="row" items="${result.rows}">
                <option value="${row.Id}">${row.City} (${row.Id}-${row.Name})</option>
              </c:forEach>
            </select>
            <br/><br/>
            Departing Date: <input type="date" name="Depart" required/><br/><br/>
            <input type="submit" value="Search"/>
          </form>
        </div>

      </div>
  </body>
</html>