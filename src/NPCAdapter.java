import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class NPCAdapter implements IPlayerAdapter {

    public NPC nonPlayableCardPlayer;

    public NPCAdapter(Player.PlayerType playerType) {
        nonPlayableCardPlayer = new NPC(playerType);
    }

    /*****************************************************************************************************************/

    /** This method is overridden from the IPlayerAdapter interface. It is used to execute the different card
        selection methods between the NPC's and Human Interactive players */

    @Override
    public Card selectCard(Hand currentHand, RoundInformation roundInformation) {
        //return card based on the execution algorithm determined by NPC type
        return nonPlayableCardPlayer.moveStrategy.executionAlgorithm(currentHand, roundInformation);
    }

    /*****************************************************************************************************************/

}
