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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pretty-checkbox@3.0/dist/pretty-checkbox.min.css"/>

</head>
<body>
<script language="Javascript">

</script>

<div class="container" style="margin-bottom: 10px">
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
                    <a class="nav-link" href="/startauthempl">Main <span class="sr-only">(current)</span></a>
                </li>


            </ul>
            <a href="${pageContext.request.contextPath}/employee" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>

    <form:form action="${pageContext.request.contextPath}/employee/newTariff" modelAttribute="tariffDto" cssStyle="margin-top: 15px">
        <div class="form-group row">
            <form:label path="tariff" for="inputTariff" cssClass="col-sm-2 col-form-label">Tariff</form:label>
            <div class="col-sm-10">
                <form:input path="tariff" cssClass="form-control" id="inputTariff" required="required"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="price" for="inputPrice" class="col-sm-2 col-form-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" type="number"  pattern="\d{1,4}\\.?\d{0,2}" step="0.01" min="0" max="9999" title="ex. 330.20$"
                            cssClass="form-control" id="inputPrice" required="required" placeholder="хххх.хх"/>
            </div>
        </div>

        <div class="form-group row">

            <div class="col-sm-12 col-md-12">
                <div><h6 style="text-align: center">Select  options that will be available in the tariff.</h6></div>

                <table class="table" id="required options" style="margin-top: 10px">
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">Option</th>
                        <th scope="col">Price</th>
                        <th scope="col">Requirements</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${optionsList}" var="mes" varStatus="loop">
                        <tr>
                            <th scope="row">${loop.count}</th>
                            <td>${mes.name}</td>
                            <td>${mes.price}$</td>
                            <td>
                                <c:if   test="${mes.requiredOptions.size()>0}">
                                    The following options should be connected:
                                    <c:forEach items="${mes.requiredOptions}" var="requirement">
                                        ${requirement.name}
                                    </c:forEach>
                                </c:if>
                                <c:if   test="${mes.exclusionOptions.size()>0}">
                                    <br> Cannot be connected with the following options:
                                    <c:forEach items="${mes.exclusionOptions}" var="exclusion">
                                        ${exclusion.name}
                                    </c:forEach>
                                </c:if>
                            </td>
                            <c:choose>
                                <c:when test="${tariffDto.options.contains(mes)}">
                                    <td>
                                        <div class="pretty p-switch p-fill">
                                        <input id="${mes.name}" type="checkbox" name="opt" value="${mes.id}"
                                               onchange="check(${mes.requirementsId}, this)" checked>
                                        <div class="state">
                                            <label>On</label>
                                        </div>
                                        </div>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <div class="pretty p-switch p-fill">
                                            <input id="${mes.name}" type="checkbox" name="opt" value="${mes.id}"
                                               onchange="check(${mes.requirementsId}, this)"/>
                                            <div class="state">
                                                <label>On</label>
                                            </div>
                                        </div>
                                    </td>
                                </c:otherwise>
                            </c:choose>



                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


            </div>
        </div>


        <div class="form-group row justify-content-end" >
            <div class="col-sm-10 ">
                <form:button type="submit" class="btn btn-secondary"  style="float: right;">Save</form:button>
            </div>
        </div>
    </form:form>

    <c:choose>
        <c:when test="${result=='changes successful'}">
            <div id="message"><p style="margin-top: 10px; color: forestgreen">Change successful! </p></div>
        </c:when>

    <c:otherwise>
        <div id="message"><p style="margin-top: 10px; color: red">${result}</p></div>
    </c:otherwise>
    </c:choose>

    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>

<script>


    /**
     * checks option what parameters have requirements before connecting
     */


    function check(optRequirementsId, checkbox) {


        var elementsInTariffBlock = document.getElementsByName("opt");

        if (checkbox.checked == true) {
            start:
                if ( optRequirementsId.length>0){
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {

                         if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==false){
                             console.log("optRequirementsId = "+ optRequirementsId);
                             console.log("elementsInTariffBlock[i].value = "+ elementsInTariffBlock[i].value);
                             console.log("elementsInTariffBlock[i].id = "+ elementsInTariffBlock[i].id);

                            alert("Before enabling this option, connect following option: " + elementsInTariffBlock[i].id);
                            checkbox.checked=false;
                            break start;
                        }
                    }
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==true){
                            elementsInTariffBlock[i].onclick = function () {
                                return false;
                            }
                        }
                    }
                }

        }
        else {
            stop:
                if ( optRequirementsId.length>0){
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value))){
                            elementsInTariffBlock[i].onclick = function () {
                                return true;
                            }
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
