import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class RandomStrategy implements ICardChoiceStrategy {

    /** This is the overridden method from ICardChoiceStrategy interface. Each NPC strategy has different
     implementation of this method (makes use of strategy pattern) */

    @Override
    public Card executionAlgorithm(Hand currentHand, RoundInformation roundInformation) {
        return NPC.randomCard(currentHand);
    }

}
