<%--
  Created by IntelliJ IDEA.
  User: KennyCao
  Date: 12/4/17
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Reservations</title>
</head>
<body>
  <%
    RequestDispatcher dispatcher = request.getRequestDispatcher("ViewReservations");
    dispatcher.forward(request, response);
  %>
</body>
</html>
