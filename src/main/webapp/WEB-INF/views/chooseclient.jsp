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
        <img src="/images/label.jpg" width="30" height="30"
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
            <a href="/employee" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>
    <div style="margin-top: 10px">
        <h3>Enter client's phone number </h3>
    </div>
    <form:form action="/chooseclient" modelAttribute="contractDto" method="post" style="margin-top: 10px">
    <div class="input-group mb-3">
        <form:input type="text" path="phone" class="form-control" pattern="\d{11}" placeholder="Client's phone ex. 89118887766" aria-label="Client's phone" aria-describedby="button-addon2"/>
        <div class="input-group-append">
            <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Button</button>
        </div>
    </div>
    </form:form>

<c:if test="${client!=null}">
    <form method="get" action="/searchclient">
    <c:set value="${client}" scope="session" var="clientVar"/>
    <div class="form-group row" style="margin-top: 10px">

        <div class="col-sm-12 col-md-12">

            <table class="table" id="required options">
                <thead>
                <tr>
                    <th scope="col">id</th>
                    <th scope="col">FirstName</th>
                    <th scope="col">LastName</th>
                    <th scope="col">Passport</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>

                    <tr>
                        <th scope="row">${clientVar.id}</th>
                        <td>${clientVar.firstName}</td>
                        <td>${clientVar.lastName}</td>
                        <td>${clientVar.passport_series} ${clientVar.passport_number}</td>
                        <td><button type="submit" name="choice" value="${clientVar.id}" class="btn btn-outline-dark btn-sm">choose</button></td>


                    </tr>

                </tbody>
            </table>
        </div>
    </div>
    </form>
</c:if>
    <c:if test="${result!=null}">
        <div id="message"><p style="margin-top: 10px; color: red">${result}</p></div>
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
