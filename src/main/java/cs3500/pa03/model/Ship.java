package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * A class that represents a ship
 */
public class Ship {

  /**
   * The list of Coord that represents this ship's position
   */
  private final ArrayList<Coord> position;

  /**
   * The type of ship this is
   */
  private final ShipType type;

  private final Direction direction;

  /**
   * Constructor
   *
   * @param position The arraylist representing the position of this ship
   * @param type     What type of ship this is
   */
  public Ship(ArrayList<Coord> position, ShipType type, Direction direction) {
    this.position = position;
    this.type = type;
    this.direction = direction;
  }

  /**
   * Returns whether this ship is sunk or not. A ship is determined to be sunk if every one of the
   * coords in this.position isSunk
   *
   * @return whether this ship is sunken
   */
  public boolean isSunk() {
    boolean totallySunk = true;
    for (Coord c : position) {
      totallySunk = totallySunk && c.getImpactStatus();
    }

    return totallySunk;
  }

  /**
   * Returns whether a given Coord is within this ship's position.
   *
   * @param c The given coord
   * @return Whether this ship contains c
   */
  public boolean containsCoord(Coord c) {
    for (Coord coord : this.position) {
      if (c.equals(coord)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the coord of the starting position of this ship
   *
   * @return the coord representing this ship's starting position
   */
  public Coord getStartingPosition() {
    return this.position.get(0);
  }

  /**
   * Get the length of this ship
   *
   * @return the length of this ship
   */
  public int getLength() {
    return this.type.getSize();
  }

  /**
   * Get the direction of this ship
   *
   * @return This ship's direction
   */
  public Direction getDirection() {
    return this.direction;
  }


}
