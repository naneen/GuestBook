<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" >
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <%--<script src="${pageContext.request.contextPath}/js/jquery.js"></script>--%>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
    <script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>

    <link rel="stylesheet" href="http://jqueryvalidation.org/files/demo/site-demos.css">

    <title>Index</title>

    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/css/page.css" rel="stylesheet" >
    <script src="${pageContext.request.contextPath}/resources/js/pagination.js"></script>

    <script src='https://www.google.com/recaptcha/api.js'></script>

</head>
<body>
    <h1>${msg}</h1>
    <%--<h1>${arrL[0].name}</h1>--%>
    <form id="myForm" name="myForm" action ="result.html" method="post">
        <div>
            <label for="message">message:</label>
            <input type="text" class="form-control" id="message" name="message" required>
        </div>
        <br>
        <br>
        <div>
            <label for="name" class="required">Name:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <br>
        <div class="g-recaptcha" data-sitekey="6LctMggTAAAAAHhA5WsS95IpWHXUkORT1qYg44wn" data-theme="white"></div>
        <br>
        <button type="submit"  class="flat-butt flat-primary-butt flat-inner-butt
                flat-primary-inner-butt" >Submit</button>
    </form>



    <br>
    <br>
    <table align="center">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Message</th>
        </tr>
        <c:forEach items="${arrL}" var="arr">
            <tr>
                <td>${arr.id}</td>
                <td>${arr.name}</td>
                <td>${arr.msg}</td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <c:forEach var="p" begin="1" end="${page}" >
        <a href="/GuestBook/?Page=${p}">${p}</a>
    </c:forEach>
    <br>

    <%--<ul class="pagination">--%>
        <%--&lt;%&ndash;<li class="disabled"><a href="#">«</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li class="active"><a href="#">1</a></li>&ndash;%&gt;--%>
        <%--<c:forEach var="p" begin="1" end="${page}" >--%>
            <%--<li><a href="/GuestBook/?Page=${p}">${p}</a></li>--%>
        <%--</c:forEach>--%>
    <%--</ul>--%>

    <%--<c:set var="salary" scope="session" value="${2000*2}"/>--%>
    <%--<p>Your salary is : <c:out value="${salary}"/></p>--%>
    <%--<c:choose>--%>
        <%--<c:when test="${salary <= 0}">--%>
            <%--Salary is very low to survive.--%>
        <%--</c:when>--%>
        <%--<c:when test="${salary > 1000}">--%>
            <%--Salary is very good.--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--No comment sir...--%>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>

    <%-- ========================================================================================= SCRIPT --%>
    <script>
        // just for the demos, avoids form submit
        jQuery.validator.setDefaults({
            debug: true,
            success: "valid"
        });
        var form = $( "#myForm" );
        form.validate({});

        $( "button" ).click(function() {
            if( form.valid() ){
                document.getElementById('myForm').submit();
            }
        });

        $("ul.pagination3").quickPagination({pagerLocation:"top",pageSize:"3"});
    </script>

</body>
</html>