package cs3500.pa03.model;

/**
 * Enumeration for all the different types of ship that can exist, along with their
 * associated sizes
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int size;

  /**
   * Default constructor
   *
   * @param size How big a ship of this type is
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Get the size of this ship
   *
   * @return the size of this ship
   */
  public int getSize() {
    return this.size;
  }
}
