<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%
    Date current = new Date();
    current.setHours(current.getHours() + 2);
    Date realEnd = new Date(2015 - 1900, 0, 27, 13, 59, 59);
    Date end = new Date(2015 - 1900, 0, 27, 14, 0, 0);

    boolean bool = current.after(realEnd);
    if (bool) {%>
<%@include file = "index.jsp"%>
<%  } else { %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    out.print(current);
    out.print("<br/>" + end);
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>KPI Quest v2.0</title>        
        <link href="css/bootstrap.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="text-center" style="position: absolute;top: 50%;left: 50%;width: 1000px; height: 300px;margin-top: -250px;margin-left: -500px">                
                <span style="font-size: 80px">KPI Quest v2.0</span>
                <span style="font-size: 160px" id="countdown">countdown displayed here</span>
                <a href="https://developers.google.com/events/6212601183731712/" style="font-size: 30px" class="label label-danger">Регистрация</a>
            </div>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //time to count down
            hoursToGo = 0;
            minutesToGo = 27;
            secondsToGo = 43;

            var endTime = new Date(2015, 0, 27, 14, 00, 00);

            function update() {
                var currentTime = new Date();

                var remainingTime = new Date(endTime - currentTime);

                //не смотри сюда.
                remainingTime.setHours(remainingTime.getHours() - 2);

                if (remainingTime.getHours() + remainingTime.getMinutes() + remainingTime.getSeconds() === 0) {
                    location.reload();
                }

                $("#countdown").text(
                        ((remainingTime.getHours() < 10) ?
                                "0" : "") +
                        remainingTime.getHours() + ":" +
                        ((remainingTime.getMinutes() < 10) ?
                                "0" : "") +
                        remainingTime.getMinutes() + ":" +
                        ((remainingTime.getSeconds() < 10) ?
                                "0" : "") +
                        remainingTime.getSeconds() + "." +
                        ((remainingTime.getMilliseconds() < 100) ?
                                "0" : "") +
                        ((remainingTime.getMilliseconds() < 10) ?
                                "0" : "") +
                        remainingTime.getMilliseconds()
                        );

                setTimeout(update, 100);
            }

            update();
        });
    </script>
</html>
<%}%>