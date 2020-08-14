<%@ page import="com.task.dto.OptionDto" %>
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

    <form:form action="${pageContext.request.contextPath}/employee/updateOrDeleteOption" modelAttribute="optionDto" cssStyle="margin-top: 15px">


        <div class="form-group row">
            <form:label path="name" for="inputNameOption" cssClass="col-sm-2 col-form-label">Option</form:label>
            <div class="col-sm-10">
                <form:input path="name"  cssClass="form-control" id="inputNameOption" required="required" />
            </div>
        </div>
        <div class="form-group row">
            <form:label path="price" for="inputPrice" class="col-sm-2 col-form-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" type="number"  pattern="\d{1,4}\\.?\d{0,2}" step="0.01" min="0" max="9999" title="ex. 330.20$" cssClass="form-control" id="inputPrice" required="required" placeholder="хххх.хх" />
            </div>
        </div>
        <div class="form-group row">
            <form:label path="price" for="inputConnectionCost"
                        class="col-sm-2 col-form-label">Connection cost</form:label>
            <div class="col-sm-10">
                <form:input path="connectionCost" type="number"  pattern="\d{1,4}\\.?\d{0,2}" step="0.01" min="0" max="9999" title="ex. 330.20$" cssClass="form-control" id="inputConnectionCost" required="required" placeholder="хххх.хх" />
            </div>
        </div>


        <div class="form-group row">

            <div class="col-sm-6 col-md-6">
                <div><h6 style="text-align: center">Choose required options.</h6></div>

                <table class="table" id="required options">
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">Option</th>
                        <th scope="col">Price</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${optionsList}" var="mes" varStatus="reqnumber">
                        <tr>
                            <th scope="row">${reqnumber.count}</th>
                            <td>${mes.name}</td>
                            <td>${mes.price}$</td>
                            <td><div class="pretty p-switch p-fill">
                                    <input type="checkbox" name="requirement" value="${mes.id}"
                                           onchange="updateCheckBox(this)">
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
            <div class="col-sm-6 col-md-6">
                <div><h6 style="text-align: center">Choose mutual exclusion options.</h6></div>
                <table class="table" id="exclusion options">
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">Option</th>
                        <th scope="col">Price</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${optionsList}" var="mes" varStatus="exclnumber">
                        <tr>
                            <th scope="row">${exclnumber.count}</th>
                            <td>${mes.name}</td>
                            <td>${mes.price}$</td>
                            <td>
                                <div class="pretty p-switch p-fill">
                                    <input type="checkbox" name="exclusion" value="${mes.id}"
                                           onchange="updateCheckBox2(this)">
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
        </div>
<c:choose>
    <c:when test="${optionDto.deleted!=true}">
    <div class="form-group row justify-content-end">
        <div class="col-sm-10 ">
            <form:button type="submit" class="btn btn-secondary" style="float: right">Save</form:button>
            <c:if test="${optionDto.id!=null}">
                <form:button type="submit" name="delete"
                             onclick="return confirm('Option: ${optionDto.id} ${optionDto.name} will be deleted. Are you sure?')"
                             class="btn btn-secondary" style="float: right; margin-right: 5px">Delete</form:button>
            </c:if>
        </div>
    </div>
    </c:when>
    <c:otherwise>
        <div class="form-group row justify-content-end">
            <div class="col-sm-10 ">
                <form:button type="submit" class="btn btn-secondary" style="float: right" disabled="true">Save</form:button>
                <c:if test="${optionDto.id!=null}">
                    <form:button type="submit" name="delete"
                                 onclick="return confirm('Option: ${optionDto.id} ${optionDto.name} will be deleted. Are you sure?')"
                                 class="btn btn-secondary" style="float: right; margin-right: 5px" disabled="true">Delete</form:button>
                </c:if>
            </div>
        </div>

    </c:otherwise>


</c:choose>

    </form:form>

    <c:if test="${result=='Changes successful.'}">
        <div id="message"><p style="margin-top: 10px; color: forestgreen">Change successful! </p></div>
    </c:if>
        <c:if test="${result !='Changes successful.' && result.length()>0}">
            <div id="message"><p style="margin-top: 10px; color: red">${result}</p></div>
        </c:if>



    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>


<script>



    /**
     * Check Set<Options> from an OptionDto. If OptionDto has some kind of restriction, the onload () function marks it on the checkboxes
     */

    function onload() {

        <c:if test="${optionDto.requiredOptions.size()>0}">
        <c:forEach items="${optionDto.requiredOptions}" var="req">
        var requiredOptions = "${req.id}"
        var reqchek = document.getElementsByName("requirement");

        for (var i = 0; i <= reqchek.length - 1; i++) {
            if (reqchek[i].value == requiredOptions) {
                reqchek[i].checked = true;
                updateCheckBox(reqchek[i]);
            }

        }


        </c:forEach>
        </c:if>

        <c:if test="${optionDto.exclusionOptions.size()>0}">
        <c:forEach items="${optionDto.exclusionOptions}" var="exc">
        var exclusionOptions = "${exc.id}"
        var excl = document.getElementsByName("exclusion");

        for (let i = 0; i <= excl.length - 1; i++) {
            if (excl[i].value == exclusionOptions) {
                excl[i].checked = true;
                updateCheckBox2(excl[i]);
            }

        }

        </c:forEach>
        </c:if>


    }

    window.onload();


    function updateCheckBox(chek) {
        var exclChek = document.getElementsByName("exclusion");
        for (let i = 0; i <= exclChek.length - 1; i++) {
            if (exclChek[i].value == chek.value && chek.checked == true) {
                exclChek[i].checked = false;
                exclChek[i].disabled = true;
            } else if (exclChek[i].value == chek.value && chek.checked == false) {

                exclChek[i].disabled = false;
            }

        }
    }

    function updateCheckBox2(chek2) {
        var reqChek = document.getElementsByName("requirement");
        for (let i = 0; i <= reqChek.length - 1; i++) {
            if (reqChek[i].value == chek2.value && chek2.checked == true) {
                reqChek[i].checked = false;
                reqChek[i].disabled = true;
            } else if (reqChek[i].value == chek2.value && chek2.checked == false) {

                reqChek[i].disabled = false;
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