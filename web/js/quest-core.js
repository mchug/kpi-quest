$(document).keypress(function (e) {
    if (e.which == 13) {
        if (document.getElementById("pass-btn") != null) {
            document.getElementById("pass-btn").click();
        }
    }
});

var modal = $("#myModal");
function showStats() {
    $("#modalTitle").html("Рейтинг");
    $.ajax({
        url: 'bigdaddy?key=stats',
        dataType: "text",
        success: function (msg) {
            $("#modalBody").html(msg);
            $(modal).modal('show');
        }
    });
}

function login() {
    var name = $("#inputName").val();
    var pass = $("#inputPass").val();

    if (/[a-z0-9]{4,12}/i.test(name) == false) {
        $("#modalTitle").html("Недопустимый логин");
        $("#modalBody").html("Логин должен содержать от 4 до 12 символов A-z 0-9.");
        $(modal).modal('show');
        return;
    }

    if (/[a-z0-9]{4,12}/i.test(pass) == false) {
        $("#modalTitle").html("Недопустимый пароль");
        $("#modalBody").html("Пароль должен содержать от 4 до 12 символов A-z 0-9.");
        $(modal).modal('show');
        return;
    }

    $.ajax({
        url: 'bigdaddy?key=login&name=' + name + '&pass=' + pass,
        dataType: "json",
        success: function (msg) {
            if (msg.result == false) {
                $("#modalTitle").html("Ошибка");
                $("#modalBody").html(msg.data);
                $(modal).modal('show');
            } else {
                location.reload();
            }
        }
    });
}

function register() {
    var name = $("#inputName").val();
    var pass = $("#inputPass").val();
    var repass = $("#repeatPass").val();

    if (/[a-z0-9]{4,12}/i.test(name) == false) {
        $("#modalTitle").html("Недопустимый логин");
        $("#modalBody").html("Логин должен содержать от 4 до 12 символов A-z 0-9.");
        $(modal).modal('show');
        return;
    }

    if (/[a-z0-9]{4,12}/i.test(pass) == false) {
        $("#modalTitle").html("Недопустимый пароль");
        $("#modalBody").html("Пароль должен содержать от 4 до 12 символов A-z 0-9.");
        $(modal).modal('show');
        return;
    }

    if (pass != repass) {
        $("#modalTitle").html("Ошибка");
        $("#modalBody").html("Пароли не совпадают.");
        $(modal).modal('show');
        return;
    }

    $.ajax({
        url: 'bigdaddy?key=reg&name=' + name + '&pass=' + pass,
        dataType: "json",
        success: function (msg) {
            if (msg.result == false) {
                $("#modalTitle").html("Ошибка");
                $("#modalBody").html(msg.data);
                $(modal).modal('show');
            } else {
                location.reload();
            }
        }
    });
}

function hint() {
    $("#modalTitle").html("Подсказка");
    if (currentLevel == -1) {
        $("#modalBody").html("Место для посказок по текущему заданию.");
    } else {
        $("#modalBody").html(hints[currentLevel]);
    }
    $(modal).modal('show');
}

function wrong(msg) {
    $("#modalTitle").html("Неправильный ответ");
    $("#modalBody").html("<div class=\"text-center\">\n\
                            <img src=\"images/wrong.jpg\" class=\"im-\">\n\
                            <h2>" + msg + "</h2>\n\
                            </div>");
    $(modal).modal('show');
}

function updateProgress(progress) {
    var tasks = 8;
    document.getElementById("task-" + progress).disabled = false;
    document.getElementById("quest-progress").style.width = (progress / tasks) * 100 + "%";
    document.getElementById("quest-progress").innerHTML = (progress) + " из " + tasks;
}

function updateTask(key) {
    if (keys[key] == '') {
        return;
    }
    $("#task-" + currentLevel).removeClass("btn-primary");
    $("#task-" + key).addClass("btn-primary");
    currentLevel = key;

    $.ajax({
        url: 'bigdaddy?key=' + ((keys[key] == '') ? "i_want_to_belive_in_jesus" : keys[key]),
        dataType: "text",
        success: function (msg) {
            document.getElementById("quest-body").innerHTML = msg;

        }
    });
}

function initPlayer() {
    var dancer = "<img src=\"http://firepic.org/images/2015-01/23/mzp4ay91nb7g.gif\" class=\"img-thumbnail\">";
    var audioElement = $("#myTune")[0];
    $(".button-pause").on("click", function () {
        $(".button-pause").blur();
        $(".button-pause").addClass("active");
        $(".button-play").removeClass("active");
        audioElement.pause();
    });

    $(".button-play").on("click", function () {
        $(".button-play").blur();
        $(".button-play").addClass("active");
        $(".button-pause").removeClass("active");
        document.getElementById("dancer").innerHTML = dancer;
        document.getElementById("smile").innerHTML = " =D";
        audioElement.play();
    });

    $(".button-stop").on("click", function () {
        $(".button-stop").blur();
        $(".button-play").removeClass("active");
        $(".button-pause").removeClass("active");
        document.getElementById("dancer").innerHTML = "";
        document.getElementById("smile").innerHTML = "...";
        audioElement.pause();
        audioElement.currentTime = 0;
    });

    $(".button-skip-forward").on("click", function () {
        $(".button-skip-fastword").blur();
        audioElement.currentTime += 5;
    });

    $(".button-skip-backward").on("click", function () {
        $(".button-skip-backward").blur();
        audioElement.currentTime -= 5;
    });

    //firstClick
    $(".button-play").blur();
    $(".button-play").addClass("active");
    $(".button-pause").removeClass("active");
    document.getElementById("dancer").innerHTML = dancer;
    document.getElementById("smile").innerHTML = " =D";
    audioElement.play();
}

function flip(btn, symb) {
    if (btn.innerHTML == ' ') {
        btn.innerHTML = symb;
        btn.style.backgroundColor = '#fff';
    } else {
        btn.innerHTML = ' ';
        btn.style.backgroundColor = '#eee';
    }
}

function test() {
    var pass = "";
    var arr = document.getElementsByClassName("cell");

    for (var i = 0; i < arr.length; i++) {
        pass += arr[i].value;
    }

    checkPass(keys[3], pass);
}

function dragon() {
    var pass = document.getElementById('quest-pass').value;

    if (pass == "") {
        return;
    }

    if (/([^a-z]|k)([^abc]|[i]|[zure])(l){2}/i.test(pass)) {
        $("#modalTitle").html("Инстинкт самосохранения");
        $("#modalBody").html("<div class=\"text-center\">\n\
                        <p>Ты действительно хочешь убить дракона голыми руками?<br/>Может стоит поискать оружие?\n\
                        </p>\n\
                        <button class=\"btn btn-danger btn\" onclick=\"$(modal).modal('hide');checkPass(keys[7])\" type=\"button\">Да, хочу!</button>\n\
                        <button class=\"btn btn-success btn\" onclick=\"$(modal).modal('hide')\" type=\"button\">Я еще подумаю...</button>");
        $(modal).modal('show');
    } else {
        checkPass(keys[7]);
    }
}

function checkPass(key, pass) {

    if (pass == undefined) {
        pass = document.getElementById('quest-pass').value;
    }

    if (pass == "") {
        return;
    }

    var myurl = 'bigdaddy?key=' + key + '&pass=' + pass;

    $.ajax({
        url: myurl,
        dataType: 'json',
        success: function (msg) {
            if (msg.result == false) {
                wrong(msg.data);
            } else {
                if (msg.data.task == msg.data.level) {
                    hints[msg.data.task] = msg.data.hint;
                    keys[msg.data.task] = msg.data.key;
                    updateProgress(msg.data.level);
                    $("#userName").html(msg.player);
                }
                updateTask(msg.data.task);
            }
        }
    });
}