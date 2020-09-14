<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <style type="text/css">
        .row-flex, .row-flex > div[class*='col-'] {
            display: -webkit-box;
            display: -moz-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            flex: 1 1 auto;
        }

        .row-flex-wrap {
            -webkit-flex-flow: row wrap;
            align-content: flex-start;
            flex: 0;
        }

        .row-flex > div[class*='col-'] {
            margin: -.2px;
        }


    </style>
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/">Main <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Tariffs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>


            </ul>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary  active" role="button" aria-pressed="true">Sign in</a>


        </div>
    </nav>

    <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>

        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/images/main_1.jpg" class="d-block w-100"
                     alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5 style="color: black">Family connect.</h5>
                    <p style="color: black">All for loved ones!</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/images/main_2.jpg"
                     class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5>Moving the business.</h5>
                    <p>Be in touch, be in action. Special conditions for business!</p>
                </div>
            </div>

        </div>
        <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <div class="row row-flex row-flex-wrap" style="margin-top: 10px">
        <div class="col-sm-2 col-md-2">
            <div class="card">
                <img src="${pageContext.request.contextPath}/images/private_account2.png" class="card-img-top" alt="...">
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-lg" style="max-width: 100%">Private account</a>
                </div>

            </div>
        </div>
        <div class="col-sm-2 col-md-2">
            <div class="card">
                <img src="${pageContext.request.contextPath}/images/deposit.jpg" class="card-img-top" alt="..." style="">
                <div class="card-body">
                    <a href="#" class="btn btn-lg btn-primary" style="max-width: 100%">Make a deposit</a>
                </div>

            </div>
        </div>
        <div class="col-sm-2 col-md-2">
            <div class="card">
                <img src="${pageContext.request.contextPath}/images/apps.png" class="card-img-top" alt="..." style="">
                <div class="card-body">
                    <a href="#" class="btn btn-primary btn-lg" style="max-width: 110%">Mobile apps</a>
                </div>

            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="card">
                <div class="card-header">
                    Tariff options
                </div>
                <div class="card-body">
                    <h5 class="card-title">Special offers</h5>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula
                        eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur
                        ridiculus mus.
                        Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum
                        semper nisi. Aenean vulputate eleifend tellus.</p>
                    <a href="#" class="btn btn-primary">More...</a>
                </div>
            </div>
        </div>
    </div>

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
