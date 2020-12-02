import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class NPC {


    public ICardChoiceStrategy moveStrategy;

    public NPC(Player.PlayerType playerType) {
        determineMoveStrategy(playerType);
    }

    /*****************************************************************************************************************/

    /** This method returns a random card from a hand */

    public static Card randomCard(Hand hand){
        int x = Whist.random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    /*****************************************************************************************************************/

    /** This method instantiates the behaviour of the NPC based on its type. It uses the strategy pattern to
        accomplish this */

    private void determineMoveStrategy(Player.PlayerType playerType) {
        switch(playerType){
            case RANDOM_NPC:
                moveStrategy = new RandomStrategy();
                break;
            case LEGAL_NPC:
                moveStrategy = new LegalStrategy();
                break;
            case SMART_NPC:
                moveStrategy = new SmartStrategy();
        }
    }

    /*****************************************************************************************************************/

}
