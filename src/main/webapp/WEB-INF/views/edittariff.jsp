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
<script language="Javascript">

</script>

<div class="container" style="margin-bottom: 10px">
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
            <a href="/employee"  class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>

        <form:form   action="/newTariff" modelAttribute="tariffDto" cssStyle="margin-top: 15px">
            <div class="form-group row">
                <form:label path="tariff" for="inputTariff" cssClass="col-sm-2 col-form-label">Tariff</form:label>
                <div class="col-sm-10">
                    <form:input path="tariff" cssClass="form-control" id="inputTariff" required="required"/>
                </div>
            </div>
            <div class="form-group row">
                <form:label path="price" for="inputPrice" class="col-sm-2 col-form-label">Price</form:label>
                <div class="col-sm-10">
                    <form:input path="price"   cssClass="form-control" id="inputPrice" required="required"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-2">Status</div>
                <div class="col-sm-10">
                    <div class="form-check">
                        <form:checkbox path="deprecated" cssClass="form-check-input"  id="status"/>
                        <form:label path="deprecated" class="form-check-label" for="status">
                            Deprecated
                        </form:label>
                    </div>
                </div>
            </div>
            <div class="form-group row justify-content-end">
                <div class="col-sm-10 ">
                    <form:button type="submit"  class="btn btn-secondary">Save</form:button>
                </div>
            </div>
        </form:form>

    <c:if test="${change!=null}">
        <div id="message"><p style="margin-top: 10px; color: forestgreen" >Change successful! </p></div>
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
