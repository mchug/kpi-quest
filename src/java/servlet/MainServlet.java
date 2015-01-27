package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import questdb.Player;
import questdb.QuestDb;
import utils.HttpRequestException;
import utils.ParseObject;

/**
 *
 * @author Maxim
 */
public class MainServlet extends HttpServlet {

    public final static int MAX_LEVEL = 8;

    public static final String[] keys = {
        "numbers-314",
        "'broken",
        "picture-224",
        "cross-1717",
        "binary-2134",
        "music-31415",
        "final-558",
        "dragon-976",
        "win-2314"};

    public static final String[] hints = {
        "Страниц больше 1000...",
        "Сломана кнопка, тебе придется её починить.</br>Ошибка вот тут onclick=\\\"checkPass(''broken')\\\"",
        "Напиши программу которая покажет все возможные комбинации того что нужно было подать на вход для что бы получился такой хеш. p.s. На вход подавали английское слово. Первая буква ответа - 'а'",
        "Попробуй почитать это http://ru.wikibooks.org/wiki/Регулярные_выражения",
        "Попробуй разбить бинарный код в группы по 8 и собрать из него символы. Получившийся ответ стоит воспринимать буквально.",
        "-- --- .-. --.. .- -. -.- .-",
        "0 4 15 . . . 28 . 41 46 . . . 76",
        "Дракона придется убить =(",
        "Хорошая работа ^_^"};

    public static final String[] passes = {
        "1110",
        "banana",
        "answer",
        "aMissIsAsGoodAsAMile",
        "42",
        "MorzankaPodanaSer",
        "AnswerIsOrange",
        "kill"};

    // <editor-fold defaultstate="collapsed" desc="private final String[] tasks = { ...">
    public static final String[] tasks = {
        //        
        "<h1><small>Задание</small> 1</h1>\n"
        + "<img src=\"images/numbers.jpg\" class=\"img-thumbnail\">"
        + "<p>При издании книги потребовалось 3333 цифр (число 100 состоит из 3 цифр) для того, "
        + "чтобы пронумеровать ее страницы. Сколько страниц в книге?</p>\n"
        + "<br/>\n"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"checkPass(keys[0])\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Задание</small> 2</h1>\n"
        + "<img src=\"images/broken.jpg\" class=\"img-thumbnail\">"
        + "<p>Пароль - banana, но где-то тут есть подвох...</p>\n"
        + "<br/>\n"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"checkPass('" + keys[1] + "')\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Задание</small> 3</h1>\n"
        + "<img src=\"images/picture.jpg\" class=\"img-thumbnail\">"
        + "<p>У меня нет ключа, но есть кое-что получше, рисунок ключа!</p>\n"
        + "<br/>\n"
        + "<div id=\"code\" style=\"margin: auto;width: 600px;\">\n"
        + "                    <pre><p class=\"text-left\" style=\"font-size: 12px;\">\n"
        + "<span style='color:#696969; '>//your hash is \"мВаеюи\"</span>\n"
        + "<span style='color:#800000; font-weight:bold; '>function</span> hash<span style='color:#808030; '>(</span>m<span style='color:#808030; '>)</span> <span style='color:#800080; '>{</span>\n"
        + "    <span style='color:#800000; font-weight:bold; '>var</span> a <span style='color:#808030; '>=</span> <span style='color:#808030; '>[</span><span style='color:#800000; '>\"</span><span style='color:#0000e6; '>Рюмка</span><span style='color:#800000; '>\"</span><span style='color:#808030; '>,</span> <span style='color:#800000; '>\"</span><span style='color:#0000e6; '>Водки</span><span style='color:#800000; '>\"</span><span style='color:#808030; '>,</span> <span style='color:#800000; '>\"</span><span style='color:#0000e6; '>На</span><span style='color:#800000; '>\"</span><span style='color:#808030; '>,</span> <span style='color:#800000; '>\"</span><span style='color:#0000e6; '>Столе</span><span style='color:#800000; '>\"</span><span style='color:#808030; '>]</span><span style='color:#800080; '>;</span>\n"
        + "\n"
        + "    <span style='color:#800000; font-weight:bold; '>var</span> o <span style='color:#808030; '>=</span> <span style='color:#800000; '>\"</span><span style='color:#800000; '>\"</span><span style='color:#800080; '>;</span>\n"
        + "\n"
        + "    <span style='color:#800000; font-weight:bold; '>for</span> <span style='color:#808030; '>(</span><span style='color:#800000; font-weight:bold; '>var</span> i <span style='color:#808030; '>=</span> <span style='color:#008c00; '>0</span><span style='color:#800080; '>;</span> i <span style='color:#808030; '>&lt;</span> m<span style='color:#808030; '>.</span>length<span style='color:#800080; '>;</span> i<span style='color:#808030; '>++</span><span style='color:#808030; '>)</span> <span style='color:#800080; '>{</span>\n"
        + "        o <span style='color:#808030; '>+=</span> a<span style='color:#808030; '>[</span>i <span style='color:#808030; '>%</span> a<span style='color:#808030; '>.</span>length<span style='color:#808030; '>]</span><span style='color:#808030; '>[</span>m<span style='color:#808030; '>[</span>i<span style='color:#808030; '>]</span><span style='color:#808030; '>.</span><span style='color:#800000; font-weight:bold; '>charCodeAt</span><span style='color:#808030; '>(</span><span style='color:#808030; '>)</span> <span style='color:#808030; '>%</span> a<span style='color:#808030; '>[</span>i <span style='color:#808030; '>%</span> a<span style='color:#808030; '>.</span>length<span style='color:#808030; '>]</span><span style='color:#808030; '>.length</span><span style='color:#808030; '>]</span><span style='color:#800080; '>;</span>\n"
        + "    <span style='color:#800080; '>}</span>\n"
        + "\n"
        + "    <span style='color:#800000; font-weight:bold; '>return</span> o<span style='color:#800080; '>;</span>\n"
        + "<span style='color:#800080; '>}</span>\n"
        + "                    </p></pre>\n"
        + "                </div>"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"checkPass(keys[2])\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Задание</small> 4</h1>\n"
        + "<img src=\"images/cross.jpg\" class=\"img-thumbnail\">"
        + "<p>Простой кроссворд, правда вопросы в нем какие-то регулярные...</p>\n"
        + "<br/>\n"
        + "<div class=\"regex\">\n"
        + "                    <table>\n"
        + "                        <thead>\n"
        + "                            <tr>                            \n"
        + "                                <td></td>\n"
        + "                                <td><img alt=\"[AIO]*\" src=\"images/_0000_reg1h.png\"></td>\n"
        + "                                <td><img alt=\"M[SO|LO]*M\" src=\"images/_0001_reg2h.png\"></td>\n"
        + "                                <td><img alt=\"[DAI]*\" src=\"images/_0002_reg3h.png\"></td>\n"
        + "                                <td><img alt=\"S{2}(AL|EL|OL)*\" src=\"images/_0003_reg4h.png\"></td>\n"
        + "                                <td><img alt=\"[SGE]*\" src=\"images/_0004_reg5h.png\"></td>\n"
        + "                                <td></td>\n"
        + "                            </tr>\n"
        + "                        </thead>\n"
        + "                        <tbody>\n"
        + "                            <tr>\n"
        + "                                <td><img alt=\"A(MI|LI)S{2}\" src=\"images/_0005_reg1l.png\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><img alt=\"(AM|AK)I[SLOW]+\" src=\"images/_0009_reg1r.png\"></td>\n"
        + "                            </tr>\n"
        + "                            <tr>\n"
        + "                                <td><img alt=\"[ASI]*G\" src=\"images/_0006_reg2l.png\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><img alt=\"(ISA).*\" src=\"images/_0010_reg2r.png\"></td>\n"
        + "                            </tr>\n"
        + "                            <tr>\n"
        + "                                <td><img alt=\"O{2}[^REX]*AS\" src=\"images/_0007_reg3l.png\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><img alt=\"[O]*(DAS|DEV)\" src=\"images/_0011_reg3r.png\"></td>\n"
        + "                            </tr>\n"
        + "                            <tr>\n"
        + "                                <td><img alt=\"^(AM|ALE)I[EL]+\" src=\"images/_0008_reg4l.png\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><input class=\"form-control input cell\" type=\"text\" maxlength=\"1\"></td>\n"
        + "                                <td><img alt=\".*\" src=\"images/_0012_reg4r.png\"></td>\n"
        + "                            </tr>\n"
        + "                        </tbody>\n"
        + "                        <tfoot>\n"
        + "                            <tr>\n"
        + "                                <td></td>\n"
        + "                                <td><img alt=\"A[IO]*A\" src=\"images/_0013_reg1f.png\"></td>\n"
        + "                                <td><img alt=\"[^LINK]*\" src=\"images/_0014_reg2f.png\"></td>\n"
        + "                                <td><img alt=\"I(AD|AR)+I\" src=\"images/_0015_reg3f.png\"></td>\n"
        + "                                <td><img alt=\"[^OW]+\" src=\"images/_0016_reg4f.png\"></td>\n"
        + "                                <td><img alt=\"S[GR]SE\" src=\"images/_0017_reg5f.png\"></td>\n"
        + "                                <td></td>\n"
        + "                            </tr>\n"
        + "                        </tfoot>\n"
        + "                    </table>\n"
        + "                    <button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"test()\">Проверить</button>\n"
        + "                </div>",
        //
        "<h1><small>Задание</small> 5</h1>\n"
        + "<img src=\"images/binary.jpg\" class=\"img-thumbnail\">"
        + "<p>Привет, человек, ты понимаешь бинарный код?</p>\n"
        + "<pre><p class=\"text-left\" style=\"font-size: 18px\">"
        + "0100100001100001011010000110000100101100 "
        + "011100110111010001110101011100000110100101100100 "
        + "011011010110000101101110 "
        + "01110111011010010110110001101100 "
        + "0110111001100101011101100110010101110010 "
        + "0110011101110101011001010111001101110011 "
        + "01110100011010000110000101110100 "
        + "011101000110100001100101 "
        + "01100011011011110111001001110010011001010110001101110100 "
        + "011000010110111001110011011101110110010101110010 "
        + "0110100101110011 "
        + "011011100111010101101101011000100110010101110010 "
        + "00101010</p></pre>"
        + "<br/>\n"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"checkPass(keys[4])\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Задание</small> 6</h1>\n"
        + "<div id=\"dancer\"></div>"
        + "<p>Музыкальная пауза<span id=\"smile\">...</span></p>\n"
        + "<div id=\"music-div\">\n"
        + "<audio src=\"music/music.mp3\" id=\"myTune\"></audio>\n"
        + "    <div class=\"buttons\">\n"
        + "        <button type=\"button\" class=\"btn btn-default btn-lg button-skip-backward\">\n"
        + "            <span class=\"glyphicon glyphicon-fast-backward\"></span>\n"
        + "        </button>\n"
        + "        <button type=\"button\" class=\"btn btn-default btn-lg button-pause\">\n"
        + "            <span class=\"glyphicon glyphicon-pause\"></span>\n"
        + "        </button>\n"
        + "        <button type=\"button\" class=\"btn btn-default btn-lg button-stop\">\n"
        + "            <span class=\"glyphicon glyphicon-stop\"></span>\n"
        + "        </button>\n"
        + "        <button type=\"button\" onclick=\"initPlayer()\" class=\"btn btn-default btn-lg button-play\">\n"
        + "            <span class=\"glyphicon glyphicon-play\"></span>\n"
        + "        </button>\n"
        + "        <button type=\"button\" class=\"btn btn-default btn-lg button-skip-forward\">\n"
        + "            <span class=\"glyphicon glyphicon-fast-forward\"></span>\n"
        + "        </button>\n"
        + "    </div>\n"
        + "</div>"
        + "<br/>\n"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"checkPass(keys[5])\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Задание</small> 7</h1>\n"
        + "<img src=\"images/final.jpg\" class=\"img-thumbnail\">"
        + "<p>Очень много букв, но разве все они нужны?</p>\n"
        + "<table class=\"img-thumbnail\"<tbody>\n"
        + "    <tr>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'K')\">K</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'N')\">N</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'G')\">G</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'R')\">R</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'O')\">O</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'O')\">O</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'R')\">R</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'D')\">D</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'S')\">S</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'W')\">W</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'O')\">O</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'N')\">N</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'D')\">D</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'R')\">R</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'F')\">F</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'U')\">U</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'L')\">L</button></td>\n"
        + "    </tr>\n"
        + "    <tr>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'B')\">B</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'I')\">I</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'G')\">G</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'S')\">S</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'O')\">O</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'R')\">R</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'Y')\">Y</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'B')\">B</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'U')\">U</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'O')\">O</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'N')\">N</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'G')\">G</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'R')\">R</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'P')\">P</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'I')\">I</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'S')\">S</button></td>\n"
        + "    </tr>\n"
        + "    <tr>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'B')\">B</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'R')\">R</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'H')\">H</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'N')\">N</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'W')\">W</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'O')\">O</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'G')\">G</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'I')\">I</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'A')\">A</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'N')\">N</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'T')\">T</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, ' ')\"> </button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'E')\">E</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'G')\">G</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'G')\">G</button></td>\n"
        + "        <td><button class=\"btn btn-default final\" onclick=\"flip(this, 'S')\">S</button></td>\n"
        + "    </tr>\n"
        + "</tbody></table>"
        + "<br/>\n"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"checkPass(keys[6])\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Задание</small> 8</h1>\n"
        + "<img src=\"http://firepic.org/images/2015-01/23/rkf6rgr2b4if.gif\" class=\"img-thumbnail\">"
        + "<p>И так, ты дошел до конца.<br/>"
        + "Перед тобой неожиданно появляется удивленный дракон!<br/>"
        + "Он не даст тебе пройти квест просто так... Что ты будешь делать?</p>\n"
        + "<br/>\n"
        + "<div class=\"form-inline quest-form\">\n"
        + "<input id=\"quest-pass\" type=\"text\" class=\"form-control input-lg\" placeholder=\"Ответ\">\n"
        + "<button id=\"pass-btn\" class=\"btn btn-primary btn-lg\" onclick=\"dragon()\" type=\"button\">\n"
        + "Проверить\n"
        + "</button>\n"
        + "</div>",
        //
        "<h1><small>Победа</small> кароч</h1>\n"
        + "<pre style=\"width: 400px;margin: auto\">Напиши <a href=\"https://vk.com/m.chug\">ему</a> и скажи пароль: <player-pass></pre><br/>"
        + "<p>Квест подготовлен при участии GDG KPI</p>"
        + "<p style=\"font-size: 18px\">by <a href=\"https://vk.com/m.chug\">m.chug</a></p>"
        + "<p style=\"font-size: 14px\">Отдельная благодарность <a href=\"https://vk.com/annvaskovska\">annvaskovska</a> за тестирование, смешные картинки и критику.<br/>"
        + "Так же спасибо за активное тестирование и критику <a href=\"https://vk.com/crasher_ua\">crasher_ua</a>, <a href=\"https://vk.com/id86690367\">Олександру Ковальчуку</a> и <a href=\"https://vk.com/evgart\">evgart</a>.</p>"
    };
    //</editor-fold>

    private String makeJson(boolean result, String data, String player) {
        return "{\"result\":" + result
                + ",\"data\":" + data + ""
                + ",\"player\":\"" + player + "\"}";
    }

    private String getInvalidMsg() {
        String[] nopes = {
            "Увы, но нет.",
            "Совсем не туда...",
            "Неправильно!",
            "Я так не думаю.",
            "Воу, серьезно?",
            "ДА! Это был неправильный ответ!",
            "Попробуй еще)",
            "БАБАХ! Опять мимо... =("};

        Random rand = new Random();

        return nopes[rand.nextInt(nopes.length)];
    }

    private String getHtml(int i, int level) {
        return "{"
                + "\"level\":" + level
                + ",\"task\":" + i
                + ",\"key\":\"" + keys[i] + "\""
                + ",\"hint\":\"" + hints[i] + "\""
                + "}";
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String key = request.getParameter("key");
        String pass = request.getParameter("pass");
        Player player = (Player) session.getAttribute("quest.player");

        try (PrintWriter out = response.getWriter()) {

            //<editor-fold desc="if (request.getParameter("getForm") != null) {">            
            if (request.getParameter("getForm") != null) {
                if ("login".equals(request.getParameter("getForm"))) {
                    out.print("<h2>Вход</h2>\n"
                            + "<form class=\"form-signin\">\n"
                            + "<input type=\"text\" id=\"inputName\" class=\"form-control\" placeholder=\"Логин\" required autofocus>\n"
                            + "<input type=\"password\" id=\"inputPass\" class=\"form-control\" placeholder=\"Пароль\" required>\n"
                            + "<button id=\"pass-btn\" class=\"btn btn-lg btn-primary btn-block\" type=\"button\" onclick=\"login()\">Поехали</button>\n"
                            + "</form>");
                } else { //это специально, что бы можно было оставить пасхалку на стороне клиента
                    out.print("<h2>Регистрация</h2>\n"
                            + "<form class=\"form-signin\">\n"
                            + "<input type=\"text\" id=\"inputName\" class=\"form-control\" placeholder=\"Логин\" required autofocus>\n"
                            + "<input type=\"password\" id=\"inputPass\" class=\"form-control\" placeholder=\"Пароль\" required>\n"
                            + "<input type=\"password\" id=\"repeatPass\" class=\"form-control\" placeholder=\"Повторить пароль\" required>\n"
                            + "<button id=\"pass-btn\" class=\"btn btn-lg btn-primary btn-block\" type=\"button\" onclick=\"register()\">Поехали</button>\n"
                            + "</form>");
                }
                return;
            }
            //</editor-fold>

            if ("reg".equals(key)) {
                String name = request.getParameter("name");

                if (name == null || pass == null) {
                    out.print(makeJson(false, "\"Не ломай мне тут сервак...\"", ""));
                    return;
                }

                if (!name.matches("[a-zA-Z0-9]{4,12}")) {
                    out.print(makeJson(false, "\"Твой логин не годится...\nДопустимые сиволы A-z и 0-9, допустимая длинна от 4 до 12\"", ""));
                    return;
                }

                if (!pass.matches("[a-zA-Z0-9]{4,12}")) {
                    out.print(makeJson(false, "\"Твой пароль не годится... Допустимые сиволы A-z и 0-9, допустимая длинна от 4 до 12\"", ""));
                    return;
                }

                ParseObject parseObj = null;
                try {
                    parseObj = QuestDb.getPlayer(name);
                } catch (HttpRequestException ex) {
                    out.print(makeJson(false, "\"Возникла ошибка при соединении с базой данный =(\"", ""));
                }
                if (parseObj != null) {
                    out.print(makeJson(false, "\"Такой игрок уже есть =(\"", ""));
                    return;
                }
                player = new Player(name, pass);

                try {
                    QuestDb.addPlayer(name, pass);
                    session.setAttribute("quest.player", player);
                    out.print(makeJson(true, "\"Успех =)\"", player.toString()));
                } catch (HttpRequestException ex) {
                    out.print(makeJson(false, "\"Возникла ошибка при соединении с базой данный =(\"", ""));
                }
                return;
            }

            if ("login".equals(key)) {
                String name = request.getParameter("name");

                if (name == null || pass == null) {
                    out.print(makeJson(false, "\"Не ломай мне тут сервак...\"", ""));
                    return;
                }

                ParseObject parseObj = null;
                try {
                    parseObj = QuestDb.getPlayer(name);
                } catch (HttpRequestException ex) {
                    out.print(makeJson(false, "\"Возникла ошибка при соединении с базой данный =(\"", ""));
                    return;
                }

                if (parseObj == null) {
                    out.print(makeJson(false, "\"Такого игрока нет =(\"", ""));
                    return;
                }

                if (!parseObj.getField("pass").equals(pass)) {
                    out.print(makeJson(false, "\"Неправильный пароль\"", ""));
                    return;
                }

                player = new Player(parseObj);

                session.setAttribute("quest.player", player);
                out.print(makeJson(true, "\"Успех =)\"", player.toString()));
                return;
            }

            if ("logout".equals(key)) {
                session.setAttribute("quest.player", null);
                out.print("Успех. Ты вышел из своего профиля.");
                return;
            }

            if ("stats".equals(key)) {
                String outStr;
                try {
                    outStr = QuestDb.print();
                    if (player != null) {
                        outStr = outStr.replaceFirst(player.getName() + "</td>", "<b>" + player.getName() + "</b></td>");
                    }
                    out.print(outStr);
                } catch (HttpRequestException ex) {
                    out.print("Возникла ошибка при соединении с базой данный =(");
                }
                return;
            }

            for (int i = 0; i < keys.length; i++) {
                if (keys[i].equals(key)) {
                    if (pass == null) {
                        if (keys[MAX_LEVEL].equals(key)) {
                            try {
                                out.print(tasks[i].replace("<player-pass>",
                                        QuestDb.getPlayer(player.getName()).getObjectId()));
                            } catch (HttpRequestException ex) {
                                out.print("Возникла ошибка при соединении с базой данный =(");
                            }
                        } else {
                            out.print(tasks[i]);
                        }
                        return;
                    }

                    if (!passes[i].equalsIgnoreCase(pass)) {
                        if (player != null) {
                            if (player.getLevel() == i) {
                                player.setScores(player.getScores() - 1);
                                try {
                                    ParseObject parseObj = QuestDb.getPlayer(player.getName());
                                    parseObj.setField("scores", Integer.toString(player.getScores()));
                                    QuestDb.setPlayer(parseObj);
                                    session.setAttribute("quest.player", player);
                                } catch (HttpRequestException ex) {
                                    out.print(makeJson(false, "\"Возникла ошибка при соединении с базой данный =(\"", ""));
                                    return;
                                }
                            }
                            out.print(makeJson(false, "\"" + getInvalidMsg() + "\"", player.toString()));
                            return;
                        }
                    }

                    if (player != null) {
                        if (player.getLevel() == i) {
                            player.setScores(player.getScores() + 100);
                            player.setLevel(player.getLevel() + 1);
                            try {
                                ParseObject parseObj = QuestDb.getPlayer(player.getName());
                                parseObj.setField("scores", Integer.toString(player.getScores()));
                                parseObj.setField("level", Integer.toString(player.getLevel()));
                                QuestDb.setPlayer(parseObj);
                                session.setAttribute("quest.player", player);
                            } catch (HttpRequestException ex) {
                                out.print(makeJson(false, "\"Возникла ошибка при соединении с базой данный =(\"", ""));
                                return;
                            }
                        }
                        out.print(makeJson(true, getHtml(i + 1, player.getLevel()), player.toString()));
                        return;
                    }
                }
            }

            if ("broken".equals(key)) {
                if (player != null) {
                    out.print(makeJson(false, "\"Ты уже догадался где проблема, "
                            + "но все же, твое решение неправильное...\"", player.toString()));
                }
                return;
            }

            if ("godmode".equals(key)) {
                if (player != null) {
                    player.setScores(0);
                    player.setLevel(8);
                    session.setAttribute("quest.player", player);
                }
                for (String item : passes) {
                    out.println(item + "<br/>");
                }
                return;
            }

            out.print("<h2>Не нужно так...</h2>"
                    + "<img src=\"images/hacker.jpg\" class=\"img-thumbnail\">"
                    + "<br/>"
                    + "<p>Не пытайся искать ответы путем взлома, у тебя ничего не получится...</p>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
