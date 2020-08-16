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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pretty-checkbox@3.0/dist/pretty-checkbox.min.css"/>
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
            <a href="${pageContext.request.contextPath}/user/userchoosenewtariff?id=${contractDto.id}"
               class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>

    <h4 style="margin-top: 10px">Choose options</h4>
    <form:form action="${pageContext.request.contextPath}/user/addoptiontotariff" modelAttribute="orderDto"
               cssStyle="margin-top: 15px" method="post">
        <c:forEach items="${orderDto.tariffDto.options}" var="option">
            <c:if test="${option.deleted!=true}">
            <div class="border-bottom row" style="margin-top: 5px">
                <div class="col-md-1"></div>
                <div class="col-md-5">
                    <form:label path="">${option.name}
                        <br>Connection cost: ${option.connectionCost}$
                        <br>Monthly cost: ${option.price}$
                        <c:if test="${option.requirementsId.size()>0}">
                            <br>The following options should be connected:
                            <c:forEach items="${option.requirementsId}" var="requirement">
                                <c:set value="${orderDto.tariffDto.options.stream().filter(opt->(opt.id).equals(requirement)).findFirst().orElse(null) }"
                                       var="reqOpt"/>
                                <c:if test="${reqOpt!=null}">
                                    ${reqOpt.name}
                                </c:if>


                            </c:forEach>
                        </c:if>
                        <c:if test="${option.exclusionsId.size()>0}">
                            <br> Cannot be connected with the following options:
                            <c:forEach items="${option.exclusionsId}" var="exclusion">
                                <c:set value="${orderDto.tariffDto.options.stream().filter(e->(e.id).equals(exclusion)).findFirst().orElse(null) }"
                                       var="excOpt"/>
                                <c:if test="${excOpt!=null}">
                                    ${excOpt.name}
                                </c:if>


                            </c:forEach>
                        </c:if>
                    </form:label>
                </div>
                <div class="col-md-6">
                    <br>
                    <div class="pretty p-switch p-fill">
                        <form:checkbox path="optionsFromNewTariff" id="${option.name}" value="${option.id}"
                                       onchange="check(${option.requirementsId},${option.exclusionsId},this)"/>
                        <div class="state">
                            <label>On</label>
                        </div>
                    </div>

                </div>
            </div>
            </c:if>
        </c:forEach>

        <div class="form-group row justify-content-end" style="margin-top: 5px">
            <div class="col-sm-10 ">
                <form:button type="submit" class="btn btn-secondary" style="float: right">Add</form:button>
            </div>

        </div>

    </form:form>


    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>


<script>
    var mapExclusions = new Map();
    var mapRequirements = new Map();

    /**
     * checks option what parameters have requirements before connecting
     *
     */


    function check(optRequirementsId, optExclusionsId, checkbox) {

        var elementsInTariffBlock = document.getElementsByName("optionsFromNewTariff");
        console.log(elementsInTariffBlock);
        if (checkbox.checked == true) {
            start:
                if (optExclusionsId.length > 0 || optRequirementsId.length > 0) {
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if (optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked == true) {
                            alert("You cannot enable this option, because " + elementsInTariffBlock[i].id
                                + " is mutually exclusive with " + checkbox.id);
                            checkbox.checked = false;
                            break start;
                        } else if (optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked == false) {
                            alert("Before enabling this option, connect following option: " + elementsInTariffBlock[i].id);
                            checkbox.checked = false;
                            break start;
                        }
                    }
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if (optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked == false) {
                            if (mapExclusions.has((elementsInTariffBlock[i].value))) {
                                let number = mapExclusions.get((elementsInTariffBlock[i].value));
                                mapExclusions.set((elementsInTariffBlock[i].value), ++number);
                            } else {
                                mapExclusions.set((elementsInTariffBlock[i].value), 1);
                            }
                            elementsInTariffBlock[i].disabled = true;
                        } else if (optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked == true) {
                            if (mapRequirements.has((elementsInTariffBlock[i].value))) {
                                let number = mapRequirements.get((elementsInTariffBlock[i].value));
                                mapRequirements.set((elementsInTariffBlock[i].value), ++number);
                            } else {
                                mapRequirements.set((elementsInTariffBlock[i].value), 1);
                            }
                            elementsInTariffBlock[i].onclick = function () {
                                return false;
                            }
                        }
                    }

                }

        } else {
            stop:
                if (optExclusionsId.length > 0 || optRequirementsId.length > 0) {
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if (optRequirementsId.includes(Number(elementsInTariffBlock[i].value))) {
                            let number = mapRequirements.get((elementsInTariffBlock[i].value));
                            mapRequirements.set((elementsInTariffBlock[i].value), --number);
                            if (mapRequirements.get((elementsInTariffBlock[i].value)) == 0) {
                                elementsInTariffBlock[i].onclick = function () {
                                    return true;
                                }
                            }
                        }
                        if (optExclusionsId.includes(Number(elementsInTariffBlock[i].value))) {
                            let number = mapExclusions.get((elementsInTariffBlock[i].value));
                            mapExclusions.set((elementsInTariffBlock[i].value), --number);
                            if (mapExclusions.get((elementsInTariffBlock[i].value)) == 0) elementsInTariffBlock[i].disabled = false;
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