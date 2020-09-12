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
    <link rel="stylesheet" href="/resources/css/style.css">

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
                    <a href="${pageContext.request.contextPath}/user/usershoppingcart" type="button"
                       class="btn btn-sm btn-light " style="margin-right: 5px">
                        <span><img src="${pageContext.request.contextPath}/images/scFull.png"/></span> Shopping Cart
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user/usershoppingcart" type="button"
                       class="btn btn-sm btn-light " style="margin-right: 5px">
                        <span><img src="${pageContext.request.contextPath}/images/emptyCart.jpg"/></span> Shopping Cart
                    </a>
                </c:otherwise>
            </c:choose>
            <a href="${pageContext.request.contextPath}/user?id=${contractDto.id}" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>
    <c:choose>
        <c:when test="${result.equals('Change successful')}">
            <div style="margin-top: 10px; text-align: right">
                <h5 style="color: green">${result}</h5>
            </div>
        </c:when>
        <c:otherwise>
            <div style="margin-top: 10px; text-align: right">
                <h5 style="color: red">${result}</h5>
            </div>
        </c:otherwise>

    </c:choose>


    <h4 style="margin-top: 10px; text-align: center">Connected options</h4>
    <div class="row" style="margin-top: 20px">
        <c:forEach items="${contractDto.connectedOptions}" var="connectedoption">

            <div class="col-md-4 col-sm-4">
                <form method="post" action="${pageContext.request.contextPath}/user/disconnectoptionbyuser" id="disconnect${connectedoption.id}">
                    <input type="hidden" name="optid" value="${connectedoption.id}">
                <h5 style="text-align: center">${connectedoption.name}</h5>
                <a href="#" onclick="document.getElementById('disconnect${connectedoption.id}').submit()">
                    <div class="box">
                        <svg class="curve1" x="0px" y="0px" viewBox="0 0 400 200">
                            <path d="M398.938,143.806c-24.004,26.063-155.373,104.33-224.724,7.328 C69.626,4.846,0.5,71.583,0.5,71.583V1.5h398.629L398.938,143.806z"/>
                        </svg>
                        <img src="${pageContext.request.contextPath}/images/manage_option.jpg" alt="">
                        <div class="box-content">
                            <h3 class="title">CLick</h3>
                            <span class="post">to disconnect</span>
                            <span class="post"></span>
                            <span class="post">Monthly cost: ${connectedoption.price}$</span>
                            <span class="post">Connection: ${connectedoption.connectionCost}$</span>

                            <c:if test="${connectedoption.requiredOptions.size()>0}">
                        <span class="post" style="color: red; font-weight: bold"> Work only with:
                    <c:forEach items="${connectedoption.requiredOptions}" var="requirement">
                        ${requirement.name}
                    </c:forEach>
                        </span>
                            </c:if>
                            <c:if test="${connectedoption.exclusionOptions.size()>0}">
                        <span class="post" style="color: red; font-weight: bold"> Cannot be connected with:
                    <c:forEach items="${connectedoption.exclusionOptions}" var="exclusion">
                        ${exclusion.name}
                    </c:forEach>
                        </span>
                            </c:if>


                        </div>
                        <svg class="curve2" x="0px" y="0px" width="150px" height="150px" viewBox="0 0 150 50">
                            <path d="M1.114,7.567C87.544-33.817,150,150.5,150,150.5H1.361L1.114,7.567z"/>
                        </svg>
                    </div>
                </a>
                </form>
            </div>

        </c:forEach>
    </div>
    <h4 style="margin-top: 100px; text-align: center" >Available options</h4>
    <div class="row" style="margin-top: 20px">
    <c:forEach items="${contractDto.tariffDto.options}" var="optionintarif">
        <c:if test="${!contractDto.connectedOptions.contains(optionintarif) && optionintarif.deleted!=true}">
            <div class="col-md-4 col-sm-4">
                <form method="post" action="${pageContext.request.contextPath}/user/connectedoptionbyuser" id="connect${optionintarif.id}">
                    <input type="hidden" name="optid" value="${optionintarif.id}">
                <h5 style="text-align: center">${optionintarif.name}</h5>
                <a href="#" onclick="document.getElementById('connect${optionintarif.id}').submit()">
                    <div class="box">

                        <svg class="curve1" x="0px" y="0px" viewBox="0 0 400 200">
                            <path d="M398.938,143.806c-24.004,26.063-155.373,104.33-224.724,7.328 C69.626,4.846,0.5,71.583,0.5,71.583V1.5h398.629L398.938,143.806z"/>
                        </svg>
                        <img src="${pageContext.request.contextPath}/images/m_option.jpg" alt="">
                        <div class="box-content">
                            <h3 class="title">CLick</h3>
                            <span class="post">to connect</span>
                            <span class="post"></span>
                            <span class="post">Monthly cost: ${optionintarif.price}$</span>
                            <span class="post">Connection: ${optionintarif.connectionCost}$</span>

                            <c:if test="${optionintarif.requiredOptions.size()>0}">
                        <span class="post" style="color: red; font-weight: bold"> Work only with:
                    <c:forEach items="${optionintarif.requiredOptions}" var="requirement">
                        ${requirement.name}
                    </c:forEach>
                        </span>
                            </c:if>
                            <c:if test="${optionintarif.exclusionOptions.size()>0}">
                        <span class="post" style="color: red; font-weight: bold"> Cannot be connected with:
                    <c:forEach items="${optionintarif.exclusionOptions}" var="exclusion">
                        ${exclusion.name}
                    </c:forEach>
                        </span>
                            </c:if>


                        </div>
                        <svg class="curve2" x="0px" y="0px" width="150px" height="150px" viewBox="0 0 150 50">
                            <path d="M1.114,7.567C87.544-33.817,150,150.5,150,150.5H1.361L1.114,7.567z"/>
                        </svg>
                    </div>
                </a>
                </form>
            </div>

        </c:if>

    </c:forEach>
    </div>


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
