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
        Mokia
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
            <a href="/logout" class="btn btn-secondary  active" role="button" aria-pressed="true">Sign out</a>


        </div>
    </nav>

    <form action="/find" method="post" style="margin-top: 10px">
        <div class="form-group">
            <input type="tel" mode name="phone" class="form-control" placeholder="Enter client's phone number">
        </div>
        <button type="submit" class="btn btn-secondary ">Search</button>
    </form>

    <form action="/getAllContracts" method="get" style="margin-top: 10px">
        <button type="submit" class="btn btn-secondary  btn-lg btn-block">Get all contracts</button>
    </form>

    <form action="/getAllClients" method="get" style="margin-top: 10px">
        <button type="submit" class="btn btn-secondary  btn-lg btn-block">Get all clients</button>
    </form>

    <button type="submit" class="btn btn-secondary  btn-lg btn-block" data-toggle="collapse"
            data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Sign a new contract
    </button>
    <div class="collapse" id="collapseExample" style="margin-top: 20px">
        <div class="row justify-content-around">

            <form role="form" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">Existing client</button>
            </form>
            <form role="form" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">New client</button>
            </form>
        </div>
    </div>
    <form action="/tariffs" method="get" style="margin-top: 10px">

        <button type="submit" class="btn btn-secondary  btn-lg btn-block">Tariffs</button>
    </form>

    <form action="/options" method="post" style="margin-top: 10px">


        <button type="submit" class="btn btn-secondary  btn-lg btn-block">Options</button>
    </form>
    <c:if test="${clientList !=null}">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">FirstName</th>
                <th scope="col">LastName</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${clientList}" var="mes">
                <tr>
                    <th scope="row">${mes.id}</th>
                    <td>${mes.firstName}</td>
                    <td>${mes.lastName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>


    <c:if test="${tariffsList !=null}">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">tariff</th>
                <th scope="col">price</th>
                <th scope="col">deprecated</th>
                <th scope="col">
                    <a href="/edittariff" class="btn btn-outline-dark btn-sm">new tariff</a>

                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tariffsList}" var="tariff" >

                <tr>
                    <th scope="row">${tariff.id}</th>
                    <td>${tariff.tariff}</td>
                    <td>${tariff.price}</td>
                    <td>${tariff.deprecated}</td>
                    <td><a href="/edittariff?id=${tariff.id} " >Edit</a></td>
<%--                    <td><button type="submit" class="btn btn-secondary btn-sm ">edit</button></td>--%>

                </tr>



            </c:forEach>
            </tbody>
        </table>
    </c:if>


    <div  class="card-footer text-muted" style="margin-top: 20px">
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
