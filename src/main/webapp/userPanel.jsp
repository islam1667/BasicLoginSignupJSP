<%
    System.out.println("CHECKING FOR SESSION");
    if (session.getAttribute("uname") == null) {
        out.print("Please Login!");
        RequestDispatcher rd = request.getRequestDispatcher("/index.html");
        rd.include(request, response);
        System.out.println("SESSION IS NULL!");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <h3><%= session.getAttribute("uname")%></h3>
        <h3><%= session.getAttribute("upwd")%></h3>
        <h3><%= session.getAttribute("umail")%></h3>
        <h3><%= session.getAttribute("uphone")%></h3>
        <form action="LoginServlet" method="POST">
            <input type="submit" value="logout" name="act">
        </form>
    </body>
</html>
