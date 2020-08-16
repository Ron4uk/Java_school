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
                    <a class="nav-link" href="${pageContext.request.contextPath}/startauthclient">Main</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/usercontract">Contract</a>
                </li>


            </ul>
            <c:choose>
                <c:when test="${orderDto.tariffDto!=null}">
                    <a href="${pageContext.request.contextPath}/user/usershoppingcart" type="button" class="btn btn-sm btn-light " style="margin-right: 5px">
                        <span><img src="${pageContext.request.contextPath}/images/scFull.png"/></span> Shopping Cart
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user/usershoppingcart" type="button" class="btn btn-sm btn-light " style="margin-right: 5px">
                        <span><img src="${pageContext.request.contextPath}/images/emptyCart.jpg"/></span> Shopping Cart
                    </a>
                </c:otherwise>
            </c:choose>
            <a href="${pageContext.request.contextPath}/user?id=${contractDto.id}" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>

    <form action="${pageContext.request.contextPath}/user/confirmorder" method="post" class="border" style="margin-top: 10px">
        <c:if test="${orderDto.tariffDto!=null}">
            <div style="margin-top: 5px; margin-left: 5px">
            <h4 align="center"> New tariff</h4>
            <table class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>${orderDto.tariffDto.tariff}</th>
                        <td>${orderDto.tariffDto.price}$</td>
                        <td style="float: right"><a href="${pageContext.request.contextPath}/user/deletetarifffromorder" class="btn btn-outline-dark btn-sm" value="${orderDto.tariffDto.id}">Delete</a></td>
                    </tr>
                </tbody>
            </table>
            </div>
        </c:if>
        <c:if test="${orderDto.optionsFromNewTariff.size()>0}">
        <div style="margin-top: 5px; margin-left: 5px">
            <h4 align="center"> Selected options</h4>
            <table class="table">
                <thead>
                <tr>
                    <th>Option</th>
                    <th>Price</th>
                    <th>Connection cost</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderDto.optionsFromNewTariff}" var="option">
                <tr>
                    <th>${option.name}</th>
                    <td>${option.price}$</td>
                    <td>${option.connectionCost}$</td>
                    <td style="float: right"><a href="${pageContext.request.contextPath}/user/deletenewoptionfromorder?id=${option.id}" class="btn btn-outline-dark btn-sm" value="${option.id}">Delete</a></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        </c:if>

        <c:if test="${orderDto.tariffDto!=null || orderDto.optionsFromNewTariff.size()>0}">
        <div class="form-group row justify-content-end" style="margin-top: 5px">
            <div class="col-sm-10 ">
                <button type="submit" class="btn btn-secondary" style="float: right; margin-right: 10px">Confirm</button>
            </div>
        </div>
        </c:if>
    </form>

    <c:if test="${result!=null}">
        <div id="message" ><p style="margin-top: 10px; color: brown" >${result}</p></div>
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