<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ComputerServlet"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${idcomputer}" />
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="EditComputerServlet" method="POST">
                        <input type="hidden" value="<c:out value="${idcomputer}"/>" id="id" name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="name">Computer name</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="Computer name" value="<c:out value="${name}"/>">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="<c:out value="${introduced}"/>">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="<c:out value="${discontinued}"/>">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyid">
                                    <option value="0">--</option>
                                    <c:forEach  items="${companies}" var="c" >
                                    	<c:choose>
                                    	
	                                    	<c:when test = "${c.id == companyid }">
									            <option selected value="<c:out value='${c.id}' />"><c:out value="${c.name}" /></option>
									        </c:when>
									         
									        <c:otherwise>
									            <option value="<c:out value='${c.id}' />"><c:out value="${c.name}" /></option>
									        </c:otherwise>
                                    	
                                    	</c:choose>
				                        
				                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="ComputerServlet" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/addComputer.js"></script>
</body>

</html>