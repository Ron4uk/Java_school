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
<div class="container">
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
                    <a class="nav-link" href="/startauthclient">Main <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/usercontract">Contract<span class="sr-only">(current)</span></a>
                </li>


            </ul>
            <c:choose>
                <c:when test="${orderDto.tariffDto!=null || orderDto.optionsFromCurTariff.size()>0 || orderDto.disableOptionsFromCurTariff.size()>0}">

                    <a href="/usershoppingcart" type="button" class="btn btn-sm btn-light " style="margin-right: 5px">
                        <span><img src="/images/scFull.png"/></span> Shopping Cart
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/usershoppingcart" type="button" class="btn btn-sm btn-light " style="margin-right: 5px">
                        <span><img src="/images/emptyCart.jpg"/></span> Shopping Cart
                    </a>
                </c:otherwise>
            </c:choose>
            <a href="/usermanageoption" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>
    <c:if test="${contractDto.connectedOptions.size()>0}">
    <form:form action="/disableoption" modelAttribute="orderDto" method="post">
        <div style="margin-top: 20px">
            <h4>Connected options</h4>
        </div>
        <table class="table" style="margin-top: 10px">
            <thead>
            <tr>
                <th scope="col">name</th>
                <th scope="col">price</th>
                <th scope="col">connection cost</th>
                <th scope="col"></th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${contractDto.connectedOptions}" var="option">

<%--                <form:hidden path="disableOptionsFromCurTariff" value="${option.id}"/>--%>
                <tr>
                    <th scope="row">${option.name}</th>
                    <td>${option.price}$</td>
                    <td>${option.connectionCost}$</td>
                    <td>
                        <c:choose>
                            <c:when test="${orderDto.disableOptionsFromCurTariff.contains(option)}">
                                <form:checkbox path="disableOptionsFromCurTariff" checked="checked" value="${option.id}"></form:checkbox>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox path="disableOptionsFromCurTariff" value="${option.id}"></form:checkbox>
                            </c:otherwise>
                        </c:choose>
                    </td>

                </tr>

            </c:forEach>
            </tbody>
        </table>
        <div class="form-group row justify-content-end" style="margin-top: 5px">
        <div class="col-sm-10 ">
            <form:button type="submit" class="btn btn-secondary" style="float: right">Save</form:button>
        </div>
        </div>
    </form:form>
    </c:if>
    <c:if test="${orderDto.tariffDto!=null}">
        <div style="color: red">
            NOTE: If you add or disable options, selected new tariff and options will be removed from the shopping cart.
        </div>

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
