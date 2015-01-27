<%@page import="servlet.MainServlet"%>
<%@page import="questdb.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>KPI Quest v2.0</title>        
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/quest.css" rel="stylesheet">
    </head>
    <body>
        <%
            Player player = (Player) session.getAttribute("quest.player");
            boolean isNull = player == null;

            String clientHints = "";
            String clientKeys = "";
            if (!isNull) {
                int len = MainServlet.hints.length;
                for (int i = 0; i < len; i++) {
                    if (i > player.getLevel()) {
                        clientHints += "'',";
                        clientKeys += "'',";
                    } else {
                        clientHints += "\"" + MainServlet.hints[i] + "\",";
                        clientKeys += "\"" + MainServlet.keys[i] + "\",";
                    }
                }

                if (MainServlet.MAX_LEVEL != player.getLevel()) {
                    clientHints += "''";
                    clientKeys += "''";
                } else {
                    clientHints += "\"" + MainServlet.hints[len - 1] + "\"";
                    clientKeys += "\"" + MainServlet.keys[len - 1] + "\"";
                }
            }
        %>
        <script>
            var currentLevel = <%= isNull ? -1 : player.getLevel()%>;
            var hints = [<%= isNull ? "" : clientHints%>];
            var keys = [<%= isNull ? "" : clientKeys%>];
        </script>
        <header>            
            <nav class="navbar navbar-default quest-navbar">
                <div class="container" style="width: 1170px">
                    <div class="navbar-brand">
                        <b>
                            <span id="userName">
                                <%= isNull ? "Твой логин будет здесь" : player.toString()%>
                            </span>
                        </b>
                    </div>
                    <div class="navbar-right">
                        <div class="container-fluid">
                            <div class="navbar-text quest-navbar-btn">                                  
                                <% if (isNull) {
                                        out.print("<button class=\"btn btn-default btn-sm\">Тут будут</button>\n"
                                                + "<button class=\"btn btn-default btn-sm\">кнопки</button>\n"
                                                + "<button class=\"btn btn-default btn-sm\">для перехода</button>\n"
                                                + "<button class=\"btn btn-default btn-sm\">по заданиям</button>");
                                    } else {
                                        for (int i = 1; i < MainServlet.keys.length; i++) {
                                            out.print("<button id=\"task-" + (i - 1) + "\"class=\"taskBtns btn btn-default btn-sm\" onclick=\"updateTask("
                                                    + (i - 1) + ")\" " + ((i - 1) > player.getLevel() ? "disabled" : "") + ">Задание " + i + "</button>\n");
                                        }

                                        out.print("<button id=\"task-8\"class=\"btn btn-default btn-sm\" onclick=\"updateTask(8)\""
                                                + (8 > player.getLevel() ? "disabled" : "") + ">Победа</button>\n");
                                    }
                                %>
                                <button class="btn btn-success btn-sm" onclick="hint()">
                                    Подсказка
                                    <span class="glyphicon glyphicon-info-sign"></span>
                                </button>
                                <button type="button" class="btn btn-primary btn-sm my-tooltip" onclick="showStats()" 
                                        data-toggle="tooltip" data-placement="bottom" title="Статистика">
                                    <span class="glyphicon glyphicon-stats"></span>
                                </button>                                
                            </div>
                        </div>
                    </div>
                </div>
            </nav>           
        </header>  
        <div class="modal fade" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Закрыть"><span aria-hidden="true">&times;</span></button>
                        <h4 id="modalTitle" class="modal-title">Modal title</h4>
                    </div>
                    <div id="modalBody" class="modal-body text-center">
                        <p>One fine body&hellip;</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Закрыть</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <div class="container">
            <div class="progress quest-progress">                    
                <div id="quest-progress" class="progress-bar progress-bar-striped" role="progressbar" 
                     aria-valuenow="60" aria-valuemin="0" 
                     aria-valuemax="100" style="width: <%=isNull ? "60%" : ((float) player.getLevel() * 100) / MainServlet.MAX_LEVEL + "%"%>;">
                    <%=isNull ? "Тут будет показан твой прогресс" : player.getLevel() + " из " + MainServlet.MAX_LEVEL%>
                </div>                    
            </div>
            <%
                if (isNull) {
                    out.print("<div  id = \"hello\" class=\"text-center\">"
                            + "<h1>KPI Quest v2.0</h1>"
                            + "</div>");
                }
            %>
            <div id="quest-body" class="jumbotron quest-body text-center">   
                <%
                    if (isNull) {
                        out.print("<div id=\"sign-in\">\n"
                                + "<script>\n"
                                + "function getForm(formType) {\n"
                                + "var hello = document.getElementById('hello');\n"
                                + "hello.parentNode.removeChild(hello);\n"
                                + "$.ajax({\n"
                                + "url: 'bigdaddy?getForm=' + formType,\n"
                                + "dataType: \"text\",\n"
                                + "success: function (msg) {\n"
                                + "$(\"#sign-in\").html(msg);\n"
                                + "}\n"
                                + "});\n"
                                + "}\n"
                                + "</script>\n"
                                + "<h2>Войди или зарегистрируйся</h2>\n"
                                + "<form class=\"form-signin\">\n"
                                + "<button class=\"btn btn-lg btn-success btn-block\" type=\"button\" onclick=\"getForm('login')\">Вход</button>\n"
                                + "<button class=\"btn btn-lg btn-primary btn-block\" type=\"button\" onclick=\"getForm('Силу исходного кода страницы познал ты!')\">Регистрация</button>\n"
                                + "</form>\n"
                                + "</div>");
                    }
                %>

            </div>
        </div><!-- /.container -->        

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/quest-core.js"></script>
        <script>
                                    $(function () {
                                        $('[data-toggle="tooltip"]').tooltip();
                                    });
            <%= isNull ? "" : "updateTask(" + player.getLevel() + ");"%>
        </script>
    </body>
</html>
