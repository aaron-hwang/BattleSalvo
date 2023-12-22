package cs3500.pa03.model;

import cs3500.pa03.controller.Reader;
import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a manual player
 */
public class ManualPlayer extends AbstractPlayer {

  private final Reader reader;

  private final View viewer;

  /**
   * Constructor
   *
   * @param name   Name of the player
   * @param reader The reader to inject
   * @param viewer The viewer to inject
   * @param rand   random
   */
  public ManualPlayer(String name, Reader reader, View viewer, Randomable rand) {
    super(name, rand);
    this.reader = reader;
    this.viewer = viewer;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    int shipsUnsunken = 0;
    for (Ship s : this.ships) {
      if (!s.isSunk()) {
        shipsUnsunken++;
      }
    }
    int availableOnEnemyBoard = this.enemyBoard.freeSquares();
    int shotsAllowed = Math.min(shipsUnsunken, availableOnEnemyBoard);
    this.promptCoordInput(shotsAllowed);
    ArrayList<Coord> shotsFired = new ArrayList<>();

    for (int i = 0; i < shotsAllowed; i++) {
      shotsFired.add(this.acceptCoordInput());
    }
    this.alreadyFired.addAll(shotsFired);
    return shotsFired;
  }

  /**
   * Accept coordinate input from the user
   *
   * @return A properly formed coordinate
   */
  private Coord acceptCoordInput() {
    //Prompt and take in user input for a given coordinate, pass in that coordinate, check if we've
    //already fired at that coord and
    int x = Integer.parseInt(reader.read());
    int y = Integer.parseInt(reader.read());
    Coord c = new Coord(x, y, false);
    if (this.alreadyFired.contains(c)) {
      this.viewer.displayMessage("Seems you've already fired at that coordinate. Try another one.");
      return this.acceptCoordInput();
    } else {
      c.setImpactStatus(true);
      this.enemyBoard.updateCoord(c);
      return c;
    }
  }

  /**
   * Prompt a user for coordinate inputs
   *
   * @param amountToAskFor How many coordinates to ask for
   */
  private void promptCoordInput(int amountToAskFor) {
    String build = "Enemy Board: " + System.lineSeparator()
        + this.enemyBoard.toString() + System.lineSeparator()
        + "----------------------------" + System.lineSeparator()
        + "Your board:" + System.lineSeparator()
        + this.myBoard.toString() + System.lineSeparator()
        + "Please input " + amountToAskFor + " shots in the format x y"
        + System.lineSeparator()
        + "Please note that coordinates are 0 indexed, and that they are increasing from"
        + "left to right, top to bottom.";
    this.viewer.displayMessage(build);
  }


}
