import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

/** This interface is defined as an adapter for the different categories of players (human interactive and NPC). Each
 * category of player has its own behaviour/implementation of how to select a card and execute a move */

public interface IPlayerAdapter {
    Card selectCard(Hand currentHand, RoundInformation roundInformation);
}
