import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class RoundInformation {

    private Hand trick;
    private PlayingCard.Suit trumpSuit;
    private Card winningCard = null;

    public RoundInformation(Round round) {
        this.trick = round.getTrick();
        trumpSuit = round.getTrumpSuit();
    }

    /*****************************************************************************************************************/

    /** Getter for the trick */

    public Hand getTrick() { return trick; }

    /*****************************************************************************************************************/

    /** Getter for the trump suit */

    public PlayingCard.Suit getTrumpSuit() { return trumpSuit; }

    /*****************************************************************************************************************/

    /** Setter and Getter for the current winning card of the round */

    public void setWinningCard(Card winningCard) { this.winningCard = winningCard; }
    public Card getWinningCard() { return winningCard; }

    /*****************************************************************************************************************/

}
