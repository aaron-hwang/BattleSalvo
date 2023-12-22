package cs3500.pa03.model;

/**
 * Represents the possible states a game can be in
 */
public enum GameResult {
  WIN("Congrats, you win!"),
  DRAW("The game is a draw."),
  LOSE("Unfortunately, you lose :("),
  //This should not be accessed typically
  UNCONCLUDED("Game not yet concluded");

  private final String resultMessage;

  /**
   * Get the result message of this result
   *
   * @return the result message
   */
  public String getResultMessage() {
    return this.resultMessage;
  }

  /**
   * Constructor
   *
   * @param resultMessage the result meesage associated
   */
  GameResult(String resultMessage) {
    this.resultMessage = resultMessage;
  }
}
