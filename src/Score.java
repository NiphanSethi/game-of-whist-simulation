import ch.aplu.jcardgame.CardGame;
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.awt.Font;

public class Score extends CardGame {

    private int currentScore = 0;
    private Location scoreBoardPosition;
    private final Font displayFont = new Font("Serif", Font.BOLD, 36);
    private final Color scoreOutlineColor = new Color(20, 80, 0);
    private Actor scoreBoardSprite;

    public Score(int playerID) {
        storeScoreBoardLocation(playerID);
    }

    /*****************************************************************************************************************/

    /** Getter for player's current score */

    public int getCurrentScore() { return currentScore; }

    /*****************************************************************************************************************/

    /** This method instantiates the position of each player's scoreboard on screen */

    private void storeScoreBoardLocation(int playerID) {
        switch(playerID) {
            case 0:
                scoreBoardPosition = new Location(575, 675);
                break;
            case 1:
                scoreBoardPosition = new Location(25, 575);
                break;
            case 2:
                scoreBoardPosition = new Location(575, 25);
                break;
            case 3:
                scoreBoardPosition = new Location(650, 575);
                break;
        }
    }

    /*****************************************************************************************************************/

    /** This method creates a new text actor for the score board of a player, and adds the actor to the card game */

    public void initScore(CardGame cardGame) {
        scoreBoardSprite = new TextActor("0", Color.WHITE, scoreOutlineColor, displayFont);
        cardGame.addActor(scoreBoardSprite, scoreBoardPosition);
    }

    /*****************************************************************************************************************/

    /** Getters for private attributes */

    public Actor getScoreBoardSprite() { return scoreBoardSprite; }
    public Location getScoreBoardPosition() { return scoreBoardPosition; }

    /*****************************************************************************************************************/

    /** This method updates the score for a player and displays it on screen */

    public void updateScore(CardGame cardGame) {

        //remove current score actor
        cardGame.removeActor(scoreBoardSprite);
        //increment winner's score by 1
        incrementCurrentScore();
        //create new text actor for the updated score and add to card game
        scoreBoardSprite = new TextActor(String.valueOf(currentScore), Color.WHITE, scoreOutlineColor, displayFont);
        cardGame.addActor(scoreBoardSprite, scoreBoardPosition);

    }

    /*****************************************************************************************************************/

    /** This method updates the current score for a player if the player wins a round */

    public void incrementCurrentScore() { currentScore++; }

    /*****************************************************************************************************************/

}
