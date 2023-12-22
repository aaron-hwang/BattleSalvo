package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An ai player in our game
 */
public class RobotPlayer extends AbstractPlayer {

  /**
   * Default constructor for an AI player
   *
   * @param name  The name of the player
   * @param rand  the random this player will use
   */
  public RobotPlayer(String name, Randomable rand) {
    super(name, rand);
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
    ArrayList<Coord> shotsFired = new ArrayList<>();
    int iterator = 0;
    while (iterator < shotsAllowed) {
      Coord c = this.enemyBoard.getRandomCoord(this.rand);
      if (!this.alreadyFired.contains(c)) {
        c.setImpactStatus(true);
        shotsFired.add(c);
        iterator++;
        this.alreadyFired.add(c);
      }

    }

    return shotsFired;
  }

}
