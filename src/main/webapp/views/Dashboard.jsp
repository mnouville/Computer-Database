<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	<c:set var="sort" value="${param['sort']}" scope="application"></c:set>
	
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ComputerServlet"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${maxcomputer}" /> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="SortServlet" method="POST" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="AddComputerServlet">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="DeleteComputerServlet" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                        	<c:choose>
				            	<c:when test="${param['type'] == null}">
									<a href="SortByName?page=${param['page']}&type=ASC&sort=name">Computer name</a>
								</c:when>
								<c:otherwise>
									<a href="SortByName?page=${param['page']}&type=${type}&sort=name">Computer name</a>
								</c:otherwise>
			            	</c:choose>
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                   <c:forEach  items="${computers}" var="c" >
                        <tr>
                        	<td class="editMode">
                            	<input type="checkbox" name="cb" class="cb" value="<c:out value="${c.id}"/>">
	                        </td>
	                        <td>
	                            <a href="EditComputerServlet?id=<c:out value="${c.id}"/>" onclick=""><c:out value="${c.name}" /></a>
	                        </td>
                            <td><c:out value="${c.introduced}" /></td>
                            <td><c:out value="${c.discontinued}" /></td>
                            <td><c:out value="${c.companyname}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="ComputerServlet?page=1" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                	</a>
	            </li>
	            <c:set var="page" value="0" />
	            <c:forEach var="i" begin="0" end="${maxcomputer}" step="1">
	            	<c:choose>
		            	<c:when test="${i % 50 == 0}">
		            		<c:set var="page" value="${page + 1}" scope="page"/>
		            		<c:choose>
		            			<c:when test="${sort == 'name'}">
		            				<li><a href="SortByName?page=<c:out value='${page}'/>&type=<c:out value="${param['type']}"/>&sort=name"><c:out value="${page}"/></a></li>
		            			</c:when>
		            		</c:choose>
<%-- 							<li><a href="ComputerServlet?page=<c:out value='${page}'/>"><c:out value="${page}"/></a></li> --%>
						</c:when>
	            	</c:choose>
				</c:forEach>
	            <li>
	            	<a href="ComputerServlet?page=<c:out value='${page}'/>" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
	            </li>
        	</ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>
    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>