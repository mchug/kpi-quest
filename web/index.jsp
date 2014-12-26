<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>KPI Quest v2.0</title>
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="css/quest.css" rel="stylesheet">

    </head>

    <body>
        <header>            
            <nav class="navbar navbar-default quest-navbar">
                <div class="container">
                    <div class="navbar-left">
                        <div class="navbar-brand">
                            Ваше имя будет тут
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="navbar-right">
                            <div class="navbar-text">
                                Войдите используя социальные сети                            
                            </div>
                            <div class="navbar-nav">
                                <button type="button" class="btn btn-primary navbar-btn" onclick=""><b>Вконтакте</b></button>
                                <button type="button" class="btn btn-danger navbar-btn"><b>Google</b></button>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <div class="container">
                <h4>Тут будет показан твой прогресс.</h4>                    
                <div class="progress">                    
                    <div class="progress-bar progress-bar-striped" role="progressbar" 
                         aria-valuenow="60" aria-valuemin="0" 
                         aria-valuemax="100" style="width: 20%;">
                        Как-то так
                    </div>
                </div>
            </div><!-- /.container -->
        </header>        
        <div class="container">
            <div class="text-center">
                <h1>KPI Quest v2.0</h1>
                <p class="lead">Привет, КПИшник! Удачи тебе!
                    <br><b>Первое заданиие:</b> Найди пароль и получи ссылку на следующее задание.</p>
            </div>
            <div class="jumbotron quest-body text-center">
                <h1><small>Задание</small> 1</h1>
                <br/>                
                <div class="form-inline">
                    <input id="quest-pass" type="text" class="form-control input-lg" placeholder="Пароль">
                    <button class="btn btn-primary btn-lg" 
                            onclick="checkPass()" type="button">Кнопочка!</button>                    
                </div>
            </div>
        </div><!-- /.container -->


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/quest-core.js"></script>
    </body>
</html>
