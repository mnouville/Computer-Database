<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="interface.css">
        <title>Company</title>
    </head>
        <body>
            <div>
            <h1>
                List of companies :
            </h1>
            
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  items="${companies}" var="c" >
                        <tr>
                            <td><c:out value="${c.id}" /></td>
                            <td><c:out value="${c.name}" /></td>
                            <td>
                            	<form method="post" action="CompanyServlet?Delete&id=${c.id}">
                            		<input type="submit" value="Delete"/>
                            	</form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <h1>Add a new Company</h1>
            <form method="post" action="CompanyServlet?Add">
               <table border="1">
                    <thead>
                        <tr>
                            <th>Data</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><label for="id">ID : </label></td>
                            <td><input type="number" name="id" id="id"/></td>
                        </tr>
                        <tr>
                            <td><label for="name">Name : </label></td>
                            <td><input type="text" name="name" id="name"/></td>
                        </tr>
                    </tbody>
                </table>
                <br>
                <input type="submit" />
            </form>
            <br>
            <button onclick="location.href='menu.jsp'" type="button">Menu</button>
            </div>
                
        </body>
</html>