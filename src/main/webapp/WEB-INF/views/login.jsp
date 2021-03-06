<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title>Authorization</title>


</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <span class="navbar-brand" href="#">
        <img src="${pageContext.request.contextPath}/images/label.jpg" width="30" height="30"
             class="d-inline-block align-top" alt="" loading="lazy">
        eCare
    </span>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Main <span class="sr-only">(current)</span></a>
                </li>


            </ul>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>

    </nav>

<form action="${pageContext.request.contextPath}/login/process"  method="post" style="margin-top: 50px; margin-bottom: 50px">

    <div class="form-group">
        <label>Phone number</label>
        <input type="tel"  name="phone" class="form-control" pattern="\d{11}" required="required">
    </div>
    <div class="form-group">
        <label >Password</label>
        <input type="password"  name="password" class="form-control" size="25" required="required">
    </div>
    <button type="submit"  class="btn btn-primary">Sign in</button>

</form>

    <c:if test="${error}" >
        <p> Invalid data </p>
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