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
            <a href="${pageContext.request.contextPath}${path}" class="btn btn-secondary  active" role="button"
               aria-pressed="true">Back</a>


        </div>
    </nav>
    <div class="form-group row">

        <div class="col-sm-12" style="margin-top: 10px">
            <h5>Draw up a new contract for the
                client: ${clientDto.id} ${clientDto.firstName} ${clientDto.lastName}</h5>
        </div>
    </div>

    <form:form action="${pageContext.request.contextPath}/employee/newcontract" modelAttribute="contractDto"
               cssStyle="margin-top: 15px">
        <div class="form-group row">
            <form:label path="phone" for="inputPhoneNumber" cssClass="col-sm-2 col-form-label">Phone Number</form:label>
            <div class="col-sm-10">
                <form:input path="phone" pattern="\d{11}" cssClass="form-control" id="inputPhoneNumber"
                            required="required" placeholder="ex. 89875643210"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="auth.password" for="inputPassword"
                        cssClass="col-sm-2 col-form-label">Password</form:label>
            <div class="col-sm-10">
                <form:input type="password" path="auth.password" cssClass="form-control" id="inputPassword"
                            required="required"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="confirmPassword" class="col-sm-2 col-form-label">Confirm password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="confirmPassword" oninput="checkPassword(this)"
                       required="required"/>
            </div>
        </div>
        <div id="optionsForTariff">
            <c:if test="${listTariffs !=null}">
                <c:forEach items="${listTariffs}" var="tariffs">
                    <div class="form-check" style="margin-bottom: 10px">
                        <div class="pretty p-default p-round">
                            <input class="form-check-input" type="radio" data-toggle="collapse" aria-expanded="false"
                                   aria-controls="${tariffs.tariff}"
                                   data-target="#${tariffs.tariff}" name="chosentariff" value="${tariffs.id}"
                                   required="required">
                            <div class="state">
                                <label>${tariffs.tariff}</label>
                            </div>
                        </div>
                    </div>
                    <div id="${tariffs.tariff}" class="collapse" data-parent="#optionsForTariff">

                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">№</th>
                                <th scope="col">Option</th>
                                <th scope="col">Price</th>
                                <th scope="col">Connection cost</th>
                                <th scope="col">Requirements</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tariffs.options}" var="option" varStatus="optioncount">
                                <tr>
                                    <th scope="row">${optioncount.count}</th>
                                    <td>${option.name}</td>
                                    <td>${option.price}$</td>
                                    <td>${option.connectionCost}$</td>
                                    <td>
                                        <c:if test="${option.requiredOptions.size()>0}">
                                            The following options should be connected:
                                            <c:forEach items="${option.requiredOptions}" var="requirement">
                                                ${requirement.name}
                                            </c:forEach>
                                            <br>
                                        </c:if>
                                        <c:if test="${option.exclusionOptions.size()>0}">
                                            Cannot be connected with the following options:
                                            <c:forEach items="${option.exclusionOptions}" var="exclusion">
                                                ${exclusion.name}
                                            </c:forEach>
                                        </c:if>

                                    </td>
                                    <td>
                                        <div class="pretty p-switch p-fill">
                                            <input type="checkbox" name="${tariffs.id}" id="${option.name}"
                                                   onchange="check(${option.requirementsId},
                                                       ${option.exclusionsId}, ${tariffs.id}, this)"
                                                   value="${option.id}">
                                            <div class="state">
                                                <label>On</label>
                                            </div>
                                        </div>


                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </c:forEach>
            </c:if>
        </div>

        <div class="form-group row justify-content-end">
            <div class="col-sm-10 ">
                <form:button type="submit" class="btn btn-secondary" style="float: right">Save</form:button>
            </div>
        </div>
    </form:form>


    <c:choose>
        <c:when test="${result.equals('Change failed. Check connection options')}">
            <div id="message"><p style="margin-top: 10px; color: red">${result}</p></div>
        </c:when>
        <c:otherwise>
            <div id="message"><p style="margin-top: 10px; color: green">${result}</p></div>
        </c:otherwise>
    </c:choose>


    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>
<script>
    var mapExclusions = new Map();
    var mapRequirements = new Map();

    function checkPassword(input) {

        if (input.value != document.getElementById('inputPassword').value) {
            input.setCustomValidity('Password Must be Matching.');
        } else {
            input.setCustomValidity('');
        }
    }


    /**
     * checks option what parameters have requirements before connecting

     */


    function check(optRequirementsId, optExclusionsId, tariff, checkbox) {


        var elementsInTariffBlock = document.getElementsByName(tariff);
        if (checkbox.checked == true) {
            start:
                if (optExclusionsId.length > 0 || optRequirementsId.length > 0) {
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if (optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked == true) {
                            alert("You cannot enable this option, because " + elementsInTariffBlock[i].id
                                + " is mutually exclusive with  " + checkbox.id);
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
                            if (mapExclusions.has((elementsInTariffBlock[i].value) + " " + tariff)) {
                                let number = mapExclusions.get((elementsInTariffBlock[i].value) + " " + tariff);
                                mapExclusions.set((elementsInTariffBlock[i].value) + " " + tariff, ++number);
                            } else {
                                mapExclusions.set((elementsInTariffBlock[i].value) + " " + tariff, 1);
                            }
                            elementsInTariffBlock[i].disabled = true;
                        } else if (optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked == true) {

                            if (mapRequirements.has((elementsInTariffBlock[i].value) + " " + tariff)) {
                                let number = mapRequirements.get((elementsInTariffBlock[i].value) + " " + tariff);
                                mapRequirements.set((elementsInTariffBlock[i].value) + " " + tariff, ++number);
                            } else {
                                mapRequirements.set((elementsInTariffBlock[i].value) + " " + tariff, 1);
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
                            let number = mapRequirements.get((elementsInTariffBlock[i].value) + " " + tariff);
                            mapRequirements.set((elementsInTariffBlock[i].value) + " " + tariff, --number);

                            if (mapRequirements.get((elementsInTariffBlock[i].value) + " " + tariff) == 0) {
                                elementsInTariffBlock[i].onclick = function () {
                                    return true;
                                }
                            }


                        }
                        if (optExclusionsId.includes(Number(elementsInTariffBlock[i].value))) {
                            let number = mapExclusions.get((elementsInTariffBlock[i].value) + " " + tariff);
                            mapExclusions.set((elementsInTariffBlock[i].value) + " " + tariff, --number);
                            console.log("optExclusionsId.includes -- " + mapExclusions)
                            if (mapExclusions.get((elementsInTariffBlock[i].value) + " " + tariff) == 0) elementsInTariffBlock[i].disabled = false;

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
