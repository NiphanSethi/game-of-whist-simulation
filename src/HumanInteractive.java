import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;

public class HumanInteractive {

    private Card selectedCard = null;

    /*****************************************************************************************************************/

    /** Getter and setter for the selected card */

    public Card getSelectedCard() { return selectedCard; }
    public void setSelectedCard(Card selectedCard) { this.selectedCard = selectedCard; }

    /*****************************************************************************************************************/

    /** Defining a function expression to set the selectedCard variable when it is clicked by the human interactive
        player */

    public CardListener cardListener = new CardAdapter() {
        public void leftDoubleClicked(Card card) {
            selectedCard = card;
        }
    };

    /*****************************************************************************************************************/

}
