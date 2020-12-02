import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardGame;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class PlayingCard {

    private Actor trumpSuitActor = null;
    private final Location TRUMPS_ACTOR_LOCATION = new Location(50, 50);

    /** Define a list of enum classes for card suit, rank, and sprites for the trump suit */
    public enum Suit { SPADES, HEARTS, DIAMONDS, CLUBS }
    public enum Rank { ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO }
    private static final String[] trumpImage = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

    /*****************************************************************************************************************/

    /** This method picks a random trump suit, adds the actor to the card game, and returns the trump suit */

    public Suit pickTrumpSuit(CardGame cardGame) {

        //pick a random trump suit
        Suit trumpSuit = Whist.randomEnum(Suit.class);
        //instantiate an actor for it
        trumpSuitActor = new Actor("sprites/" + trumpImage[trumpSuit.ordinal()]);
        //add actor to the card game
        cardGame.addActor(trumpSuitActor, TRUMPS_ACTOR_LOCATION);
        return trumpSuit;

    }

    /*****************************************************************************************************************/

    /** This method removes the trump suit actor from the screen */

    public void removeTrumpActor(CardGame cardGame) {
        cardGame.removeActor(trumpSuitActor);
    }

    /*****************************************************************************************************************/
    /** This method compares the rank of two cards, using the ranking system shown in the enum class above */

    public static boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId();
    }

    /*****************************************************************************************************************/

}
