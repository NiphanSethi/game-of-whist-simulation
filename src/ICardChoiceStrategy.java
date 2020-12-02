import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

/** This interface is used to define specific behaviour for the NPC's. Each NPC has its own playing strategy based on
    its type. It allows behaviour abstraction through the strategy pattern */

public interface ICardChoiceStrategy {
    Card executionAlgorithm(Hand currentHand, RoundInformation roundInformation);
}
