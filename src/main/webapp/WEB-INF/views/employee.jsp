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

    <form:form action="/findClientOnMainPage" method="post" modelAttribute="contractDto" style="margin-top: 10px">
        <div class="form-group">
            <form:input type="tel" path="phone" name="phone" class="form-control" placeholder="Enter client's phone number"/>
        </div>
        <button type="submit" class="btn btn-secondary ">Search</button>
    </form:form>

    <form action="/getAllContracts" method="get" style="margin-top: 10px">
        <button type="submit" class="btn btn-secondary  btn-lg btn-block">Get all contracts</button>
    </form>

    <form action="/getAllClients" method="get" style="margin-top: 10px">
        <button type="submit" class="btn btn-secondary  btn-lg btn-block">Get all clients</button>
    </form>

    <button type="submit" class="btn btn-secondary  btn-lg btn-block" data-toggle="collapse"
            data-target="#clients" aria-expanded="false" aria-controls="collapseExample">Sign a new contract
    </button>
    <div class="collapse" id="clients" style="margin-top: 20px">
        <div class="row justify-content-around">

            <form role="form" action="/chooseclient" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">Existing client</button>
            </form>
            <form role="form" action="/newclient" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">New client</button>
            </form>
        </div>
    </div>
    <button type="submit" class="btn btn-secondary  btn-lg btn-block" data-toggle="collapse"
            data-target="#tarrifs" aria-expanded="false" aria-controls="tarrifs" style="margin-top: 10px">Tariffs
    </button>
    <div class="collapse" id="tarrifs" style="margin-top: 20px">
        <div class="row justify-content-around">
            <form role="form" action="/edittariff" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">New tariff</button>
            </form>
            <form role="form" action="/tariffs" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">Show all</button>
            </form>
        </div>
    </div>

    <button type="submit" class="btn btn-secondary  btn-lg btn-block" data-toggle="collapse"
            data-target="#options" aria-expanded="false" aria-controls="options" style="margin-top: 10px">Options
    </button>
    <div class="collapse" id="options" style="margin-top: 20px">
        <div class="row justify-content-around">
            <form role="form" action="/editoption"  class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">New option</button>
            </form>
            <form role="form" action="/options" class="col-md-2" method="get">
                <button type="submit" class="btn btn-secondary  btn ">Show all</button>
            </form>
        </div>
    </div>
    <c:if test="${contract !=null}">

        <table class="table">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">FirstName</th>
                <th scope="col">LastName</th>
                <th scope="col">Tariff</th>
                <th scope="col">Phone number</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>

                <tr>
                    <th scope="row">${contract.clientDto.id}</th>
                    <td>${contract.clientDto.firstName}</td>
                    <td>${contract.clientDto.lastName}</td>
                    <td>${contract.tariffDto.tariff}</td>
                    <td>${contract.phone}</td>
                    <td><a href="/editcontract?id=${contract.id} ">Edit</a></td>
                    <c:if test="${contract.blockByOperator!=true && contract.blockByClient!=true}">
                        <form method="post" action="/blockcontract">
                        <td><button  type="submit" name="block" class="btn btn-outline-dark btn-sm" value="${contract.id}"> block </button></td>
                        </form>
                    </c:if>
                    <c:if test="${contract.blockByOperator==true || contract.blockByClient==true }">
                        <form method="post" action="/unblockcontract">
                        <td><button  type="submit" name="unblock" class="btn btn-outline-danger btn-sm" value="${contract.id}">unblock</button></td>
                        </form>
                    </c:if>
                </tr>

            </tbody>
        </table>
    </c:if>


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

    <c:if test="${tariffsList.size() ==0}">
        <p>No tariffs</p>
    </c:if>
    <c:if test="${tariffsList !=null}">

        <table class="table">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">tariff</th>
                <th scope="col">price</th>
                <th scope="col"></th>
                <th scope="col">
                    <a href="/edittariff" class="btn btn-outline-dark btn-sm">new tariff</a>

                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tariffsList}" var="tariff">

                <tr>
                    <th scope="row">${tariff.id}</th>
                    <td>${tariff.tariff}</td>
                    <td>${tariff.price}</td>
                    <td><a href="/edittariff?id=${tariff.id} ">Edit</a></td>
                    <td><a href="/deletetariff?id=${tariff.id} ">Delete</a></td>


                </tr>


            </c:forEach>
            </tbody>
        </table>

    </c:if>


        <c:if test="${optionsList.size() ==0}">
            <p>No options</p>
        </c:if>

    <c:if test="${optionsList !=null}">
        <table class="table " >
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">price</th>
                <th scope="col">connection cost</th>
                <th scope="col">
                    <a href="/editoption" class="btn btn-outline-dark btn-sm">new option</a>

                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${optionsList}" var="option">


                <tr>
                    <th scope="row">${option.id}</th>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                    <td><a href="/editoption?id=${option.id} ">Edit</a></td>


                </tr>


            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${change!=null}">
        <div id="message"><p style="margin-top: 10px; color: forestgreen" >Change successful! </p></div>
    </c:if>

    <c:if test="${error!=null}">
        <div id="message" ><p style="margin-top: 10px; color: red" >Change failed! </p></div>
    </c:if>
    <c:if test="${result!=null}">
        <div id="message" ><p style="margin-top: 10px; color: green" >${result}</p></div>
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
