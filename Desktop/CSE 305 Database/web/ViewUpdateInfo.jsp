<%--
  Created by IntelliJ IDEA.
  User: KennyCao
  Date: 12/6/17
  Time: 8:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View/Update Person Info</title>
</head>
<body>
  <%
    RequestDispatcher dispatcher = request.getRequestDispatcher("ViewUpdatePersonalInfo");
    dispatcher.forward(request, response);
  %>
</body>
</html>
