package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a game board, a 2D Board comprised of a 2D arraylist of Coords
 */
public class Board {
  private final ArrayList<ArrayList<Coord>> board;

  /**
   * Constructor
   *
   * @param height the height of this board
   * @param width  the width of this board
   */
  public Board(int height, int width) {
    this.board = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      this.board.add(new ArrayList<>());
      for (int x = 0; x < width; x++) {
        Coord newCoord = new Coord(x, y, false);
        this.board.get(y).add(newCoord);
      }
    }
  }

  /**
   * Get the Coord in the xth row and yth column of this Board
   *
   * @param x the x of the coord to grab
   * @param y  the y of the coord to grab
   * @return The cprresponding coord
   */
  public Coord get(int x, int y) {
    return this.board.get(y).get(x);
  }

  /**
   * Update the given coordinate where the given Coord c is equivalent in this Board, such that
   * c.equals(e) where e is the coordinate that makes the previous expression true
   *
   * @param c the coord to update
   */
  public void updateCoord(Coord c) {
    boolean foundCoord = false;
    int i = 0;
    while (!foundCoord && i < this.board.size()) {
      ArrayList<Coord> coords = this.board.get(i);
      foundCoord = coords.contains(c);
      if (foundCoord) {
        Coord toUpdate = coords.get(coords.indexOf(c));
        toUpdate.copyDataOf(c);
      }
      i++;
    }


  }

  /**
   * Represent this board as a string
   *
   * @return This board, as a string
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (ArrayList<Coord> coords : board) {
      for (Coord c : coords) {
        builder.append(c.toString());
        builder.append(" ");
      }
      builder.append(System.lineSeparator());
    }
    return builder.toString();
  }

  /**
   * Return the amount of squares that haven't been fired upon as an int
   *
   * @return the int representing how many Coords have not been fired on
   */
  public int freeSquares() {
    int freeSquares = 0;
    for (ArrayList<Coord> coords : this.board) {
      for (Coord c : coords) {
        if (!c.getImpactStatus()) {
          freeSquares++;
        }
      }
    }

    return freeSquares;
  }

  /**
   * Get a random coord from this board
   *
   * @param rand the random to use
   * @return a random coord
   */
  public Coord getRandomCoord(Randomable rand) {
    int x = rand.nextInt(this.board.get(0).size());
    int y = rand.nextInt(this.board.size());

    return this.get(x, y);

  }

  /**
   * Randomly generate a list of Coords as long as or less than a given range, starting from a given
   * x and y, randomly either horizontal or vertical
   *
   * @param x the x of the coord to start from
   * @param y  the y of the coord to start from
   * @param random the random to use
   * @param range  how far out to branch
   * @return a random list of coordinates
   */
  public ArrayList<Coord> generatePositions(int x, int y, Randomable random, int range) {
    boolean vertOrNot = random.nextBoolean();
    if (vertOrNot) {
      return this.generateVertical(x, y, range);
    } else {
      return this.generateHorizontal(x, y, range);
    }
  }

  /**
   * Generate a random vertical position for a ship
   *
   * @param x x of the coord to generate from
   * @param y  y of the coord to generate from
   * @param range  range of how far to branch out
   * @return The list of positions
   */
  private ArrayList<Coord> generateVertical(int x, int y, int range) {
    ArrayList<Coord> returnList = new ArrayList<>();
    for (int i = 0; i < range && i < this.board.size() - y; i++) {
      Coord c = this.board.get(y + i).get(x);
      if (!c.hasShip()) {
        returnList.add(c);
      }
    }
    return returnList;
  }

  /**
   * Generate a random horizontal position for a ship
   *
   * @param x Height of the coord to gen from
   * @param y  y of the coord to gen from
   * @param range  how far to branch out
   * @return the list of positions
   */
  private ArrayList<Coord> generateHorizontal(int x, int y, int range) {
    ArrayList<Coord> returnList = new ArrayList<>();
    for (int i = 0; i < range && i < this.board.get(0).size() - x; i++) {
      Coord c = this.board.get(y).get(x + i);
      if (!c.hasShip()) {
        returnList.add(this.board.get(y).get(x + i));
      }
    }
    return returnList;
  }


}
