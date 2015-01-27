package questdb;

import utils.ParseObject;

/**
 *
 * @author Maxim
 */
public class Player implements Comparable<Player> {

    private static final long serialVersionUID = 0x2d0;

    private String name;
    private String pass;
    private int level;
    private int scores;

    public Player(ParseObject obj) {
        name = obj.getField("name");
        pass = obj.getField("pass");
        level = Integer.parseInt(obj.getField("level"));        
        scores = Integer.parseInt(obj.getField("scores"));
    }
    
    public Player(String name, String pass) {
        this.name = name;
        this.pass = pass;
        level = 0;
        scores = 100;
    }

    private Player(String name, String pass, int level, int scores) {
        this.name = name;
        this.pass = pass;
        this.level = level;
        this.scores = scores;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the rating
     */
    public int getScores() {
        return scores;
    }

    /**
     * @param rating the rating to set
     */
    public void setScores(int rating) {
        this.scores = rating;
    }


    @Override
    public String toString() {
        return getName() + " (" + getLevel() + ")";
    }

    @Override
    public int compareTo(Player other) {
        return other.getScores() - this.getScores();
    }
}
