import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import java.util.ArrayList;

public class SmartStrategy implements ICardChoiceStrategy {

    /*****************************************************************************************************************/

    /** This method returns the index of the array list containing the card with the next best rank from the list of
        potential moves. SmartStrategy does not return the highest card of lead card suit/winning suit, but rather a
        card that just beats the current winning card by the smallest margin */

    private int nextBestCardIndex(ArrayList<Card> potentialMoves, Card winningCard) {

        //initialize variables to store card next best card rank and index in array list
        int nextBestCardIndex = -1;
        int nextBestRank = -1;

        //iterate through array list
        for (int i = 0; i < potentialMoves.size(); i++) {
            //find the next best rank
            if (potentialMoves.get(i).getRankId() < winningCard.getRankId() &&
                    potentialMoves.get(i).getRankId() > nextBestRank) {
                //update best rank and index
                nextBestRank = potentialMoves.get(i).getRankId();
                nextBestCardIndex = i;
            }
        }

        //return index of next best card rank in array list
        return nextBestCardIndex;

    }

    /*****************************************************************************************************************/

    /** This method returns the index of the card with the smallest rank from an array list */

    private int findSmallestCardIndex(ArrayList<Card> potentialMoves) {

        //initialize variables to store index and smallest card rank
        int index = 0;
        int worstCardRank = potentialMoves.get(0).getRankId();
        //iterate through array list
        for (int i = 1; i < potentialMoves.size(); i++) {
            //if card rank has smaller value (higher rank id) than current worst card rank, update variables
            if (potentialMoves.get(i).getRankId() > worstCardRank) {
                index = i;
                worstCardRank = potentialMoves.get(i).getRankId();
            }
        }

        //return index of smallest card value in array list
        return index;

    }

    /*****************************************************************************************************************/

    /** Same functionality as previous method, but iterates through a hand of cards rather than an array list */

    private int findSmallestCardIndexFromHand(Hand currentHand) {

        //initialize a variable to store smallest card value index
        int smallestCardIndex = 0;

        //iterate through hand of cards
        for (int i = 1; i < currentHand.getNumberOfCards(); i++) {
            //if value of card is smaller (higher rank), update index
            if (currentHand.get(i).getRankId() > currentHand.get(smallestCardIndex).getRankId()) {
                smallestCardIndex = i;
            }
        }

        //return index of the smallest card value in the hand
        return smallestCardIndex;

    }

    /*****************************************************************************************************************/

    /** This is the overridden method from ICardChoiceStrategy interface. Each NPC strategy has different
        implementation of this method (makes use of strategy pattern) */

    @Override
    public Card executionAlgorithm(Hand currentHand, RoundInformation roundInformation) {

        //initialize variables representing possible moves and the current trick state
        ArrayList<Card> potentialMoves;
        Hand currentTrickState = roundInformation.getTrick();

        //if trick is empty (player is leading, choose a random card from hand)
        if (currentTrickState.isEmpty()) { return NPC.randomCard(currentHand); }
        else {
            //if player not leading, store information from round into local variables
            Card leadCard = currentTrickState.getFirst();
            PlayingCard.Suit leadCardSuit = (PlayingCard.Suit) leadCard.getSuit();
            potentialMoves = currentHand.getCardsWithSuit(leadCardSuit);
            Card winningCard = roundInformation.getWinningCard();

            //If have lead card suit and the current winning suit is of that suit, execute:
            if (!potentialMoves.isEmpty() && winningCard.getSuit() == leadCardSuit) {
                //find a card in the current hand that leads to the marginally smallest win
                int nextBestRankIndex = nextBestCardIndex(potentialMoves, winningCard);
                //if can't find a higher card than winning card, play smallest card of that suit in hand
                if (nextBestRankIndex == -1) { nextBestRankIndex = findSmallestCardIndex(potentialMoves); }
                return potentialMoves.get(nextBestRankIndex);

            } else if (!potentialMoves.isEmpty() && winningCard.getSuit() == roundInformation.getTrumpSuit()) {
                //if have lead card suit in hand but winning card is of trump suit, play lowest card of lead suit
                return potentialMoves.get(findSmallestCardIndex(potentialMoves));

            } else if (potentialMoves.isEmpty() && winningCard.getSuit() == leadCardSuit) {
                //if no more lead card suit in hand and winning card is of lead card suit, play smallest trump suit card
                potentialMoves = currentHand.getCardsWithSuit(roundInformation.getTrumpSuit());
                if (!potentialMoves.isEmpty()) { return potentialMoves.get(findSmallestCardIndex(potentialMoves)); }
                //if no trump suit card left, discard smallest card from hand (of another suit)
                else { return currentHand.get(findSmallestCardIndexFromHand(currentHand)) ; }

            } else { return currentHand.get(findSmallestCardIndexFromHand(currentHand)); }

        }

    }

    /*****************************************************************************************************************/

}
