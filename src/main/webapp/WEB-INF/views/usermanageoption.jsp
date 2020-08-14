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

    <h4 style="margin-top: 10px">Connected options</h4>
    <c:forEach items="${contractDto.connectedOptions}" var="connectedoption">

        <div class="border-bottom row" style="margin-top: 5px">
            <div class="col-md-1"></div>
            <div class="col-md-5">
                <label>${connectedoption.name}
                    <br>Connection cost: ${connectedoption.connectionCost}$
                    <br>Monthly cost: ${connectedoption.price}$

                    <c:if   test="${connectedoption.requiredOptions.size()>0}">
                    <br>The following options should be connected:
                    <c:forEach items="${connectedoption.requiredOptions}" var="requirement">
                        ${requirement.name}
                    </c:forEach>
                    </c:if>
                    <c:if   test="${connectedoption.exclusionOptions.size()>0}">
                    <br> Cannot be connected with the following options:
                    <c:forEach items="${connectedoption.exclusionOptions}" var="exclusion">
                        ${exclusion.name}
                    </c:forEach>
                    </c:if>


                    <label>
            </div>
            <div class="col-md-6">
                <br>
                <label>
                    <input type="checkbox" id="${connectedoption.name}" name="optionsFromCurTariff" value="${connectedoption.id}" checked disabled/>
                </label>
            </div>
        </div>
    </c:forEach>

    <h4 style="margin-top: 10px">Available options</h4>
    <form:form action="/changecurrentoptions" modelAttribute="orderDto" cssStyle="margin-top: 15px" method="post">
        <c:forEach items="${contractDto.tariffDto.options}" var="option">
            <c:if test="${!contractDto.connectedOptions.contains(option)}">
                <div class="border-bottom row" style="margin-top: 5px">
                    <div class="col-md-1"></div>
                    <div class="col-md-5">
                        <form:label path="optionsFromCurTariff" for="${option.id}">${option.name}
                            <br>Connection cost: ${option.connectionCost}$
                            <br>Monthly cost: ${option.price}$

                            <c:if   test="${option.requiredOptions.size()>0}">
                                <br>The following options should be connected:
                                <c:forEach items="${option.requiredOptions}" var="requirement">
                                    ${requirement.name}
                                </c:forEach>
                            </c:if>
                            <c:if   test="${option.exclusionOptions.size()>0}">
                                <br> Cannot be connected with the following options:
                                <c:forEach items="${option.exclusionOptions}" var="exclusion">
                                    ${exclusion.name}
                                </c:forEach>
                            </c:if>



                        </form:label>
                    </div>
                    <div class="col-md-6">
                        <br>
                        <c:choose>
                            <c:when test="${orderDto.optionsFromCurTariff.contains(option)}">
                                <form:checkbox path="optionsFromCurTariff" id="${option.name}" value="${option.id}" checked="checked" onchange="check(${option.requirementsId},${option.exclusionsId},this)"></form:checkbox>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox path="optionsFromCurTariff" id="${option.name}" value="${option.id}" onchange="check(${option.requirementsId},${option.exclusionsId},this)"></form:checkbox>
                            </c:otherwise>
                        </c:choose>


                    </div>
                </div>
            </c:if>
        </c:forEach>

        <div class="form-group row justify-content-end" style="margin-top: 5px">
            <div class="col-sm-10 ">
                <a href="/userdisableoptions" type="button"  style="margin-right: 5px" class="btn btn-secondary">Disable options</a>
                <form:button type="submit" class="btn btn-secondary" style="float: right">Save</form:button>
            </div>
        </div>
    </form:form>
    <c:if test="${orderDto.tariffDto!=null}">
        <div style="color: red">
            NOTE: If you add or disable options, selected new tariff and options will be removed from the shopping cart.
        </div>

    </c:if>


    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>

</div>
<script>
    var amountSlctOnExclOptions=0;
    /**
     * checks option what parameters have requirements before connecting
     *  instance amountSlctOnExclOptions increase when another option have the same mutual exclusion option.
     *  when option will be disconnected and amountSlctOnExclOptions !=0, this means that exclusion options should not be enabled.
     */


    function check(optRequirementsId, optExclusionsId, checkbox) {

        var elementsInTariffBlock = document.getElementsByName("optionsFromCurTariff");
        for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
            console.log(elementsInTariffBlock[i].value);

        }

        if (checkbox.checked == true) {
            start:
                if (optExclusionsId.length > 0 || optRequirementsId.length>0){
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==true){
                            alert("You cannot enable this option, because " +elementsInTariffBlock[i].id
                                + " is mutually exclusive with "+checkbox.id);
                            checkbox.checked = false;
                            break start;
                        }
                        else if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==false){
                            alert("Before enabling this option, connect following option: " + elementsInTariffBlock[i].id);
                            checkbox.checked=false;
                            break start;
                        }
                    }
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==false){
                            amountSlctOnExclOptions++;
                            elementsInTariffBlock[i].disabled=true;
                        }
                        else if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==true){
                            elementsInTariffBlock[i].onclick = function () {
                                return false;
                            }
                        }
                    }

                }

        }
        else {
            stop:
                if (optExclusionsId.length > 0 || optRequirementsId.length>0){
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value))){
                            elementsInTariffBlock[i].onclick = function () {
                                return true;
                            }
                        }
                        if(optExclusionsId.includes(Number(elementsInTariffBlock[i].value))){
                            if(--amountSlctOnExclOptions==0) elementsInTariffBlock[i].disabled=false;
                            console.log("check amountSlctOnExclOptions after disable "+amountSlctOnExclOptions)
                        }
                    }

                }
        }

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