package menu;

/**
 * storing winner name and score
 * @author pawel and Marcin
 */
public class Winners {
    /**
     * player name
     */
    private final String nickname;
    /**
     * player score
     */
    private final int score;
    
    /**
     * constructor which takes 2 parameteres to be set
     * @param nickname player nickname
     * @param score player score
     */
    public Winners(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }
    /**
     * get nickname
     * @return nickname
     */
    public String getNick(){ return nickname;}
    
    /**
     * get score
     * @return score
     */
    public int getScore(){ return score;}
}
