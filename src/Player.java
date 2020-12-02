import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class Player {

    public enum PlayerType { HUMAN_INTERACTIVE, LEGAL_NPC, SMART_NPC, RANDOM_NPC }

    private int playerID;
    public PlayerType playerType;
    private Score playerScore;
    private PlayingHand playingHand;
    private IPlayerAdapter playerAdapter;

    public Player(int playerID, PlayerType playerType) {
        this.playerID = playerID;
        this.playerType = playerType;
        playerScore = new Score(playerID);
        playingHand = new PlayingHand(playerID);
        initPlayerAdapter();
    }

    /*****************************************************************************************************************/

    /** Getter and setter for the current hand of the player */

    public void setPlayingHand(Hand handDealt) { this.playingHand.setCardsInHand(handDealt); }
    public PlayingHand getPlayingHand() { return playingHand; }

    /*****************************************************************************************************************/

    /** Getter for player score */

    public Score getPlayerScore() { return playerScore; }

    /*****************************************************************************************************************/

    /** Getter for the type of player: based on playerType enum class defined above */

    public PlayerType getPlayerType() { return playerType; }

    /*****************************************************************************************************************/

    /** Getter for the player ID */

    public int getPlayerID() { return playerID; }

    /*****************************************************************************************************************/

    /** This method initializes the player adapter based on the player type. Each player category (NPC and Human
        Interactive) have their own adapters, as they each have their own behaviour/implementations of selecting a
        card based on a strategy. */

    private void initPlayerAdapter() {
            if (playerType.equals(PlayerType.HUMAN_INTERACTIVE)) {
                playerAdapter = new HumanInteractiveAdapter();
            } else {
                playerAdapter = new NPCAdapter(playerType);
            }
    }

    /*****************************************************************************************************************/

    /** This method selects a card from this player's hand taking into account the player type (human interactive or
        NPC) and executes the move accordingly */

    public void executeMove(Hand trick, RoundInformation roundInformation) {
        //select card based on strategy implemented by player type
        Card selected = playerAdapter.selectCard(playingHand.getCardsInHand(), roundInformation);
        //show face of card
        selected.setVerso(false);
        //transfer to the middle of the table (as part of the trick)
        selected.transfer(trick, true);
    }

    /*****************************************************************************************************************/

}
