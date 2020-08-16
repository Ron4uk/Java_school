<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>Unexpected Error</title>
    <link href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400" rel="stylesheet">
    <link href="/resources/css/500.css" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400" rel="stylesheet">
<body class="loading">
<h1>500</h1>
<h2>Unexpected Error <b>:(</b></h2>
<h2><p>You can go back to  <a href="/">Homepage</a>.</p></h2>
<div class="gears">
    <div class="gear one">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
    <div class="gear two">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
    <div class="gear three">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>

<script>
    $(function() {
        setTimeout(function(){
            $('body').removeClass('loading');
        }, 1000);
    });
</script>
</body>
</html>