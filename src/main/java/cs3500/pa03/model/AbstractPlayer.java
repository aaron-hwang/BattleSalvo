package cs3500.pa03.model;

import static cs3500.pa03.model.Direction.HORIZONTAL;
import static cs3500.pa03.model.Direction.VERTICAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An abstract class to represent any player of the game BattleSalvo
 */
public abstract class AbstractPlayer implements Player {

  /**
   * Our fields, protected for inheritance.
   */
  protected String name;
  protected List<Ship> ships;
  protected Board myBoard;
  protected Board enemyBoard;
  protected ArrayList<Coord> alreadyFired;
  protected Randomable rand;

  /**
   * Default constructor
   *
   * @param name The name of the player
   * @param rand The Randomable to use
   */
  public AbstractPlayer(String name, Randomable rand) {
    this.name = name;
    this.rand = rand;
    this.ships = new ArrayList<>();
    this.alreadyFired = new ArrayList<>();

  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.myBoard = new Board(height, width);
    this.enemyBoard = new Board(height, width);
    ArrayList<Ship> returnList = new ArrayList<>();
    for (ShipType s : ShipType.values()) {
      for (int i = 0; i < specifications.get(s); i++) {
        //create a new ship with Randomable coords
        returnList.add((this.RandomableShip(s, this.rand, width, height)));
      }

    }
    this.ships.addAll(returnList);
    return returnList;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board. Also update our own board to reflect the damage.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   * ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> damage = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      for (Ship s : this.ships) {
        if (s.containsCoord(c)) {
          c.setShipStatus(true);
          damage.add(c);
        }
      }
      c.setImpactStatus(true);
      this.myBoard.updateCoord(c);
    }
    return damage;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

    for (Coord c : shotsThatHitOpponentShips) {
      //update our copy of enemy board to reflect
      c.setImpactStatus(true);
      c.setShipStatus(true);
      this.enemyBoard.updateCoord(c);
    }

  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    //Doesn't do anything yet....
    System.out.println(result.getResultMessage());
    System.out.println(reason);
  }

  /**
   * Generate a Randomable valid ship
   *
   * @param type The type of ship we are generating
   * @param rand The Randomable to pass in to generate specifications for a coord from
   * @param y    the y bounds of our Randomablely generated coordinates
   * @param x    the x bounds of our Randomablely generated Coords
   * @return the Randomable valid arraylist of coords
   */
  protected Ship RandomableShip(ShipType type, Randomable rand, int x, int y) {
    ArrayList<Coord> position = new ArrayList<>();
    boolean posFound = false;
    while (!posFound) {
      int x1 = rand.nextInt(0, x);
      int y1 = rand.nextInt(0, y);
      ArrayList<Coord> attemptedPos;
      attemptedPos = this.generatePositionFrom(x1, y1, rand, type.getSize());
      posFound = attemptedPos.size() == type.getSize();
      if (posFound) {
        position.addAll(attemptedPos);
        for (Coord c : position) {
          c.setShipStatus(true);
          this.myBoard.updateCoord(c);
        }
      }
    }
    Direction direction;
    if (position.get(0).getY() != position.get(1).getY()) {
      direction = VERTICAL;
    } else {
      direction = HORIZONTAL;
    }
    return new Ship(position, type, direction);
  }

  /**
   * Attempt to generate a Randomable list of Coords, horizontal or vertical
   *
   * @param x     the attempted x to branch out from
   * @param y     the attempted y to branch out from
   * @param rand  the Randomable to seed with
   * @param range How far out we must branch
   * @return The attempted valid list of arraylist of coords
   */
  protected ArrayList<Coord> generatePositionFrom(int x, int y, Randomable rand, int range) {
    ArrayList<Coord> attemtpedPos;
    attemtpedPos = this.myBoard.generatePositions(x, y, rand, range);

    return attemtpedPos;
  }


}
