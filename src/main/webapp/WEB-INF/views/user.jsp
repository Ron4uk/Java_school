<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>


    </style>
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
                    <a class="nav-link" href="/startauthclient">Main<span class="sr-only">(current)</span></a>
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
            <a href="/logout" class="btn btn-secondary  active" role="button" aria-pressed="true">Sign out</a>


        </div>
    </nav>
    <div class="row" style="margin-top: 20px; margin-bottom: 20px">
        <div class="col-md-4">
            <h2>Hello, ${contractDto.clientDto.firstName}!</h2>
        </div>
        <div class="col-md-6"></div>
        <div class="col-md-2 ">
            <c:choose>
                <c:when test="${contractDto.blockByOperator==true || contractDto.blockByClient==true}">
                    <div class="badge badge-danger text-wrap" style="width: 10rem;">
                        Your tariff:
                        <br>${contractDto.tariffDto.tariff}
                        <br>Monthly payment:
                        <br>${contractDto.tariffDto.price}$
                        <br>Your phone number is blocked
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="badge badge-success text-wrap" style="width: 10rem;">
                        Your tariff:
                        <br>${contractDto.tariffDto.tariff}
                        <br>Monthly payment:
                        <br>${contractDto.tariffDto.price}$
                    </div>
                </c:otherwise>

            </c:choose>
        </div>
    </div>


    <div class="row" style="margin-top: 100px">
        <div class="col-sm-1 col-md-1">
        </div>
        <div class=" col-sm-4 col-md-4">
            <div class="card" style="width: 18rem;">
                <img src="/images/newTariff2.png" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">Tariffs</h5>
                    <p class="card-text">Choose new tariff and enjoy!</p>
                    <a href="/userchoosenewtariff" id="checkOnBlock" class="btn btn-primary">Change tariff</a>
                </div>
            </div>
        </div>
        <div class="col-sm-2 col-md-2">
        </div>
        <div class="col-sm-4 col-md-4 float-right">
            <div class="card" style="width: 18rem;">
                <img src="/images/setup.png" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">Options</h5>
                    <p class="card-text">Connect new options!</p>
                    <a href="/usermanageoption" class="btn btn-primary">Manage options</a>
                </div>
            </div>
        </div>
        <div class="col-sm-1 col-md-1">
        </div>
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
