<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
        Created by IntelliJ IDEA.
        User: Oleg
        Date: 12.07.2020
        Time: 20:25
        To change this template use File | Settings | File Templates.
        --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title>MyJSPPage</title>


</head>
<body>
<script language="Javascript">

</script>

<div class="container" style="margin-bottom: 10px">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <span class="navbar-brand" href="#">
        <img src="${pageContext.request.contextPath}/images/label.jpg" width="30" height="30"
             class="d-inline-block align-top" alt="" loading="lazy">
        eCare
    </span>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/startauthempl">Main <span class="sr-only">(current)</span></a>
                </li>


            </ul>
            <a href="${pageContext.request.contextPath}/employee" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>
    <c:if test="${listContracts !=null}">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">№</th>
                <th scope="col">Phone number</th>
                <th scope="col">Client</th>
                <th scope="col">Tariff</th>
            </tr>
            </thead>
            <tbody>


            <c:forEach items="${listContracts}" var="mes" varStatus="numberOfContract">
                <tr>
                    <c:choose>
                        <c:when test="${pagenumber==0 || pagenumber==1}">
                            <th scope="row">${numberOfContract.count}</th>
                        </c:when>
                        <c:otherwise>
                            <th scope="row">${numberOfContract.count+(5*(pagenumber-1))}</th>
                        </c:otherwise>
                    </c:choose>

                    <td>${mes.phone}</td>
                    <td>${mes.clientDto.firstName} ${mes.clientDto.lastName}</td>
                    <td>${mes.tariffDto.tariff}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${count>1}">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:forEach var = "pagenumber" begin = "1" end = "${count}">
                    <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}
                    /employee/getAllContracts?id=${pagenumber}">${pagenumber}</a></li>
                </c:forEach>

            </ul>
        </nav>


    </c:if>


    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>




<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>