import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardGame;
import ch.aplu.jcardgame.Hand;

public class HumanInteractiveAdapter extends CardGame implements IPlayerAdapter {

    private HumanInteractive humanInteractivePlayer;

    public HumanInteractiveAdapter() {
        humanInteractivePlayer = new HumanInteractive();
    }

    /*****************************************************************************************************************/

    /** This method is overridden from the IPlayerAdapter. It allows execution of actions of different behaviour
        between the human interactive player and NPC's through the adapter pattern. */

    @Override
    public Card selectCard(Hand currentHand, RoundInformation roundInformation) {
        humanInteractivePlayer.setSelectedCard(null);
        currentHand.setTouchEnabled(true);
        currentHand.addCardListener(humanInteractivePlayer.cardListener);
        while (humanInteractivePlayer.getSelectedCard() == null) { delay(100); }
        currentHand.setTouchEnabled(false);
        return humanInteractivePlayer.getSelectedCard();
    }

    /*****************************************************************************************************************/
}
