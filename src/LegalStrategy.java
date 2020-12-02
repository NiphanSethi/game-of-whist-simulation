import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import java.util.ArrayList;

public class LegalStrategy implements ICardChoiceStrategy {

    @Override
    public Card executionAlgorithm(Hand currentHand, RoundInformation roundInformation) {

        Hand trick = roundInformation.getTrick();

        //if player is leading trick, pick a random card from current hand to lead
        if (trick.isEmpty()) { return NPC.randomCard(currentHand); }
        //card picked must be of same suit as the leading card of the trick
        Card leadingCard = trick.getFirst();
        //possible actions are cards with same suit as leading card
        ArrayList<Card> possibleActions =  currentHand.getCardsWithSuit((PlayingCard.Suit) leadingCard.getSuit());
        int handLength;

        //if have cards with same suit as leading card, execute:
        if (!possibleActions.isEmpty()) {
            //return a random card from this list
            handLength = possibleActions.size();
            return possibleActions.get(Whist.random.nextInt(handLength));
        }
        //otherwise, return a random card from hand
        else { return NPC.randomCard(currentHand); }

    }

}
