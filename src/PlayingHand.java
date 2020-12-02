import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.*;

public class PlayingHand {

    private Location position;
    private Hand cardsInHand;

    public PlayingHand(int playerID) {
        storePlayingHandLocation(playerID);
    }

    /*****************************************************************************************************************/

    /** This method instantiates the location of a player's hand on screen */

    private void storePlayingHandLocation(int playerID) {
        switch(playerID) {
            case 0:
                position = new Location(350, 625);
                break;
            case 1:
                position = new Location(75, 350);
                break;
            case 2:
                position = new Location(350, 75);
                break;
            case 3:
                position = new Location(625, 350);
                break;
        }
    }

    /*****************************************************************************************************************/

    /** Getter and setter for cards in hand */

    public Hand getCardsInHand() { return cardsInHand; }
    public void setCardsInHand(Hand cardsInHand) { this.cardsInHand = cardsInHand; }

    /*****************************************************************************************************************/

    /** Getter for the position of the player's hand on screen */

    public Location getPosition() { return position; }

    /*****************************************************************************************************************/

}
