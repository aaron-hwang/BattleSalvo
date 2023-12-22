package cs3500.pa03.model;

import cs3500.pa04.json.CoordJson;

/**
 * A class to represent a 2D Coordinate
 */
public class Coord {

  private final int xInt;
  private final int yInt;

  private boolean impacted;

  private boolean hasShip;

  /**
   * Constructor for a coord. Mote that by default, coords are considered to be not hit
   *
   * @param x       The x pos
   * @param y       the y pos
   * @param hasShip Whether it has a ship
   */
  public Coord(int x, int y, boolean hasShip) {
    this.xInt = x;
    this.yInt = y;
    this.impacted = false;
    this.hasShip = hasShip;
  }

  /**
   * Get the impact status of this ship
   *
   * @return The impact status
   */
  public boolean getImpactStatus() {
    return this.impacted;
  }

  /**
   * Set the impact status of this coord
   *
   * @param flag what to set it to
   */
  public void setImpactStatus(boolean flag) {
    this.impacted = flag;
  }

  /**
   * Determine if this coord has a ship
   *
   * @return if this coord has a ship
   */
  public boolean hasShip() {
    return this.hasShip;
  }

  /**
   * Set the status of whether this coord has a ship
   *
   * @param status The status to set it to
   */
  public void setShipStatus(Boolean status) {
    this.hasShip = status;
  }

  /**
   * Get the height of this coord
   *
   * @return the height of this coord
   */
  public int getX() {
    return this.xInt;
  }

  /**
   * Get the width of this coord
   *
   * @return return this coord's width
   */
  public int getY() {
    return this.yInt;
  }

  /**
   * Override .equals to compare two coordinates solely by their x and y values.
   * Disregards whether two Coords with different memory addresses have differing hasShip or
   * impacted values.
   *
   * @param o The other object to compare to.
   * @return Whether two coords are the same
   */
  public boolean equals(Object o) {
    if (!(o instanceof Coord)) {
      return false;
    } else {
      Coord that = (Coord) o;
      return this.xInt == that.xInt && this.yInt == that.yInt;
    }
  }

  /**
   * Overriding hashCode to be in line with .equals
   *
   * @return this coord's hashcode
   */
  public int hashCode() {
    return (this.xInt + 256) * (this.yInt + 256);
  }

  /**
   * Represent this coord as a string
   *
   * @return A string representing this coordinate
   */
  public String toString() {
    if (this.hasShip && !this.impacted) {
      //A known ship not hit yet
      return "S";
    } else if (this.hasShip && this.impacted) {
      //A known ship that's hit
      return "H";
    } else if (!this.hasShip && this.impacted) {
      //A miss.
      return "M";
    } else {
      //A coordinate that neither has a ship nor is known
      return "O";
    }
  }

  /**
   * Copy the data of another Coord. Specifically, its impacted status and hasShip status.
   *
   * @param that the other coord to copy data from
   */
  public void copyDataOf(Coord that) {
    this.impacted = that.impacted;
    this.hasShip = that.hasShip;
  }

  /**
   * Turn this into a CoordJson
   *
   * @return The coordjson with the corresonding location data
   */
  public CoordJson toJson() {
    return new CoordJson(this.xInt, this.yInt);
  }
}
