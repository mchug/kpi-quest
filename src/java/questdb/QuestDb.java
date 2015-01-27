package questdb;

import java.util.List;
import utils.HttpRequestException;
import utils.ParseObject;
import utils.ParseSession;

/**
 *
 * @author Maxim
 */
public class QuestDb {

    public static ParseObject getPlayer(String name) throws HttpRequestException {
        for (ParseObject item : ParseSession.getQuery("Player")) {
            if (item.getField("name").equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static void addPlayer(String name, String pass) throws HttpRequestException {
        String json = "{\"scores\":100,\"level\":0,\"name\":\"" + name + "\",\"pass\":\"" + pass + "\"}";
        ParseSession.createObject("Player", json);
    }

    public static void setPlayer(ParseObject obj) throws HttpRequestException {
        ParseSession.updateObject("Player", obj.getObjectId(), obj);
    }

    public static String print() throws HttpRequestException {
        String outStr = "";
        List<ParseObject> list = ParseSession.getQuery("Player");
        list.sort((ParseObject o1, ParseObject o2) -> (Integer.parseInt(o1.getField("scores"))
                - Integer.parseInt(o2.getField("scores")))
        );

        for (int i = 0; i < list.size(); i++) {
            outStr += "<tr><td>" + (i + 1) + "</td>"
                    + "<td>" + list.get(i).getField("name") + "</td>"
                    + "<td>" + list.get(i).getField("level") + "</td></tr>";
        }

        return "<table class=\"table table-hover\" style=\"margin-top: -20px\">"
                + "<thead><tr><th colspan=\"3\" class=\"text-center\">"
                + "<h3>Всего игроков: " + list.size() + "</h3></th></tr></thead>"
                + "<tbody>" + outStr + "</tbody></table>";
    }
}
