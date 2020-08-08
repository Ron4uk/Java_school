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
            <a href="/user?id=${contractDto.id}" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>
    <form:form action="/addtarifftoorder" modelAttribute="orderDto" cssStyle="margin-top: 15px" method="post">
        <div class="form-group row">
            <form:label path="tariffDto" for="editTariff" cssClass="col-sm-2 col-form-label">Tariff</form:label>
            <div class="col-sm-10">

                <form:select path="tariffDto" class="form-control" id="selectmenu" required="required" onchange="toggle(this)" >
                    <form:option value="${null}" id="forHide">Choose tariff</form:option>
                    <c:forEach items="${listTariffs}" var="tariff">
                        <form:option value="${tariff.id}" >${tariff.tariff}</form:option>

                    </c:forEach>
                </form:select>
            </div>
        </div>
        <c:forEach items="${listTariffs}" var="tariff">
            <div style="display: none" id="${tariff.id}">
                <h3>Monthly cost - ${tariff.price}$</h3>
                <p>Available options:</p>

                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Option</th>
                        <th scope="col">Monthly cost</th>
                        <th scope="col">Connection cost</th>
                        <th scope="col">Requirements</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${tariff.options}" var="option">
                        <tr>
                            <th scope="row">${option.name}</th>
                            <td>${option.price}$</td>
                            <td>${option.connectionCost}$</td>
                            <td>
                                <c:if test="${option.requirementsId.size()>0}">
                                    The following options should be connected:
                                    <c:forEach items="${option.requirementsId}" var="requirement">
                                        <c:set value="${tariff.options.stream().filter(opt->(opt.id).equals(requirement)).findFirst().orElse(null) }" var="reqOpt"/>
                                        <c:if test="${reqOpt!=null}">
                                            ${reqOpt.name}
                                        </c:if>



                                    </c:forEach>
                                </c:if>
                                <c:if test="${option.exclusionsId.size()>0}">
                                   <br> Cannot be connected with the following  options:
                                    <c:forEach items="${option.exclusionsId}" var="exclusion">
                                        <c:set value="${tariff.options.stream().filter(e->(e.id).equals(exclusion)).findFirst().orElse(null) }" var="excOpt"/>
                                        <c:if test="${excOpt!=null }">
                                            ${excOpt.name}
                                        </c:if>


                                    </c:forEach>
                                </c:if>

                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:forEach>


        <div class="form-group row justify-content-end">
            <div class="col-sm-10 ">
                <form:button type="submit" class="btn btn-secondary" style="float: right">Add</form:button>
            </div>
        </div>
    </form:form>
    <c:if test="${orderDto.optionsFromCurTariff.size()>0}">
        <div style="color: red">
            NOTE: If you add new tariff, selected options will be removed from the shopping cart.
        </div>

    </c:if>




    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>
<script>
    var previoustariff=null;
    function toggle(sel) {
        $("#forHide").remove();
        let id = sel.options[sel.selectedIndex].value;
        var tariff = document.getElementById(id);

        if (tariff.style.display == 'none') {
            tariff.style.display = 'block';
        }
        else {
            tariff.style.display = 'none';
        }
        if(previoustariff!=null){
            previoustariff.style.display = 'none';
        }
        previoustariff=tariff;
    }
</script>
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