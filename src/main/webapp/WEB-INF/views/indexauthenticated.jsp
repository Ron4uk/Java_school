<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<!DOCYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <style type="text/css">
        .row-flex, .row-flex > div[class*='col-']
        {  display: -webkit-box;
            display: -moz-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            flex:1 1 auto; }
        .row-flex-wrap
        {    -webkit-flex-flow: row wrap;
            align-content: flex-start;
            flex:0; }
        .row-flex > div[class*='col-'] {  margin:-.2px; }
    </style>
</head>

<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <span class="navbar-brand" href="#">
        <img src="https://dzertv.ru/wp-content/uploads/2018/10/planetarii25_10_18.jpg" width="30" height="30"
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
                    <a class="nav-link" href="#">Главная <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Тарифы и услуги</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Частным клиентам
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="#">Бизнесу</a>
                        <a class="dropdown-item" href="#">О компании</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Обратная связь</a>
                    </div>
                </li>

            </ul>
            <a href="/logout" class="btn btn-secondary  active" role="button" aria-pressed="true">Выход</a>


        </div>
    </nav>

    <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>

        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="https://www.apploder.xyz/wp-content/uploads/2019/05/Untitled-copy-2.jpg" class="d-block w-100"
                     alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5 style="color: black">Семейный коннект.</h5>
                    <p style="color: black">Все для близких!</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="https://paratic.com/dosya/2016/07/yatirimcilar-forex-piyasasindaki-riskleri-onlemek-icin-nasil-dusunmeli.jpg"
                     class="d-block w-100" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h5>Двигаем бизнес.</h5>
                    <p>Быть на связи, быть в деле. Особые условия для бизнеса!</p>
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
                <img src="https://teatravel.ru/local/templates/.default/images/icons/lk.png" class="card-img-top" alt="...">
                <div class="card-body">
                    <a href="#" class="btn btn-primary" style="max-width: 100%">Личный кабинет</a>
                </div>

            </div>
        </div>
        <div class="col-sm-2 col-md-2">
            <div class="card">
                <img src="https://image.winudf.com/v2/image1/Y29tLm5qYXBwcy5vbmxpbmVmcmVlcmVjaGFyZ2VfaWNvbl8xNTQ4MzU3NzM3XzA2Ng/icon.png?w=100&fakeurl=1" class="card-img-top" alt="..." style="padding-top: 5px">
                <div class="card-body">
                    <a href="#" class="btn btn-primary" style="max-width: 100%">Пополнить баланс</a>
                </div>

            </div>
        </div>
        <div class="col-sm-2 col-md-2" >
            <div class="card" >
                <img src="https://lh5.ggpht.com/RCzJaB3vx-1e5fUrPFxxpjSBYGAVf57UhMm4WOjfJhD7WCl8H-TnlxNRT9jsXdvkvw=w100" class="card-img-top" alt="..." style="padding-top: 5px">
                <div class="card-body">
                    <a href="#" class="btn btn-primary" style="max-width: 110%">Мобильные приложения</a>
                </div>

            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="card">
                <div class="card-header">
                    Тарфиные опции
                </div>
                <div class="card-body">
                    <h5 class="card-title">Специальные предложения</h5>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.
                        Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus.</p>
                    <a href="#" class="btn btn-primary">Подробнее</a>
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
